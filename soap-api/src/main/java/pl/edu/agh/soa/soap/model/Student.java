package pl.edu.agh.soa.soap.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import java.util.List;


@XmlType(propOrder={"id","name","age","avatarPath","courses"})
public class Student {

    private String name;
    private int id;
    private int age;
    private String avatarPath = "defaultAvatar.jpg";
    private List<String> courses;

//    @Override
//    public String toString() {
//        String result = String.format("%s %d %d",name, id, age, courses);
//        for (String s : courses){
//            result += s;
//            result +=" ";
//        }
//        return result;
//    }

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
    @XmlElement(name = "course")
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
}
