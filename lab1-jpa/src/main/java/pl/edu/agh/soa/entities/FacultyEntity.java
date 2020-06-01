package pl.edu.agh.soa.entities;


import javax.persistence.*;

@Entity
@Table(name = "faculties")
public class FacultyEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    private String name;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private DeanEntity dean;


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

    public DeanEntity getDean() {
        return dean;
    }

    public void setDean(DeanEntity dean) {
        this.dean = dean;
    }
}
