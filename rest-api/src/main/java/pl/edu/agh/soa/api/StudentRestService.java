package pl.edu.agh.soa.api;

import io.swagger.annotations.*;
import pl.edu.agh.soa.model.Student;
import pl.edu.agh.soa.model.StudentsDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
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
            @ApiResponse(code = 201, message = "Student added")  ,
            @ApiResponse(code = 409, message = "Student with the same id already exists")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addStudent(@ApiParam(required=true, name = "New Student") Student student) {

            try {
                myDAO.addStudent(student);
                return Response.status(Response.Status.CREATED).build();
            }catch (Exception e){
                return Response.status(Response.Status.CONFLICT).build();
            }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("Updates student with given id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Student updated")  ,
            @ApiResponse(code = 404, message = "Student with given id does not exist")
    })
    @Path("/{id}")
    public Response updateStudent(@ApiParam(required=true) @PathParam("id") int id, @ApiParam(required=true, name = "New Student") Student student){
        try {
            myDAO.updateStudent(id, student);
            return Response.status(Response.Status.OK).build();
        }catch (Exception e){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @ApiOperation("Updates student with given id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Student deleted")  ,
            @ApiResponse(code = 404, message = "Student with given id does not exist")
    })
    @Path("/{id}")
    public Response deleteStudent(@ApiParam(required=true) @PathParam("id") int id) {
        myDAO.removeStudentById(id);
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Produces("image/jpeg")
    @Path("/{id}/avatar")
    @ApiOperation("Returns avatar of student with given ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Student found, avatar returned")  ,
            @ApiResponse(code = 404, message = "Student with given id not found") ,
            @ApiResponse(code = 500, message = "Something went wrong during loading the image, try again later")
    })
    public Response getAvatarById(@ApiParam(required=true) @PathParam("id") int id) {
        Student student = myDAO.getStudentById(id);
        if(student ==  null)
            return Response.status(Response.Status.NOT_FOUND).build();
        Object result;
        URL resource = getClass().getClassLoader().getResource(student.getAvatarPath());
        try {
            byte[] bytes = new byte[resource.openConnection().getContentLength()];
            resource.openStream().read(bytes);
            result = bytes;
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
        return Response.status(Response.Status.OK).entity(result).build();
    }
}
