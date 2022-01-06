/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import bbdd.Actuador;
import bbdd.Registro_actuador;
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
 * Devuelve un json con los actuadores del sistema y otro con los registros de actuadores (simulación de presencia) 
 * @author lucyr
 */
public class GetRegistrosActuadores extends HttpServlet {

    public GetRegistrosActuadores() {
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
        ArrayList<Actuador> actuadores= new ArrayList<>();
        ArrayList<Registro_actuador> registros_actuadores = new ArrayList<>();
        Log.log.info("-- Buscando registros de actuadores del sistema " + request.getParameter("cod_sistema")+" --");
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            actuadores=Logic.getActuadores(Integer.parseInt(request.getParameter("cod_sistema")));
            registros_actuadores=(Logic.getRegistrosActuadoresSistema(Integer.parseInt(request.getParameter("cod_sistema"))));
            String jsonActuadores = new Gson().toJson(actuadores);
            String jsonRegistros = new Gson().toJson(registros_actuadores);
            Log.log.info("JSON value => {}", jsonActuadores);
            Log.log.info("JSON value => {}", jsonRegistros);
            out.println(jsonActuadores);
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
