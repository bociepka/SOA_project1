package pl.edu.agh.soa.model;

public class Faculty {

    private int id;
    private String name;
    private Dean dean;

    public Faculty() {
    }

    public Faculty(String name, Dean dean){
        this.name = name;
        this.dean = dean;
    }

    public Dean getDean() {
        return dean;
    }

    public void setDean(Dean dean) {
        this.dean = dean;
    }

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
