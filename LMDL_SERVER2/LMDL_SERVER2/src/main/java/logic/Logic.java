package logic;


import bbdd.Sistema_seguridad;
import bbdd.Actuador;
import bbdd.Cliente;
import bbdd.ConexionBD;
import bbdd.Habitacion;
import bbdd.Identificacion;
import bbdd.Topic;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;


public class Logic 
{
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
    /**
     * 
     * @return La lista de todos los sistemas almacenados en la base de datos
     */
    public static ArrayList<Sistema_seguridad> getSistemas()
    {
        ArrayList<Sistema_seguridad> sistemas = new ArrayList<Sistema_seguridad>();
		
        ConexionBD conector = new ConexionBD();
        Connection con = null;
	try
        {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
			
            PreparedStatement ps = ConexionBD.GetSistemas(con);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Sistema_seguridad sistema = new Sistema_seguridad();
                sistema.setNombre(rs.getString("nombre"));
                sistema.setCod_sistema(Integer.parseInt(rs.getString("cod_sistema")));
                sistema.setDireccion(rs.getString("direccion"));
                sistema.setId_cliente_cliente(Integer.parseInt(rs.getString("id_cliente_cliente")));
                sistemas.add(sistema);
            }	
	} catch (SQLException e)
	{
            Log.log.error("Error: {}", e);
            sistemas = new ArrayList<Sistema_seguridad>();
	} catch (NullPointerException e)
        {
            Log.log.error("Error: {}", e);
            sistemas = new ArrayList<Sistema_seguridad>();
	} catch (Exception e)
        {
            Log.log.error("Error:{}", e);
            sistemas = new ArrayList<Sistema_seguridad>();
        } finally
        {
            conector.closeConnection(con);
        }
        return sistemas;
    }
	
    /**
     * 
     * @return La lista de todos los usuarios de un sistema
     */
    public static ArrayList<Identificacion> getIdentificaciones(){
        ArrayList<Identificacion> identificaciones = new ArrayList<Identificacion>();
	ConexionBD conector = new ConexionBD();
	Connection con = null;
        try
        {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
		
            PreparedStatement ps = ConexionBD.GetIdentificaciones(con);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Identificacion identificacion = new Identificacion();
		identificacion.setCodigo_qr(rs.getInt("codigo_qr"));
                identificacion.setNombre(rs.getString("nombre"));
                identificacion.setPassword(rs.getString("password"));
                identificacion.setCod_sistema_sistema_seguridad(rs.getInt("cod_sistema_sistema_seguridad"));
                identificaciones.add(identificacion);
            }	
        } catch (SQLException e)
	{
            Log.log.error("Error: {}", e);
            identificaciones = new ArrayList<Identificacion>();
	} catch (NullPointerException e)
        {
            Log.log.error("Error: {}", e);
            identificaciones = new ArrayList<Identificacion>();
        } catch (Exception e)
        {
            Log.log.error("Error:{}", e);
            identificaciones = new ArrayList<Identificacion>();
        } finally
        {
            conector.closeConnection(con);
	}
        return identificaciones;
    }
	

    /**
     * Funcion que pide a la base de datos las habitaciones registradas en el sistema. 
     * @return La lista de todas las habitaciones de un sistema
     */
    public static ArrayList<Habitacion> getHabitaciones(){
        ArrayList<Habitacion> habitaciones = new ArrayList<Habitacion>();
	ConexionBD conector = new ConexionBD();
	Connection con = null;
        try
        {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
		
            PreparedStatement ps = ConexionBD.GetHabitaciones(con);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Habitacion habitacion=new Habitacion();
		habitacion.setId_habitacion(rs.getInt("id_habitacion"));
                habitacion.setN_puertas(rs.getInt("n_puertas"));
                habitacion.setN_ventanas(rs.getInt("n_ventanas"));
                habitacion.setTamanno(rs.getInt("tamanno"));
                habitacion.setCod_sistema_sistema_seguridad(rs.getInt("cod_sistema_sistema_seguridad"));
            }	
        } catch (SQLException e)
	{
            Log.log.error("Error: {}", e);
            habitaciones = new ArrayList<Habitacion>();
	} catch (NullPointerException e)
        {
            Log.log.error("Error: {}", e);
            habitaciones = new ArrayList<Habitacion>();
        } catch (Exception e)
        {
            Log.log.error("Error:{}", e);
            habitaciones = new ArrayList<Habitacion>();
        } finally
        {
            conector.closeConnection(con);
	}
        return habitaciones;
    }
    
    /**
     * Funcion que devuelve la contraseña de un id_usuario que recibe como parámetro
     * @param nombre, contrasena 
     * @return boolean
     */
    
    public static Boolean getContrasena(String nombre, String contrasena_introducida){
        String contrasena="";
        ConexionBD conector = new ConexionBD();
	Connection con = null;
        try
        {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConexionBD.GetContrasenaUsuario(con);
            ps.setString(1, nombre);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            contrasena=rs.getString("password");
        } catch (SQLException e)
	{
            Log.log.error("Error: {}", e);
            contrasena="";
	} catch (NullPointerException e)
        {
            Log.log.error("Error: {}", e);
            contrasena="";
        } catch (Exception e)
        {
            Log.log.error("Error:{}", e);
            contrasena="";
        } finally
        {
            conector.closeConnection(con);
	}
        if(contrasena==""){
            return false;
        }
        return contrasena_introducida==contrasena; 
    }
	
    /**
     * Buscar el entero que representa el código QR pasado como parámetro en las identificaciones del sistema de la base de datos. 
     * Devuelve el nombre del usuario asociado a ese QR. 
     * @param cod_QR_leido
     * @return usuario
     */
    
    public static String getUsuarioQR (int cod_QR_leido){
        String usuario="";
        ConexionBD conector = new ConexionBD();
	Connection con = null;
        try
        {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConexionBD.GetContrasenaUsuario(con);
            ps.setInt(1, cod_QR_leido);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            usuario=rs.getString("nombre");
        } catch (SQLException e)
	{
            Log.log.error("Error: {}", e);
            usuario="";
	} catch (NullPointerException e)
        {
            Log.log.error("Error: {}", e);
            usuario="";
        } catch (Exception e)
        {
            Log.log.error("Error:{}", e);
            usuario="";
        } finally
        {
            conector.closeConnection(con);
	}

        return usuario;
    }
    
    
    
    /**
     * Funcion que devuelve los sensores que hay en una habitacion
     * @param id_Habitacion ID de la habitación de la que queremos obtener los sensores
     * @return La lista de sensores de una habitacion 
     */
      
    /**
     * Funcion que devuelve los actuadores que hay en una habitacion
     * @param id_Habitacion ID de la habitación de la que queremos obtener los sensores
     * @return La lista de actuadores de una habitacion 
     */
	
    /**
     * Funcion que devuelve los registros_estadisticos de los sensores de una habitacion 
     * @param id_habitacion
     * @return Arraylist con los registros de los sensores de esa habitacion 
     */
    
    /**
     * Funcion que devuelve los registros de los actuadores de una habitacion 
     * @param id_habitacion
     * @return Arraylist con los registros de los actuadores de esa habitacion 
     */

    /** 
     * Añadir un nuevo registro de un sensor a la base de datos 
     * @param newTopic 
     */
    public static void guardarRegistroSensor(Topic newTopic) {
        ConexionBD conector = new ConexionBD();
	Connection con = null;
        try
	{
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConexionBD.InsertarRegistroSensor(con);
            ps.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            ps.setFloat(2, newTopic.getValor());
            ps.setTime(3, java.sql.Time.valueOf(LocalTime.now(ZoneId.of("Europe/Madrid"))));
            ps.setInt(4, newTopic.getId_sensor_actuador());
            Log.log.info("Query=> {}", ps.toString());
            ps.executeUpdate();            	
	} catch (SQLException e)
	{
            Log.log.error("Error: {}", e);
	} catch (NullPointerException e)
	{
            Log.log.error("Error: {}", e);
	} catch (Exception e)
	{
            Log.log.error("Error:{}", e);
        } finally
        {
            conector.closeConnection(con);
	}	
    }
    
    /**
     * Registrar en la base de datos un nuevo registro de un actuador. 
     * @param newReg
     */
    
    public static void guardarRegistroActuador(Topic newTopic){
        ConexionBD conector = new ConexionBD();
	Connection con = null;
        try
	{
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConexionBD.InsertarRegistroActuador(con);
            ps.setTime(1, java.sql.Time.valueOf(LocalTime.now(ZoneId.of("Europe/Madrid"))));
            ps.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            ps.setFloat(3, newTopic.getValor()); //Duración en segundos
            ps.setInt(4, newTopic.getId_sensor_actuador());
            Log.log.info("Query=> {}", ps.toString());
            ps.executeUpdate();            	
	} catch (SQLException e)
	{
            Log.log.error("Error: {}", e);
	} catch (NullPointerException e)
	{
            Log.log.error("Error: {}", e);
	} catch (Exception e)
	{
            Log.log.error("Error:{}", e);
        } finally
        {
            conector.closeConnection(con);
	}
    }
    
	
}