package pl.edu.agh.soa.dao;

import pl.edu.agh.soa.model.Student;

import java.util.List;

public interface StudentsDAOInterface {

    public List<Student> getAllStudents();

    public void addStudent(Student newStudent) throws Exception ;

    public List<Student> getStudentByName(String name);

    public List<Student> getStudentByAge(int age);

    public Student getStudentById(int id);

    public void updateStudentById(int id, String name, int age) throws Exception ;

    public void removeStudentById(int id);

    public String addCourseToStudent(int id, String course);

    public String updateAvatar(int id, String avatarPath);
    public Student updateStudent(int id, Student student) throws Exception;
    public StudentsDAOInterface populateListWithDefaultData();
}
