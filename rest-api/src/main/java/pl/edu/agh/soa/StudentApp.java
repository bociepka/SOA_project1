package pl.edu.agh.soa;


import io.swagger.jaxrs.config.BeanConfig;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class StudentApp extends Application {

    public StudentApp(){
        BeanConfig beanConfig = new BeanConfig();

        beanConfig.setVersion("1.0.0");
        beanConfig.setTitle("StudentApp API");
        beanConfig.setBasePath("/rest-api/api");
        beanConfig.setResourcePackage("pl.edu.agh.soa");
        beanConfig.setScan(true);
    }
}
