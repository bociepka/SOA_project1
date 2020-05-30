package pl.edu.agh.soa.model;

public class Dean {

    private int id;
    private String degree;
    private String name;

    public Dean() {
    }

    public Dean(String degree, String name) {
        this.degree = degree;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
