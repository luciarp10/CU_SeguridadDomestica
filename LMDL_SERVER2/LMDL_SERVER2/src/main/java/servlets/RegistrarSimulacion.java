/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import bbdd.Alerta;
import bbdd.Registro_actuador;
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
 * Dados como parámetros el codigo del sistema, el usuario que activa el actuador, el actuador que se quiere activar y el tiempo en segundos,
 * se envía un topic al sensor a través de mqtt y se registra la activación en la base de datos en la tabla alerta y en registro_actuador. 
 * @author lucyr
 */
public class RegistrarSimulacion extends HttpServlet {

    public RegistrarSimulacion() {
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
        Log.log.info("-- Registrando simulación de presencia en el sistema " + request.getParameter("cod_sistema")+" --");
        response.setContentType("text/html;charset=UTF-8");
        Alerta alerta_nueva=new Alerta();
        Registro_actuador registro_nuevo = new Registro_actuador();
        PrintWriter out = response.getWriter();
        try {           
            //Registrar alerta en la base de datos
            Log.log.info("Insertar en alerta registro activación de simulación de presencia por el usuario: "+request.getParameter("usuario"));
            alerta_nueva.setId_alerta(Logic.getUltimaAlerta(Integer.parseInt(request.getParameter("cod_sistema")))+1);
            alerta_nueva.setInfo("Usuario "+ request.getParameter("usuario") + " programa sensor de presencia " + request.getParameter("id_actuador") + " durante " +request.getParameter("tiempo")+ " segundos");
            alerta_nueva.setCod_sistema_sistema_seguridad(Integer.parseInt(request.getParameter("cod_sistema")));
            Logic.insertarAlerta(alerta_nueva);
            
            //Publicar topic para que el sensor se active 
            MqttBroker broker = new MqttBroker();
            MqttPublisher.publish(broker, "SistSeg"+request.getParameter("cod_sistema")+"/Actuador"+request.getParameter("id_actuador"), request.getParameter("tiempo"));

            String json = new Gson().toJson(1);
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
