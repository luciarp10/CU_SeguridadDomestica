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
import mqtt.MqttSuscriber;

/**
 * Se recibe como parámetro el id_actuador de la cámara con la que se quiere hacer la foto, el nombre de usuario que la solicita y el codigo del sistema
 * @author lucyr
 */
public class HacerFoto extends HttpServlet {

    public HacerFoto() {
        super();
    }

    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Log.log.info("-- Solicitando foto de la cámara con id " + request.getParameter("id_camara") + " del sistema "+request.getParameter("cod_sistema")+" --");
        Log.log.info("Insertar en alerta solicitud de foto ");
        Alerta alerta_nueva=new Alerta();
        alerta_nueva.setId_alerta(Logic.getUltimaAlerta(Integer.parseInt(request.getParameter("cod_sistema")))+1);
        alerta_nueva.setInfo("Usuario "+ request.getParameter("usuario") + " solicita foto de la cámara con id: "+ request.getParameter("id_camara"));
        alerta_nueva.setCod_sistema_sistema_seguridad(Integer.parseInt(request.getParameter("cod_sistema")));
        Logic.insertarAlerta(alerta_nueva);
        //Publicar topic para que los sensores de la alarma se activen 
        MqttBroker broker = new MqttBroker();
        MqttPublisher.publish(broker, "ServidorSistema"+request.getParameter("cod_sistema")+"/Camara"+request.getParameter("id_camara"), "Hacer foto");
        MqttSuscriber suscriber = new MqttSuscriber();
        suscriber.searchTopicsToSuscribe(broker);
 
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String json = new Gson().toJson("1");
            Log.log.info("JSON value => {}", json);
            out.println(json);
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
