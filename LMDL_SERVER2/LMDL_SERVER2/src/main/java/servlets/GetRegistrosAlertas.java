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

/**
 * Dado como parámetro el código del sistema de seguridad, devuelve todos los registros de alertas. 
 * @author lucyr
 */
public class GetRegistrosAlertas extends HttpServlet {

    public GetRegistrosAlertas() {
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
        ArrayList<Alerta> alertas_registradas= new ArrayList<>();
        Log.log.info("-- Buscando registros de alertas en el sistema de seguridad "+request.getParameter("cod_sistema")+" --");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            alertas_registradas=Logic.getRegistrosAlertas(Integer.parseInt(request.getParameter("cod_sistema")));
            String jsonAlertas = new Gson().toJson(alertas_registradas);
            Log.log.info("JSON value => {}", jsonAlertas);
            out.println(jsonAlertas);
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
