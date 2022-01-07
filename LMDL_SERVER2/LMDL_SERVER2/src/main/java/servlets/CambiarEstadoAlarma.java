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
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logic.Log;
import logic.Logic;
import mqtt.MqttBroker;
import mqtt.MqttPublisher;

/**
 * Como parámetros se reciben el código del sistema, el nombre de usuario y el estado. (1=activar y 0=Desactivar) y el servidor envía un topic a los 
 * sensores del sistema de seguridad para que se desactiven. 
 * @author lucyr
 */
public class CambiarEstadoAlarma extends HttpServlet {

    public CambiarEstadoAlarma() {
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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try{
            if(Integer.parseInt(request.getParameter("estado"))==1){
                Log.log.info("-- Activando sistema de seguridad "+request.getParameter("cod_sistema")+" --");
                Log.log.info("Insertar en alerta registro de activación de alarma ");
                Alerta alerta_nueva=new Alerta();
                alerta_nueva.setId_alerta(Logic.getUltimaAlerta(Integer.parseInt(request.getParameter("cod_sistema")))+1);
                alerta_nueva.setInfo("Alarma activada desde app por usuario "+ request.getParameter("usuario"));
                alerta_nueva.setCod_sistema_sistema_seguridad(Integer.parseInt(request.getParameter("cod_sistema")));
                Logic.insertarAlerta(alerta_nueva);
                Logic.cambiarEstadoSistema(1,Integer.parseInt(request.getParameter("cod_sistema")));
                //Publicar topic para que los sensores de la alarma se activen 
                MqttBroker broker = new MqttBroker();
                MqttPublisher.publish(broker, "SistSeg"+request.getParameter("cod_sistema")+"/Sistema", "Activar");
            }
            else{
                Log.log.info("-- Desactivando sistema de seguridad "+request.getParameter("cod_sistema")+" --");
                Alerta alerta_nueva=new Alerta();
                alerta_nueva.setId_alerta(Logic.getUltimaAlerta(Integer.parseInt(request.getParameter("cod_sistema")))+1);
                alerta_nueva.setInfo("Alarma desactivada desde app por usuario "+ request.getParameter("usuario"));
                alerta_nueva.setCod_sistema_sistema_seguridad(Integer.parseInt(request.getParameter("cod_sistema")));
                Logic.insertarAlerta(alerta_nueva);
                Logic.cambiarEstadoSistema(0,Integer.parseInt(request.getParameter("cod_sistema")));
                //Publicar topic para que los sensores de la alarma se apaguen (desconecten) 
                MqttBroker broker = new MqttBroker();
                MqttPublisher.publish(broker, "SistSeg"+request.getParameter("cod_sistema")+"/Sistema", "Desactivar");
            }

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
