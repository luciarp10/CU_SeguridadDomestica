/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import bbdd.Registro_sensor;
import bbdd.Sensor;
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

/**
 * Dada una habitación como parámetro, una fecha de inicio y una de fin, se devuelven todos los registros de esa habitación
 * entre dichas fechas. También se devuelven los sensores que tiene la habitación para saber a qué sensor corresponde cada registro. 
 * @author lucyr
 */
public class GetRegistrosSensoresHabitacionFecha extends HttpServlet {

    public GetRegistrosSensoresHabitacionFecha() {
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
        ArrayList<Sensor> sensores_habitacion= new ArrayList<>();
        ArrayList<Registro_sensor> registros_habitacion = new ArrayList<>();
        Log.log.info("-- Buscando registros de la habitacion " + request.getParameter("id_habitacion") + ""
                + "entre los días " + request.getParameter("fecha_ini") + " - "+request.getParameter("fecha_fin")+" --");
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            sensores_habitacion=Logic.getSensoresHabitacion(Integer.parseInt(request.getParameter("id_habitacion")));
            registros_habitacion=(Logic.getRegistrosSensoresHabitacionFecha(Integer.parseInt(request.getParameter("id_habitacion")), request.getParameter("fecha_ini"), request.getParameter("fecha_fin")));
            String jsonSensores = new Gson().toJson(sensores_habitacion);
            String jsonRegistros = new Gson().toJson(registros_habitacion);
            Log.log.info("JSON value => {}", jsonSensores);
            Log.log.info("JSON value => {}", jsonRegistros);
            out.println(jsonSensores);
            out.println(jsonRegistros);
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
