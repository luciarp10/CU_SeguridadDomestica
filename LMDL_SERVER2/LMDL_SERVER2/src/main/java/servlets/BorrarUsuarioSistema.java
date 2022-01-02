/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import bbdd.Identificacion;
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
 * Dado un nombre de usuario, se elimina de la base de datos. No se comprueba que esté porque al hacer un getUsuariosSistema previamente desde
 * la app, el usuario que seleccione para borrar siempre va a estar. 
 * @author lucyr
 */

public class BorrarUsuarioSistema extends HttpServlet {

    public BorrarUsuarioSistema() {
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
        Log.log.info("-- Borrando identificacion del sistema " + request.getParameter("cod_sistema")+" --");
        response.setContentType("text/html;charset=UTF-8");
        Identificacion usuario_nuevo = new Identificacion();
        PrintWriter out = response.getWriter();
        try {           
            
            Logic.borrarIdentificacion(request.getParameter("nombre_usuario"));
            String json = new Gson().toJson(1);
            Log.log.info("Se ha borrado la identificación con nombre: "+ request.getParameter("nombre_usuario")+" de la base de datos.");
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
