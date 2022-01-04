/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import bbdd.Alerta;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logic.Log;
import logic.Logic;
import mqtt.MqttBroker;
import mqtt.MqttPublisher;

/**
 * Si el usuario está registrado y la contraseña es correcta, devuelve el código del sistema de seguridad y true si el usuario es administrador o false si no lo es.
 * del usuario para que todo lo que se le muestre en la aplicación sea en relación con ese sistema. 
 * @author lucyr
 */
public class InicioSesion extends HttpServlet {


    public InicioSesion(){
        super();
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * Parámetros --> usuario y password
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Log.log.info("-- Comprobando si usuario y contraseña están registrados --");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String usuario = request.getParameter("usuario");
            String contrasenna= request.getParameter("password");
            Boolean usuario_registrado = Logic.getContrasena(usuario,contrasenna);
            if(usuario_registrado){
                boolean es_admin = Logic.getTipoUsuario(usuario);
                int cod_sistema_seguridad = Logic.getSistemaUsuario(usuario);
                String jsontipoUsuario= new Gson().toJson(es_admin);
                String jsonSistema = new Gson().toJson(cod_sistema_seguridad);
                Log.log.info("JSON value => {}", jsonSistema);
                Log.log.info("JSON value => {}", jsontipoUsuario);
                out.println(jsonSistema);
                out.println(jsontipoUsuario);
            }
            else{
                String jsonSistema = new Gson().toJson(-1);
                Log.log.info("JSON value => {}", jsonSistema);
                out.println(jsonSistema);
            }
            
        }
        catch (NumberFormatException nfe) 
        {
            out.println("-1");
            Log.log.error("Number Format Exception: {}", nfe);
	} catch (IndexOutOfBoundsException iobe) 
        {
            out.println("-1");
            Log.log.error("Index out of bounds Exception: {}", iobe);
        } catch (Exception e) 
	{
            out.println("-1");
            Log.log.error("Exception: {}", e);
	} finally 
        {
            out.close();
	}
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
