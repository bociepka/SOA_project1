package pl.edu.agh.soa.dao;

import pl.edu.agh.soa.entities.CourseEntity;
import pl.edu.agh.soa.entities.FacultyEntity;
import pl.edu.agh.soa.entities.StudentEntity_;
import pl.edu.agh.soa.mappers.CoursesMapper;
import pl.edu.agh.soa.mappers.FacultiesMapper;
import pl.edu.agh.soa.mappers.StudentsMapper;
import pl.edu.agh.soa.model.Dean;
import pl.edu.agh.soa.model.Faculty;
import pl.edu.agh.soa.model.Student;
import pl.edu.agh.soa.entities.StudentEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class StudentsDAO{

    @PersistenceContext(unitName = "students")
    EntityManager em;


    public List<Student> getAllStudents() {
        return getAllStudents(Collections.emptyMap());
    }

    public List<Student> getAllStudents(Map<String, String> params) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<StudentEntity> criteriaQuery = builder.createQuery(StudentEntity.class);
        Root<StudentEntity> root = criteriaQuery.from(StudentEntity.class);
        if(params.isEmpty()) {
            criteriaQuery.select(root);
        }
        else {
            List<Predicate> predicates = new ArrayList<Predicate>();
            for(Map.Entry<String, String> param : params.entrySet()){
                if (param.getKey().equals("age")){
                    predicates.add(builder.equal(root.get(param.getKey()), Integer.parseInt(param.getValue())));
                }
                else if(param.getKey().equals("course")){
                    predicates.add(builder.equal(root.join("courses", JoinType.INNER).get("name")
                            ,param.getValue()));
                }
                else{
                    predicates.add(builder.equal(root.get(param.getKey()), param.getValue()));
                }
            }
            criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));
        }
        TypedQuery<StudentEntity> query = em.createQuery(criteriaQuery);
        List<StudentEntity> resultList = query.getResultList();
        if(resultList == null)
            return null;
        return resultList.stream().map(StudentsMapper::entityToModel).collect(Collectors.toList());
    }



    public void addStudent(Student student){
        StudentEntity studentEntity = StudentsMapper.modelToEntity(student);
        studentEntity.setCourses(new HashSet<>());
        for(String course : student.getCourses()){
            CourseEntity courseEntity = CoursesMapper.modelToEntity(course);
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<CourseEntity> criteriaQuery = builder.createQuery(CourseEntity.class);
            Root<CourseEntity> root = criteriaQuery.from(CourseEntity.class);
            criteriaQuery.select(root);
            TypedQuery<CourseEntity> query = em.createQuery(criteriaQuery);
            List<CourseEntity> resultList = query.getResultList();
            for (CourseEntity result : resultList){
                if (result.getName().equals(course)){
                    courseEntity = result;
                }
            }
            studentEntity.getCourses().add(courseEntity);
        }

        if (student.getFaculty() != null){
            FacultyEntity facultyEntity = FacultiesMapper.modelToEntity(student.getFaculty());
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<FacultyEntity> criteriaQuery = builder.createQuery(FacultyEntity.class);
            Root<FacultyEntity> root = criteriaQuery.from(FacultyEntity.class);
            criteriaQuery.select(root);
            TypedQuery<FacultyEntity> query = em.createQuery(criteriaQuery);
            List<FacultyEntity> resultList = query.getResultList();
            for (FacultyEntity result : resultList){
                if (result.getName().equals(student.getFaculty().getName())){
                    facultyEntity = result;
                }
            }
            studentEntity.setFaculty(facultyEntity);
        }
//        studentEntity.setName((String.valueOf(studentEntity.getId())));
        em.persist(studentEntity);
    }

    public Student getStudentById(int id) {
        return StudentsMapper.entityToModel(em.find(StudentEntity.class, id));
    }

    public void removeStudentById(int id) {
        StudentEntity s = em.find(StudentEntity.class, id);
        em.remove(s);
    }

    public Student updateStudent(int id, Student student) {
        StudentEntity studentEntity = StudentsMapper.modelToEntity(student);
        studentEntity.setCourses(new HashSet<>());
        for(String course : student.getCourses()){
            CourseEntity courseEntity = CoursesMapper.modelToEntity(course);
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<CourseEntity> criteriaQuery = builder.createQuery(CourseEntity.class);
            Root<CourseEntity> root = criteriaQuery.from(CourseEntity.class);
            criteriaQuery.select(root);
            TypedQuery<CourseEntity> query = em.createQuery(criteriaQuery);
            List<CourseEntity> resultList = query.getResultList();
            for (CourseEntity result : resultList){
                if (result.getName().equals(course)){
                    courseEntity = result;
                }
            }
            studentEntity.getCourses().add(courseEntity);
        }
        studentEntity.setId(id);
        em.merge(studentEntity);
        return student;
    }
    public void populateListWithDefaultData() {
        ArrayList<String> courses = new ArrayList<>();
        Dean dean = new Dean("prof. dr hab. inż.", "Jan Nowak");
        Faculty faculty = new Faculty("EAIiIB", dean);
        courses.add("SOA");
        courses.add("Technologie Mobilne");
        courses.add("Kompilatory");
        courses.add("Interfejsy multimodalne");
        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student("Jacek",21, courses, faculty));
        students.add(new Student("Kasia",24, courses, faculty));
        students.add(new Student("Basia",22, courses, faculty));
        students.add(new Student("Kasia",20, courses, faculty));
        students.add(new Student("Jacek",22, courses, faculty));
        for (Student student : students){
            addStudent(student);
        }
//        addStudent(new Student("A", 1, 21, courses, faculty));
    }

}
