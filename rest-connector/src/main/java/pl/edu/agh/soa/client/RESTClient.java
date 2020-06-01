package pl.edu.agh.soa.client;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.specimpl.MultivaluedMapImpl;
import pl.edu.agh.soa.model.Dean;
import pl.edu.agh.soa.model.Faculty;
import pl.edu.agh.soa.model.Student;
import pl.edu.agh.soa.model.StudentProto;
import pl.edu.agh.soa.protobuf.StudentMessageWriter;

import javax.swing.*;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RESTClient {

    private ResteasyClient resteasyClient;
    private String username = "";
    private String password = "";
    private static String token = null;


    public RESTClient() {
        this.resteasyClient = new ResteasyClientBuilder().build();
    }

    public RESTClient(String username, String password) {
        this.resteasyClient = new ResteasyClientBuilder().build();
        this.username = username;
        this.password = password;
        authorize();
    }

    public void endSession() {
        if(!resteasyClient.isClosed())
            resteasyClient.close();
    }

    public void authorize() {
        ResteasyWebTarget target = resteasyClient.target("http://localhost:8080/rest-api/api/auth/login");
        Form form = new Form();
        form.param("login", this.username);
        form.param("password", this.password);
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));
        MultivaluedMap<String, String> headers = response.getStringHeaders();
        token = headers.getFirst("authorization");
    }

    private List<Student> getAllStudents(MultivaluedMap<String, Object> parameters) {
        List<Student> result = null;
        ResteasyWebTarget target = resteasyClient.target("http://localhost:8080/rest-api/api/students/");

        System.out.println("Getting all students");
        if(parameters != null && !parameters.isEmpty()) {
            target = target.queryParams(parameters);
            System.out.println("filtering parameters:");
            for(String key : parameters.keySet()) {
                System.out.println(" - " + key + ": " + parameters.getFirst(key));
            }
        }

        Response response = target.request().get();
        int responseStatus = response.getStatus();
        if(responseStatus == 200) {
            result = response.readEntity(new GenericType<List<Student>>() {});
        }
        response.close();

        System.out.println("RESPONSE STATUS: "+responseStatus);
        return result;
    }

    private Student getStudentById(int id){
        Student result = null;
        ResteasyWebTarget target = resteasyClient.target("http://localhost:8080/rest-api/api/students/"+id);
        System.out.println("Getting student with id "+id);
        Response response = target.request().get();
        int responseStatus = response.getStatus();
        if(responseStatus == 200) {
            result = response.readEntity(Student.class);
        }
        response.close();
        System.out.println("RESPONSE STATUS: "+responseStatus);
        return result;
    }

    private StudentProto.Student getStudentByIdProto(int id){
        StudentProto.Student result = null;
        ResteasyWebTarget target = resteasyClient.target("http://localhost:8080/rest-api/api/students/"+id+"/protobuf").register(StudentMessageWriter.class);
        System.out.println("Getting student with id "+id+"(ProtoBuf)");
        Response response = target.request().get();
        int responseStatus = response.getStatus();
        if(responseStatus == 200) {
            result = response.readEntity(StudentProto.Student.class);
        }
        response.close();
        System.out.println("RESPONSE STATUS: "+responseStatus);
        return result;
    }

    private void addStudent(Student student){
        ResteasyWebTarget target = resteasyClient.target("http://localhost:8080/rest-api/api/students/");
        System.out.println("Adding new student: "+student.toString());
        if(token == null) {
            authorize();
        }
        Response response = target.request().header("Authorization", token).post(Entity.entity(student, MediaType.APPLICATION_JSON_TYPE));
        System.out.println("RESPONSE STATUS: "+response.getStatus());
        response.close();
    }

    private void displayAvatar(int id){
        byte[] result = null;
        ResteasyWebTarget target = resteasyClient.target("http://localhost:8080/rest-api/api/students/"+id+"/avatar");
        System.out.println("Getting avatar of student with id "+id);
        Response response = target.request().get();
        int responseStatus = response.getStatus();
        if(responseStatus == 200) {
            result = response.readEntity(byte[].class);
        }
        response.close();
        System.out.println("RESPONSE STATUS: "+responseStatus);
        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(500, 640);
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(result));
        panel.add(label);
        jFrame.getContentPane()
                .add(panel);
        jFrame.pack();
        jFrame.setVisible(true);
    }

    private void removeStudent(int id){
        ResteasyWebTarget target = resteasyClient.target("http://localhost:8080/rest-api/api/students/"+id);
        System.out.println("Removing student with id "+id);
        if(token == null) {
            authorize();
        }
        Response response = target.request().header("Authorization", token).delete();
        System.out.println("RESPONSE STATUS: "+response.getStatus());
        response.close();
    }


    public static void main(String[] args) {

        RESTClient consumer = new RESTClient("user1","user1");
        //printing all students
        System.out.println("\nWszyscy studenci (id, imię, wiek, przedmioty):");
        for(Student student : consumer.getAllStudents(null)){
            System.out.println(student.toString());
        }

        //adding new student
        List<String> courses = new ArrayList<>();
        Dean dean = new Dean("dr hab. inż.", "Adam Kowalski");
        Faculty faculty = new Faculty("WIEiT", dean);
        courses.add("Systemy operacyjne");
        courses.add("Kompilatory");
        Student student = new Student("Jacek", 99,"defaultAvatar2.jpg", courses, faculty);
        consumer.addStudent(student);

        //printing with filtering
        MultivaluedMap<String, Object> query = new MultivaluedMapImpl<>();
        query.add("name","Jacek");
        System.out.println("\nWszyscy studenci (id, imię, wiek, przedmioty) o imieniu Jacek:");
        for(Student student2 : consumer.getAllStudents(query)){
            System.out.println(student2.toString());
        }

        //printing student with id 3
        System.out.println("\nStudent o id 3");
        System.out.println(consumer.getStudentById(3).toString());

        //displaying avatar
        System.out.println("\n");
        consumer.displayAvatar(6);

        //deleting previously added student
        System.out.println("\n");
        consumer.removeStudent(6);


        //getting student in ProtoBuf
        System.out.println("\n");
        System.out.println(consumer.getStudentByIdProto(2));

        consumer.endSession();
    }
}
