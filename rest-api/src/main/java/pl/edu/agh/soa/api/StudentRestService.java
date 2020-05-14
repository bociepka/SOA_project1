package pl.edu.agh.soa.api;

import io.swagger.annotations.*;
import pl.edu.agh.soa.model.Student;
import pl.edu.agh.soa.model.StudentsDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/students")
@Api(value = "Students API")
public class StudentRestService {

    private static StudentsDAO myDAO = new StudentsDAO().populateListWithDefaultData();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    @ApiOperation("Returns list of all students")
    @ApiResponses({
            @ApiResponse(code = 404, message = "No students found"),
            @ApiResponse(code = 200, message = "Students found",
                    response = Student.class,
                    responseContainer = "List")
    })
    public Response getAllStudents(
            @QueryParam("name") String name,
            @QueryParam("age") int age,
            @QueryParam("course") List<String> courses
    ) {
        List<Student> resultList = myDAO.getAllStudents();
        if(name != null)
            resultList = resultList.stream().filter(student -> student.getName().equals(name)).
                    collect(Collectors.toList());
        if(age != 0)
            resultList = resultList.stream().filter(student -> student.getAge() == age).
                    collect(Collectors.toList());
        if(courses != null)
            for (String course : courses) {
            resultList = resultList.stream().filter(student -> student.getCourses().contains(course)).collect(Collectors.toList());
        }
        if( resultList.size() == 0)
            return Response.status(Response.Status.NOT_FOUND).entity("No students found").build();
        return Response.status(Response.Status.OK).entity(resultList).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    @ApiOperation("Returns student with given ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Student found")  ,
            @ApiResponse(code = 404, message = "Student with given id not found")
    })
    public Response getStudentById(@ApiParam(required=true) @PathParam("id") int id) {
        if (myDAO.getStudentById(id) == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        else {
            return Response.status(Response.Status.OK).entity(myDAO.getStudentById(id)).build();
        }
    }

    @POST
    @Path("/")
    @ApiOperation("Adds student to the database")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Student added")  ,
            @ApiResponse(code = 409, message = "Student with the same id already exists")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addStudent(@ApiParam(required=true, name = "New Student") Student student) {

            try {
                myDAO.addStudent(student);
                return Response.status(Response.Status.OK).build();
            }catch (Exception e){
                return Response.status(Response.Status.CONFLICT).build();
            }
    }
}
