package pl.edu.agh.soa.api;

import pl.edu.agh.soa.model.Student;
import pl.edu.agh.soa.model.StudentsDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/students")
public class StudentRestService {

    private StudentsDAO myDAO = new StudentsDAO().populateListWithDefaultData();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response getAllStudents(

    ) {
        List<Student> resultList = myDAO.getAllStudents();

        return Response.status(Response.Status.OK).entity(resultList).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getStudentById(@PathParam("id") int id) {
        if (myDAO.getStudentById(id) == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        else {
            return Response.status(Response.Status.OK).entity(myDAO.getStudentById(id)).build();
        }
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addStudent(Student student) {
            try {
                myDAO.addStudent(student);
            }catch (Exception e){
                return Response.status(Response.Status.CONFLICT).entity(myDAO.getStudentById(student.getId())).build();
            }
            return Response.status(Response.Status.OK).entity(student).build();
    }
}
