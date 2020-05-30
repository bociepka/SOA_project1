package pl.edu.agh.soa.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(propOrder={"id","name","age","avatarPath","courses", "faculty"})
public class Student {

    private String name;
    private int id;
    private int age;
    private String avatarPath = "defaultAvatar.jpg";
    private List<String> courses;
    private Faculty faculty;


    public Student() {
    }

    public Student(String name, int id, int age) {
        this.name = name;
        this.id = id;
        this.age = age;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student(String name, int id, int age, List<String> courses, Faculty faculty) {
        this.name = name;
        this.id = id;
        this.age = age;
        this.courses = courses;
        this.faculty = faculty;
    }

    public Student(String name, int id, int age, List<String> courses) {
        this(name, id, age);
        this.courses = courses;
    }

    public Student(String name, int id, int age, String avatarPath) {
        this(name, id, age);
        this.avatarPath = avatarPath;
    }

    public Student(String name, int id, int age, String avatarPath, List<String> courses) {
        this(name, id, age, avatarPath);
        this.courses = courses;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    @XmlElementWrapper(name = "courses")
    @XmlElement(name = "courses")
    public List<String> getCourses() {
        return courses;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        String result = this.getId() + ", " + this.getName() + ", lat " + this.getAge() + ", ";
        if (this.getCourses() != null){
            result += this.getCourses();
        }
        if (this.getFaculty() != null) {
            result += ", " + this.getFaculty().getName();
            if (this.getFaculty().getDean() != null)
            result += " (dziekan - " + this.getFaculty().getDean().getDegree() + " " + this.getFaculty().getDean().getName() + ")";
        }
        return result;
    }


    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }
}
