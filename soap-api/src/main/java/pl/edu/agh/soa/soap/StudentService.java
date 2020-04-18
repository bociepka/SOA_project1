package pl.edu.agh.soa.soap;

//import org.apache.commons.codec.binary.Base64;
import org.jboss.annotation.security.SecurityDomain;
import org.jboss.ws.api.annotation.WebContext;
import pl.edu.agh.soa.soap.model.Student;

import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;


@Stateless
@WebService
@SecurityDomain("Test-security-domain")
@DeclareRoles({"GrupaTestowa"})
@WebContext(contextRoot = "/soa", urlPattern = "/studentService", authMethod = "BASIC", transportGuarantee = "NONE", secureWSDLAccess = false)
@SOAPBinding(style = SOAPBinding.Style.RPC, use = SOAPBinding.Use.LITERAL)
public class StudentService {

    private StudentsDAO myDAO = new StudentsDAO().populateListWithDefaultData();

    @RolesAllowed("GrupaTestowa")
    @WebMethod(action = "getAllStudents")
    @WebResult(name = "getAllStudents")
    @XmlElementWrapper(name = "students")
    @XmlElement(name = "student")
    public List<Student> getAllStudents(){
        return myDAO.getAllStudents();
    }

    @RolesAllowed("GrupaTestowa")
    @WebMethod(action = "getStudentById")
    @WebResult(name = "getStudentByIdResult")
    @XmlElement(name = "student")
    public Student getStudentById(@WebParam(name = "id") int id) {
        return myDAO.getStudentById(id);
    }

    @RolesAllowed("GrupaTestowa")
    @WebMethod(action = "getStudentsByName")
    @WebResult(name = "getStudentByNameResult")
    @XmlElementWrapper(name = "students")
    @XmlElement(name = "student")
    public List<Student> getStudentsByName(@WebParam(name = "name") String name){
        return myDAO.getStudentByName(name);
    }

    @RolesAllowed("GrupaTestowa")
    @WebMethod(action = "getStudentsByAge")
    @WebResult(name = "getStudentByAgeResult")
    @XmlElementWrapper(name = "students")
    @XmlElement(name = "student")
    public List<Student> getStudentsByAge(@WebParam(name = "age") int age){
        return myDAO.getStudentByAge(age);
    }

    @RolesAllowed("GrupaTestowa")
    @WebMethod(action = "addStudent")
    @WebResult(name = "addStudentResult")
    public String addStudent(@WebParam(name = "id") int id,@WebParam(name = "name") String name, @WebParam(name ="age") int age){
        try {
            myDAO.addStudent(new Student(name, id, age));
        }catch(Exception e){
            return e.getMessage();
        }
        return "Student added successfully";
    }

    @RolesAllowed("GrupaTestowa")
    @WebMethod(action = "addCourseToStudent")
    @WebResult(name = "addCourseToStudentResult")
    public String addCourseToStudent(@WebParam(name = "id") int id, @WebParam(name = "course")String course){
        return myDAO.addCourseToStudent(id, course);
    }

    @RolesAllowed("GrupaTestowa")
    @WebMethod(action = "updateAvatarOfStudent")
    @WebResult(name = "updateAvatarOfStudentResult")
    public String updateAvatarOfStudent(@WebParam(name = "id") int id, @WebParam(name = "avatarPath")String avatarPath){
        return myDAO.updateAvatar(id, avatarPath);
    }

    @RolesAllowed("GrupaTestowa")
    @WebMethod(action = "updateStudent")
    @WebResult(name = "updateStudentResult")
    public String updateStudent(@WebParam(name = "id") int id,@WebParam(name = "name") String name,@WebParam(name = "age") int age){
        try{
            myDAO.updateStudentById(id, name, age);
        }catch(Exception e){
            return e.getMessage();
        }
        return "Student updated successfully";
    }

    @RolesAllowed("GrupaTestowa")
    @WebMethod(action = "removeStudentById")
    @WebResult(name = "removeStudentByIdResult")
    public String removeStudentById(@WebParam(name="id") int id){
        myDAO.removeStudentById(id);
        return "Student removed successfully";
    }

    @RolesAllowed("GrupaTestowa")
    @WebMethod(action = "getBase64AvatarById")
    @WebResult(name = "getBase64AvatarByIdResult")
    public String getBase64AvatarById(@WebParam(name = "id") int id) {
        Student student = myDAO.getStudentById(id);
        if(student ==  null)
            return null;
        String result;
        URL resource = getClass().getClassLoader().getResource(student.getAvatarPath());
        try {
            byte[] bytes = new byte[resource.openConnection().getContentLength()];
            resource.openStream().read(bytes);
            result = Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            return e.getMessage();
        }
        return result;
    }


}
