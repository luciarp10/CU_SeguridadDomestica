package logic;


import bbdd.Sistema_seguridad;
import bbdd.Actuador;
import bbdd.Cliente;
import bbdd.ConexionBD;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


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
				sistema.setNombre(rs.getString("NAME"));
				sistema.setCod_sistema(Integer.parseInt(rs.getString("COD_SISTEMA")));
                                sistema.setDireccion(rs.getString("DIRECCION"));
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

	
	/**
	 * 
	 * @return La lista de todas las habitaciones de un sistema
	 */

	
	/**
	 * 
	 * @param idHabitacion ID de la habitación de la que queremos obtener los sensores
	 * @return La lista de sensores de una habitacion 
	 */

	
	/**
	 * 
	 * @return The last week measurements
	 */

	
	/**
	 * 
	 * @return The last week measurements
	 */

	
	/**
	 * 	
	 * @param idStation
	 * @return Arraylist con los registros de los sensores y actuadores de esa habitacion 
	 */

	
	/**
	 * 	
	 * @param newTopic
	 * @return Almacenar un nuevo registro de algún sensor o actuador en la base de datos
	 */

	
	
	
}