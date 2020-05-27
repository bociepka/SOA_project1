package pl.edu.agh.soa.mappers;

import pl.edu.agh.soa.dao.CoursesDAO;
import pl.edu.agh.soa.entities.CourseEntity;
import pl.edu.agh.soa.entities.StudentEntity;
import pl.edu.agh.soa.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StudentsMapper {

    public static StudentEntity modelToEntity(Student student){
        StudentEntity entity = new StudentEntity();
        Set<CourseEntity> courses = new java.util.HashSet<>();
        entity.setAge(student.getAge());
        entity.setName(student.getName());
        entity.setAvatarPath(entity.getAvatarPath());
        entity.setId(student.getId());
        for (String course : student.getCourses()) {
            courses.add(CoursesMapper.modelToEntity(course));
        }
        entity.setCourses(courses);
        return entity;
    }

    public static Student entityToModel(StudentEntity entity){
        Student student = new Student();
        List<String> courses = new ArrayList<>();
        student.setAge(entity.getAge());
        student.setName(entity.getName());
        student.setId(entity.getId());
        student.setAvatarPath(entity.getAvatarPath());
        for (CourseEntity course: entity.getCourses()){
            courses.add(course.getName());
        }
        student.setCourses(courses);
        return student;
    }

}
