package pl.edu.agh.soa.soap;

import org.jboss.annotation.security.SecurityDomain;
import org.jboss.ws.api.annotation.WebContext;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@Stateless
@WebService
@SecurityDomain("Test-security-domain")
@DeclareRoles({"GrupaTestowa"})
@WebContext(contextRoot = "/soa", urlPattern = "/helloSecurity", authMethod = "BASIC", transportGuarantee = "NONE", secureWSDLAccess = false)
@SOAPBinding(style = SOAPBinding.Style.RPC, use = SOAPBinding.Use.LITERAL)
public class HelloWorld {


    @RolesAllowed("GrupaTestowa")
    @WebMethod(action = "helloSecurity")
    @WebResult(name = "HiSecurityResult")
    public String hello(@WebParam(name = "name") String name) {
        return "Hello " + name;
    }

}
