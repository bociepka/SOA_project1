package pl.edu.agh.soa.dao;

import pl.edu.agh.soa.model.Student;
import pl.edu.agh.soa.entities.StudentEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class StudentsDAO implements StudentsDAOInterface {

    @PersistenceContext(unitName = "students")
    private EntityManager em;

    @Override
    public List<Student> getAllStudents() {
//        em.find();
        return null;
    }

    @Override
    public void addStudent(Student newStudent) throws Exception {

    }

    @Override
    public List<Student> getStudentByName(String name) {
        return null;
    }

    @Override
    public List<Student> getStudentByAge(int age) {
        return null;
    }

    @Override
    public Student getStudentById(int id) {
        return null;
    }

    @Override
    public void updateStudentById(int id, String name, int age) throws Exception {

    }

    @Override
    public void removeStudentById(int id) {

    }

    @Override
    public String addCourseToStudent(int id, String course) {
        return null;
    }

    @Override
    public String updateAvatar(int id, String avatarPath) {
        return null;
    }

    @Override
    public Student updateStudent(int id, Student student) throws Exception {
        return null;
    }

    @Override
    public StudentsDAOInterface populateListWithDefaultData() {
        StudentEntity student = new StudentEntity();
        student.setId(1);
        student.setName("Jacek");
        student.setAge(21);
        em.persist(student);
        return this;
    }
}
