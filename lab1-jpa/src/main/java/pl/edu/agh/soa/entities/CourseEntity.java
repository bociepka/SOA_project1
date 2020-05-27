package pl.edu.agh.soa.entities;


import javax.persistence.*;
import java.beans.ConstructorProperties;

@Entity
@Table(name = "courses")
public class CourseEntity {


    @Id
    @GeneratedValue
    private int id;

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
