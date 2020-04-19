package pl.edu.agh.soa;

import javax.imageio.ImageIO;
import javax.xml.ws.BindingProvider;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class Client {

    public static void main(String[] args){
        StudentService studentService = new StudentServiceService().getStudentServicePort();
        setCredentials((BindingProvider) studentService);
        System.out.println("Wszyscy studenci:");
        listAllStudents(studentService);
        addStudent(studentService, "Adam", 333, 33);
        System.out.println("Wszyscy studenci (po dodaniu nowego):");
        listAllStudents(studentService);
        getAvatarById(studentService, studentService.getAllStudents().getItem().get(0).getId());
    }

    private static void listAllStudents(StudentService studentService) {
        System.out.println("id, imię, wiek, przedmioty");
        for(Student student : studentService.getAllStudents().getItem()) {
            System.out.print(student.getId() + ", " + student.getName() + ", lat " + student.getAge() + ", ");
            if (student.getCourses() != null){
                System.out.print(student.getCourses().getCourse()+"\n");
            }
            else{
                System.out.println("BRAK");
            }
        }
    }

    private static void addStudent(StudentService studentService, String name, int id, int age){
        studentService.addStudent(id, name, age);
    }

    private static void getAvatarById(StudentService studentService, int id){
        String base64String = studentService.getBase64AvatarById(id);
        try (FileOutputStream fos = new FileOutputStream(String.format("avatar_no_%d.jpg",id))) {
            byte[] imageByteArray = Base64.getDecoder().decode(base64String);
            fos.write(imageByteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setCredentials(BindingProvider port){
        port.getRequestContext().put("javax.xml.ws.security.auth.username","user1");
        port.getRequestContext().put("javax.xml.ws.security.auth.password","user1");
    }


}
