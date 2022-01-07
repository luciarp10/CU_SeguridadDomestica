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
 * Dados los datos del usuario y el código del sistema, insertarlos en la base de datos. 
 * @author lucyr
 */
public class InsertarUsuarioSistema extends HttpServlet {

    public InsertarUsuarioSistema() {
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
        Log.log.info("-- Creando nueva identificacion en el sistema " + request.getParameter("cod_sistema")+" --");
        response.setContentType("text/html;charset=UTF-8");
        Identificacion usuario_nuevo = new Identificacion();
        PrintWriter out = response.getWriter();
        try {           
            //Registrar usuario en la base de datos si no existe ya
            ArrayList<Identificacion> identificaciones_sistema = Logic.getIdentificacionesSistema(Integer.parseInt(request.getParameter("cod_sistema")));
            ArrayList<String> nombres_de_usuario = new ArrayList<>();
            for (int i=0; i<identificaciones_sistema.size();i++){
                nombres_de_usuario.add(identificaciones_sistema.get(i).getNombre());
            }
            if (nombres_de_usuario.contains(request.getParameter("nombre_usuario"))){
                String json = new Gson().toJson(-1);
                Log.log.info("-- Error al registrar la identificación, el nombre de usuario " + request.getParameter("nombre_usuario")+ " ya existe --");
                Log.log.info("JSON value => {}", json);
                out.println(json);
            }
            else{
                int cod_qr = Logic.getUltimoQr()+1;
                usuario_nuevo.setCodigo_qr(cod_qr);
                usuario_nuevo.setNombre(request.getParameter("nombre_usuario"));
                usuario_nuevo.setPassword(request.getParameter("password"));
                usuario_nuevo.setCod_sistema_sistema_seguridad(Integer.parseInt(request.getParameter("cod_sistema")));
                usuario_nuevo.setAdmin(false); //solo se pueden registrar administradores cuando se da de alta el sistema (se crea una identificación para el cliente principal, ese es el administrador)
                Logic.insertarIdentificacion(usuario_nuevo);
                String json = new Gson().toJson(1);
                Log.log.info("JSON value => {}", json);
                out.println(json);
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
