
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webServices;

import com.google.gson.Gson;
import java.io.PrintWriter;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.MediaType;
import logic.Log;
import logic.Logic;
/**
 *
 * @author lucyr
 */
@Path ("ComprobarQR")
@RequestScoped
public class ComprobarQR extends javax.ws.rs.core.Application{

    /**
     * This is a sample web service operation
     */
    @Context
    private UriInfo context;

    public ComprobarQR() {
    }
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String comprobar(@QueryParam("codigo") String codigo, @QueryParam("id_sistema") int id_sistema) {
        Log.log.info("-- Buscando información código QR en base de datos--");
	String usuario=Logic.getUsuarioQR(Integer.parseInt(codigo), id_sistema);
        if(!usuario.equals("")){
            return "Bienvenido"+usuario;
        }
        else{
            return "Error, no hay ningún usuario registrado en el sistema con este código.";
        }
    }
}
