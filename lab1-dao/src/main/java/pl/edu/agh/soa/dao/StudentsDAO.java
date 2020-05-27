package pl.edu.agh.soa.dao;

import pl.edu.agh.soa.entities.CourseEntity;
import pl.edu.agh.soa.mappers.StudentsMapper;
import pl.edu.agh.soa.model.Student;
import pl.edu.agh.soa.entities.StudentEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class StudentsDAO{

    @PersistenceContext(unitName = "students")
    EntityManager em;// = Persistence.createEntityManagerFactory("students").createEntityManager();


    public List<Student> getAllStudents() {
//        List<Student> students = new ArrayList<>();
//        Query q = em.createNativeQuery("SELECT (id, name, age) FROM students", StudentEntity.class);
//        List<StudentEntity> entities = q.getResultList();
//        for (StudentEntity entity: entities){
//            students.add(StudentsMapper.entityToModel(entity));
//        }
//        Student student = new Student("Adam", 1, 21);
//        if (em == null){
//            student.setName("null");
//        }
//        students.add(student);
//        em.createNativeQuery("SELECT name from students");
//        return students;
        return getAllStudents(Collections.emptyMap());
    }

    public List<Student> getAllStudents(Map<String, String> params) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<StudentEntity> criteriaQuery = builder.createQuery(StudentEntity.class);
        Root<StudentEntity> root = criteriaQuery.from(StudentEntity.class);
        if(params.isEmpty()) {
            criteriaQuery.select(root);
        }
//        else {
//            criteriaQuery.select(root).where(processParameters(builder, root, params));
//        }
        TypedQuery<StudentEntity> query = em.createQuery(criteriaQuery);
        List<StudentEntity> resultList = query.getResultList();
        if(resultList == null)
            return null;
        return resultList.stream().map(StudentsMapper::entityToModel).collect(Collectors.toList());
    }




    public void addStudent(Student newStudent) throws Exception {
        em.persist(StudentsMapper.modelToEntity(newStudent));
    }


    public List<Student> getStudentByName(String name) {
        return null;
    }


    public List<Student> getStudentByAge(int age) {
        return null;
    }


    public Student getStudentById(int id) {
        return null;
    }


    public void updateStudentById(int id, String name, int age) throws Exception {

    }


    public void removeStudentById(int id) {
        StudentEntity s = em.find(StudentEntity.class, id);
        em.remove(s);
    }


    public String addCourseToStudent(int id, String course) {
        return null;
    }


    public String updateAvatar(int id, String avatarPath) {
        return null;
    }


    public Student updateStudent(int id, Student student) throws Exception {
        return null;
    }

    public void populateListWithDefaultData() {
        ArrayList<String> courses = new ArrayList<>();
        courses.add("SOA");
        courses.add("Technologie Mobilne");
        courses.add("Kompilatory");
        courses.add("Interfejsy multimodalne");
        em.persist(StudentsMapper.modelToEntity(new Student("Jacek",1, 21, courses)));
        em.persist(StudentsMapper.modelToEntity(new Student("Kasia",2, 24, courses)));
        em.persist(StudentsMapper.modelToEntity(new Student("Basia",3, 22, courses)));
        em.persist(StudentsMapper.modelToEntity(new Student("Kasia", 4, 20, courses)));
        em.persist(StudentsMapper.modelToEntity(new Student("Jacek", 5, 22, courses)));
    }



}
