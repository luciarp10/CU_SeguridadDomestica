/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logic.Log;
import logic.Logic;

/**
 * Envía como parámetro la ruta de la imagen y se manda como respuesta en un json. El json contiene un array de tipo byte[] que después hay que transformar en jpg.
 * @author lucyr
 */
public class GetImagen extends HttpServlet {

    public GetImagen() {
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
        BufferedImage img = null;
        
        
        BufferedImage bi = ImageIO.read(new File(request.getParameter("enlace_foto")));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, "jpg", baos);
        byte[] bytes = baos.toByteArray();
        
        /*
        Para pasar de byte[] a BufferedImage y guardarlo
        img=toBufferedImage(bytes);
        ImageIO.write(img, "jpg", new File("/tmp/imagenes/prueba.jpg"));
        */
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            
            String foto = new Gson().toJson(bytes);
            Log.log.info("JSON value => {}", foto);
            out.println(foto);
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


    
    /* Funcion para pasar de byte[] a BufferedImage de nuevo
    public static BufferedImage toBufferedImage(byte[] bytes)
        throws IOException {

        InputStream is = new ByteArrayInputStream(bytes);
        BufferedImage bi = ImageIO.read(is);
        return bi;

    }

    */ 
    
}
