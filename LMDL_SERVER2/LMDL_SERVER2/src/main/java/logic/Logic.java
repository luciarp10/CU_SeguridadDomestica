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
	 * 
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
	 * 
	 * @param idHabitacion ID de la habitación de la que queremos obtener los sensores
	 * @return La lista de sensores de una habitacion 
	 */
        
	
         

	
	/**
	 * 	
	 * @param id_habitacion
	 * @return Arraylist con los registros de los sensores de esa habitacion 
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