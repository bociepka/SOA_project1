package pl.edu.agh.soa.entities;


import javax.persistence.*;
import java.util.Set;


@Entity
@Table
public class StudentEntity {

    @Column
    private String name;

    @Id
    @Column
    private int id;

    @Column
    private int age;

    @Column
    private String avatarPath = "defaultAvatar.jpg";


    @ManyToMany
    @JoinTable
    private Set<CourseEntity> courses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public Set<CourseEntity> getCourses() {
        return courses;
    }

    public void setCourses(Set<CourseEntity> courses) {
        this.courses = courses;
    }
}
