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
 *
 * @author lucyr
 */
public class ComprobarQR extends HttpServlet {

    private static final long serialVersionUID = 1L; 
    
    public ComprobarQR(){
        super();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * Parametros --> codigo e id_sistema
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Log.log.info("-- Comprobando si QR escaneado está en el sistema --");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            int codigo_leido = Integer.parseInt(request.getParameter("codigo"));
            int codigo_sistema = Integer.parseInt(request.getParameter("id_sistema"));
            String usuario = Logic.getUsuarioQR(codigo_leido, codigo_sistema);
            String jsonUsuario = new Gson().toJson(usuario);
            Log.log.info("JSON value => {}", jsonUsuario);
            //Registrar alerta en la base de datos
            if(!usuario.equals("")){
                Log.log.info("Insertar en alerta registro de entrada de: "+usuario);
                Alerta alerta_nueva=new Alerta();
                alerta_nueva.setId_alerta(Logic.getUltimaAlerta(codigo_sistema)+1);
                alerta_nueva.setInfo("Alarma desconectada por "+usuario+" mediante código QR.");
                alerta_nueva.setCod_sistema_sistema_seguridad(codigo_sistema);
                Logic.insertarAlerta(alerta_nueva);
                //Cambiar el estado de la alarma en la base de datos
                Logic.cambiarEstadoSistema(0, codigo_sistema);
                //Publicar topic para que los sensores de la alarma se apaguen (desconecten) 
                MqttBroker broker = new MqttBroker();
                MqttPublisher.publish(broker, "SistSeg"+codigo_sistema+"/Alerta", "Desactivar");
            }
            else{
                Log.log.info("Insertar en Alerta registro de entrada incorrecto.");
                Alerta alerta_nueva = new Alerta();
                alerta_nueva.setId_alerta(Logic.getUltimaAlerta(codigo_sistema)+1);
                alerta_nueva.setInfo("Intento de desconexión de alarma incorrecto. Código QR desconocido.");
                alerta_nueva.setCod_sistema_sistema_seguridad(codigo_sistema);
                Logic.insertarAlerta(alerta_nueva);
            }
            out.println(jsonUsuario);
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
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }


}
