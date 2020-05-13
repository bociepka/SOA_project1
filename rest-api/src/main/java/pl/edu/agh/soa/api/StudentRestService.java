package pl.edu.agh.soa.api;

import pl.edu.agh.soa.model.Student;
import pl.edu.agh.soa.model.StudentsDAO;

import javax.print.attribute.standard.Media;
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
}
