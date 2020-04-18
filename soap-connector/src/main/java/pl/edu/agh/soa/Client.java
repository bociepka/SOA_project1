package pl.edu.agh.soa;

import javax.imageio.ImageIO;
import javax.xml.ws.BindingProvider;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

public class Client {

    public static void main(String[] args){
        StudentService studentService = new StudentServiceService().getStudentServicePort();
        setCredentials((BindingProvider) studentService);
        listAllStudents(studentService);
        getAvatarById(studentService, 1);
    }

    private static void listAllStudents(StudentService studentService) {
        for(Student student : studentService.getAllStudents().getItem())
            System.out.println(student.getName()+", "+student.getAge());
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
