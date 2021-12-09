package logic;


import bbdd.Sistema_seguridad;
import bbdd.Actuador;
import bbdd.Alerta;
import bbdd.Cliente;
import bbdd.ConexionBD;
import bbdd.Habitacion;
import bbdd.Identificacion;
import bbdd.Registro_actuador;
import bbdd.Topic;
import bbdd.Registro_camara;
import bbdd.Registro_sensor;
import bbdd.Sensor;

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
     * @return lista de actuadores de la base de datos
     */
    public static ArrayList<Actuador> getActuadores(){
        ArrayList<Actuador> actuadores = new ArrayList<Actuador>();
	ConexionBD conector = new ConexionBD();
	Connection con = null;
        try
        {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
		
            PreparedStatement ps = ConexionBD.GetActuadores(con);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Actuador actuador = new Actuador();
		actuador.setId_actuador(rs.getInt("id_actuador"));
                actuador.setTipo(rs.getString("tipo"));
                actuador.setId_habitacion(rs.getInt("id_habitacion_habitacion"));
                actuadores.add(actuador);
            }	
        } catch (SQLException e)
	{
            Log.log.error("Error: {}", e);
            actuadores = new ArrayList<Actuador>();
	} catch (NullPointerException e)
        {
            Log.log.error("Error: {}", e);
            actuadores = new ArrayList<Actuador>();
        } catch (Exception e)
        {
            Log.log.error("Error:{}", e);
            actuadores = new ArrayList<Actuador>();
        } finally
        {
            conector.closeConnection(con);
	}
        return actuadores;
    }
    
    /**
     * Funcion que devuelve los clientes registrados en la base de datos
     * @return lista de clientes
     */
    
        /**
     * Funcion que pide a la base de datos las habitaciones registradas en el sistema. 
     * @return La lista de todas las habitaciones de un sistema
     */
    public static ArrayList<Cliente> getClientes(){
        ArrayList<Cliente> clientes = new ArrayList<Cliente>();
	ConexionBD conector = new ConexionBD();
	Connection con = null;
        try
        {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
		
            PreparedStatement ps = ConexionBD.GetClientes(con);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Cliente cliente = new Cliente();
		cliente.setDni(rs.getString("dni"));
                cliente.setId_cliente(rs.getInt("id_cliente"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setTelefono(rs.getString("telefono"));
            }	
        } catch (SQLException e)
	{
            Log.log.error("Error: {}", e);
            clientes = new ArrayList<Cliente>();
	} catch (NullPointerException e)
        {
            Log.log.error("Error: {}", e);
            clientes = new ArrayList<Cliente>();
        } catch (Exception e)
        {
            Log.log.error("Error:{}", e);
            clientes = new ArrayList<Cliente>();
        } finally
        {
            conector.closeConnection(con);
	}
        return clientes;
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
     * @return registros de los actuadores del sistema
     */
        public static ArrayList<Registro_actuador> getRegistrosActuadores(){
        ArrayList<Registro_actuador> registros_actuadores = new ArrayList<Registro_actuador>();
	ConexionBD conector = new ConexionBD();
	Connection con = null;
        try
        {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
		
            PreparedStatement ps = ConexionBD.GetRegistrosActuadores(con);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Registro_actuador registroAct = new Registro_actuador();
		registroAct.setHora_on(rs.getTimestamp("hora_on"));
                registroAct.setFecha_on(rs.getDate("fecha_on"));
                registroAct.setDuracion(rs.getFloat("duracion"));
                registroAct.setId_actuador_actuador(rs.getInt("id_actuador_actuador"));
                registros_actuadores.add(registroAct);
            }	
        } catch (SQLException e)
	{
            Log.log.error("Error: {}", e);
            registros_actuadores = new ArrayList<Registro_actuador>();
        } catch (NullPointerException e)
        {
            Log.log.error("Error: {}", e);
            registros_actuadores = new ArrayList<Registro_actuador>();
        } catch (Exception e)
        {
            Log.log.error("Error:{}", e);
            registros_actuadores = new ArrayList<Registro_actuador>();
        } finally
        {
            conector.closeConnection(con);
	}
        return registros_actuadores;
    }
    
    /**
     * Devuelve todos los registros de cámara de la base de datos
     * @return arraylist<Registro_camara>
     */
    public static ArrayList<Registro_camara> getRegistrosCamaras(){
        ArrayList<Registro_camara> registros_camaras = new ArrayList<Registro_camara>();
	ConexionBD conector = new ConexionBD();
	Connection con = null;
        try
        {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
		
            PreparedStatement ps = ConexionBD.GetRegistrosCamaras(con);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Registro_camara registroCam = new Registro_camara();
		registroCam.setFecha(rs.getDate("fecha"));
                registroCam.setEnlace_foto(rs.getString("foto"));
                registroCam.setHora(rs.getTimestamp("hora"));
                registroCam.setId_sensor_sensor(rs.getInt("id_sensor_sensor"));
                registros_camaras.add(registroCam);
            }	
        } catch (SQLException e)
	{
            Log.log.error("Error: {}", e);
            registros_camaras = new ArrayList<Registro_camara>();
        } catch (NullPointerException e)
        {
            Log.log.error("Error: {}", e);
            registros_camaras = new ArrayList<Registro_camara>();
        } catch (Exception e)
        {
            Log.log.error("Error:{}", e);
            registros_camaras = new ArrayList<Registro_camara>();
        } finally
        {
            conector.closeConnection(con);
	}
        return registros_camaras;
    }
    
    /**
     * Funcion que devuelve la lista de sensores del sistema
     * @return ArrayList<Sensor>
     */
    public static ArrayList<Sensor> getSensores(){
        ArrayList<Sensor> sensores = new ArrayList<Sensor>();
	ConexionBD conector = new ConexionBD();
	Connection con = null;
        try
        {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
		
            PreparedStatement ps = ConexionBD.GetSensores(con);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Sensor sensor = new Sensor();
		sensor.setId_sensor(rs.getInt("id_sensor"));
                sensor.setTipo(rs.getString("tipo"));
                sensor.setId_habitacion_habitacion(rs.getInt("id_habitacion_habitacion"));
                sensores.add(sensor);
            }	
        } catch (SQLException e)
	{
            Log.log.error("Error: {}", e);
            sensores = new ArrayList<Sensor>();
        } catch (NullPointerException e)
        {
            Log.log.error("Error: {}", e);
            sensores = new ArrayList<Sensor>();
        } catch (Exception e)
        {
            Log.log.error("Error:{}", e);
            sensores = new ArrayList<Sensor>();
        } finally
        {
            conector.closeConnection(con);
	}
        return sensores;
    }
        
    /**
     * Sensores de una habitacion
     * @param id_habitacion
     * @return ArrayList<Sensor>
     */ 
    public static ArrayList<Sensor> getSensoresHabitacion(int id_habitacion){
        ArrayList<Sensor> sensores = new ArrayList<Sensor>();
	ConexionBD conector = new ConexionBD();
	Connection con = null;
        try
        {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
		
            PreparedStatement ps = ConexionBD.GetSensoresHabitacion(con);
            ps.setInt(1, id_habitacion);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Sensor sensor = new Sensor();
		sensor.setId_sensor(rs.getInt("id_sensor"));
                sensor.setTipo(rs.getString("tipo"));
                sensor.setId_habitacion_habitacion(rs.getInt("id_habitacion_habitacion"));
                sensores.add(sensor);
            }	
        } catch (SQLException e)
	{
            Log.log.error("Error: {}", e);
            sensores = new ArrayList<Sensor>();
        } catch (NullPointerException e)
        {
            Log.log.error("Error: {}", e);
            sensores = new ArrayList<Sensor>();
        } catch (Exception e)
        {
            Log.log.error("Error:{}", e);
            sensores = new ArrayList<Sensor>();
        } finally
        {
            conector.closeConnection(con);
	}
        return sensores;
    }
        
        
    /**
     * Actuadores de una habitacion
     * @param id_habitacion
     * @return ArrayList<Actuador>
     */
    public static ArrayList<Actuador> getActuadoresHabitacion(int id_habitacion){
        ArrayList<Actuador> actuadores = new ArrayList<Actuador>();
	ConexionBD conector = new ConexionBD();
	Connection con = null;
        try
        {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
		
            PreparedStatement ps = ConexionBD.GetActuadoresHabitacion(con);
            ps.setInt(1, id_habitacion);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Actuador actuador = new Actuador();
		actuador.setId_actuador(rs.getInt("id_actuador"));
                actuador.setTipo(rs.getString("tipo"));
                actuador.setId_habitacion(rs.getInt("id_habitacion_habitacion"));
                actuadores.add(actuador);
            }	
        } catch (SQLException e)
	{
            Log.log.error("Error: {}", e);
            actuadores = new ArrayList<Actuador>();
        } catch (NullPointerException e)
        {
            Log.log.error("Error: {}", e);
            actuadores = new ArrayList<Actuador>();
        } catch (Exception e)
        {
            Log.log.error("Error:{}", e);
            actuadores = new ArrayList<Actuador>();
        } finally
        {
            conector.closeConnection(con);
	}
        return actuadores;
    }
    
    
    /**
     * Registros estadísticos de una habitacion 
     * @param id_habitacion
     * @return ArrayList<Registro_sensor>
     */
    public static ArrayList<Registro_sensor> getRegistrosSensoresHabitacion(int id_habitacion){
        ArrayList<Registro_sensor> registros = new ArrayList<Registro_sensor>();
	ConexionBD conector = new ConexionBD();
	Connection con = null;
        try
        {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
		
            PreparedStatement ps = ConexionBD.GetRegistrosEstadisticosHabitacion(con);
            ps.setInt(1, id_habitacion);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Registro_sensor registro = new Registro_sensor();
		registro.setFecha(rs.getDate("fecha"));
                registro.setValor(rs.getFloat("valor"));
                registro.setHora(rs.getTimestamp("hora"));
                registro.setId_sensor_sensor(rs.getInt("id_sensor_sensor"));
                registros.add(registro);
            }	
        } catch (SQLException e)
	{
            Log.log.error("Error: {}", e);
            registros = new ArrayList<Registro_sensor>();
        } catch (NullPointerException e)
        {
            Log.log.error("Error: {}", e);
            registros = new ArrayList<Registro_sensor>();
        } catch (Exception e)
        {
            Log.log.error("Error:{}", e);
            registros = new ArrayList<Registro_sensor>();
        } finally
        {
            conector.closeConnection(con);
	}
        return registros;
    }

    

    /**
     * Registros de los actuadores de una habitacion
     * @param id_habitacion
     * @return ArrayList<Registro_actuador>
     */
    public static ArrayList<Registro_actuador> getRegistrosActuadoresHabitacion(int id_habitacion){
        ArrayList<Registro_actuador> registros = new ArrayList<Registro_actuador>();
	ConexionBD conector = new ConexionBD();
	Connection con = null;
        try
        {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
		
            PreparedStatement ps = ConexionBD.GetRegistrosActuadoresHabitacion(con);
            ps.setInt(1, id_habitacion);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Registro_actuador registro = new Registro_actuador();
		registro.setFecha_on(rs.getDate("fecha_on"));
                registro.setDuracion(rs.getFloat("duracion"));
                registro.setHora_on(rs.getTimestamp("hora_on"));
                registro.setId_actuador_actuador(rs.getInt("id_actuador_actuador"));
                registros.add(registro);
            }	
        } catch (SQLException e)
	{
            Log.log.error("Error: {}", e);
            registros = new ArrayList<Registro_actuador>();
        } catch (NullPointerException e)
        {
            Log.log.error("Error: {}", e);
            registros = new ArrayList<Registro_actuador>();
        } catch (Exception e)
        {
            Log.log.error("Error:{}", e);
            registros = new ArrayList<Registro_actuador>();
        } finally
        {
            conector.closeConnection(con);
	}
        return registros;
    }
    
    /**
     * Registros estadísticos de una habitación entre dos fechas
     * @param fechaini 
     * @param fechafin
     * @param cod_sistema
     * @return ArrayList<Registro_sensor>
     */
    public static ArrayList<Registro_sensor> getRegistrosSensoresHabitacionFecha(int id_habitacion, String fecha){
        ArrayList<Registro_sensor> registros = new ArrayList<Registro_sensor>();
	ConexionBD conector = new ConexionBD();
	Connection con = null;
        try
        {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
		
            PreparedStatement ps = ConexionBD.GetRegistrosEstadisticosHabitacion(con);
            ps.setInt(1, id_habitacion);
            ps.setDate(2, java.sql.Date.valueOf(fecha));
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Registro_sensor registro = new Registro_sensor();
		registro.setFecha(rs.getDate("fecha"));
                registro.setValor(rs.getFloat("valor"));
                registro.setHora(rs.getTimestamp("hora"));
                registro.setId_sensor_sensor(rs.getInt("id_sensor_sensor"));
                registros.add(registro);
            }	
        } catch (SQLException e)
	{
            Log.log.error("Error: {}", e);
            registros = new ArrayList<Registro_sensor>();
        } catch (NullPointerException e)
        {
            Log.log.error("Error: {}", e);
            registros = new ArrayList<Registro_sensor>();
        } catch (Exception e)
        {
            Log.log.error("Error:{}", e);
            registros = new ArrayList<Registro_sensor>();
        } finally
        {
            conector.closeConnection(con);
	}
        return registros;
    }

        
    /**
     * Registros de alertas en el sistema
     * @param cod_sistema
     * @return ArrayList<Alarma>
     */
    public static ArrayList<Alerta> getRegistrosAlertas(int cod_sistema){
        ArrayList<Alerta> registros_alertas = new ArrayList<Alerta>();
	ConexionBD conector = new ConexionBD();
	Connection con = null;
        try
        {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
		
            PreparedStatement ps = ConexionBD.GetRegistrosAlertas(con);
            ps.setInt(1, cod_sistema);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Alerta alerta = new Alerta();
		alerta.setId_alerta(rs.getInt("id_alerta"));
                alerta.setFecha(rs.getDate("fecha"));
                alerta.setHora(rs.getTimestamp("hora"));
                alerta.setInfo(rs.getString("info"));
                alerta.setCod_sistema_sistema_seguridad(rs.getInt("cpd_sistema_sistema_seguridad"));

                registros_alertas.add(alerta);
            }	
        } catch (SQLException e)
	{
            Log.log.error("Error: {}", e);
            registros_alertas = new ArrayList<Alerta>();
        } catch (NullPointerException e)
        {
            Log.log.error("Error: {}", e);
            registros_alertas = new ArrayList<Alerta>();
        } catch (Exception e)
        {
            Log.log.error("Error:{}", e);
            registros_alertas = new ArrayList<Alerta>();
        } finally
        {
            conector.closeConnection(con);
	}
        return registros_alertas;
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
     * @param cod_sistema
     * @return usuario
     */
    
    public static String getUsuarioQR (int cod_QR_leido, int cod_sistema){
        String usuario="";
        ConexionBD conector = new ConexionBD();
	Connection con = null;
        try
        {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConexionBD.GetUsuario_QR(con);
            ps.setInt(1,cod_QR_leido);
            ps.setInt(2, cod_sistema );
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                usuario=rs.getString("nombre");
            }
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
     * Habitaciones que tiene asociadas un sistema
     * @param cod_sistema
     * @retunr ArrayList<Habitacion>
     */
    
    public static ArrayList<Habitacion> getHabitacionesSistema(int cod_sistema){
        ArrayList<Habitacion> habitaciones = new ArrayList<Habitacion>();
        ConexionBD conector = new ConexionBD();
	Connection con = null;
        try
	{
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConexionBD.GetHabitacionesSistema(con);
            ps.setInt(1, cod_sistema);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();  
            while (rs.next())
            {
                Habitacion habitacion = new Habitacion();
		habitacion.setId_habitacion(rs.getInt("id_habitacion"));
                habitacion.setN_puertas(rs.getInt("n_puertas"));
                habitacion.setN_ventanas(rs.getInt("n_ventanas"));
                habitacion.setTamanno(rs.getInt("tamanno"));
                habitacion.setCod_sistema_sistema_seguridad(rs.getInt("cod_sistema_sistema_seguridad"));
                habitacion.setDescriptivo(rs.getString(rs.getString("descriptivo")));
                habitaciones.add(habitacion);
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
     * ID de la última alerta que se ha registrado para un sistema.
     * @param cod_sistema
     * @return int
     */
    
    public static int getUltimaAlerta(int cod_sistema){
        Alerta alerta = new Alerta();
	ConexionBD conector = new ConexionBD();
	Connection con = null;
        try
        {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
		
            PreparedStatement ps = ConexionBD.GetRegistrosAlertas(con);
            ps.setInt(1, cod_sistema);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            alerta.setId_alerta(rs.getInt("id_alerta"));
            alerta.setFecha(rs.getDate("fecha"));
            alerta.setHora(rs.getTimestamp("hora"));
            alerta.setInfo(rs.getString("info"));
            alerta.setCod_sistema_sistema_seguridad(rs.getInt("cpd_sistema_sistema_seguridad"));
	
        } catch (SQLException e)
	{
            Log.log.error("Error: {}", e);
            return -1; 
        } catch (NullPointerException e)
        {
            Log.log.error("Error: {}", e);
            return -1;
        } catch (Exception e)
        {
            Log.log.error("Error:{}", e);
            return -1; 
        } finally
        {
            conector.closeConnection(con);
	}
        return alerta.getId_alerta();
    }
    
    
    /**
     * Simulaciones de presencia registradas en el sistema 
     * @param cod_sistema
     * @param tipo
     */
    public static ArrayList<Registro_actuador> getSimulaciones(int cod_sistema){
        ArrayList<Registro_actuador> registros = new ArrayList<Registro_actuador>();
        ConexionBD conector = new ConexionBD();
	Connection con = null;
        try
	{
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConexionBD.GetSimulaciones(con);
            ps.setInt(1, cod_sistema);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();  
            while (rs.next())
            {
                Registro_actuador registroAct = new Registro_actuador();
		registroAct.setHora_on(rs.getTimestamp("hora_on"));
                registroAct.setFecha_on(rs.getDate("fecha_on"));
                registroAct.setDuracion(rs.getFloat("duracion"));
                registroAct.setId_actuador_actuador(rs.getInt("id_actuador_actuador"));
                registros.add(registroAct);
            }	
        } catch (SQLException e)
	{
            Log.log.error("Error: {}", e);
            registros = new ArrayList<Registro_actuador>();
	} catch (NullPointerException e)
	{
            Log.log.error("Error: {}", e);
            registros = new ArrayList<Registro_actuador>();
	} catch (Exception e)
	{
            Log.log.error("Error:{}", e);
            registros = new ArrayList<Registro_actuador>();
        } finally
        {
            conector.closeConnection(con);
	}
        return registros; 
    }

    
    /**
     * Devuelve el listado de fotos que hay registradas dada una fecha
     * @param fecha
     * @param cod_sistema
     * @return ArrayList<Registro_camara>
     */
    
    public static ArrayList<Registro_camara> getRegFotosDia(String fecha, int cod_sistema){
        ArrayList<Registro_camara> registros = new ArrayList<Registro_camara>();
        ConexionBD conector = new ConexionBD();
	Connection con = null;
        try
	{
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConexionBD.GetSimulaciones(con);
            ps.setDate(1, java.sql.Date.valueOf(fecha));
            ps.setInt(2, cod_sistema);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();  
            while (rs.next())
            {
                Registro_camara registroCam = new Registro_camara();
		registroCam.setFecha(rs.getDate("fecha"));
                registroCam.setEnlace_foto(rs.getString("foto"));
                registroCam.setHora(rs.getTimestamp("hora"));
                registroCam.setId_sensor_sensor(rs.getInt("id_sensor_sensor"));
                registros.add(registroCam);
            }	
        } catch (SQLException e)
	{
            Log.log.error("Error: {}", e);
            registros = new ArrayList<Registro_camara>();
	} catch (NullPointerException e)
	{
            Log.log.error("Error: {}", e);
            registros = new ArrayList<Registro_camara>();
	} catch (Exception e)
	{
            Log.log.error("Error:{}", e);
            registros = new ArrayList<Registro_camara>();
        } finally
        {
            conector.closeConnection(con);
	}
        return registros; 
    }
    
    
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
    
    /**
     * Insertar una alerta nueva en el sistema
     * @param alerta_nueva
     */
    //Ya veremos si es mejor con Topic como los registros de sensores y actuadores
        public static void insertarAlerta(Alerta alerta_nueva){ 
        ConexionBD conector = new ConexionBD();
	Connection con = null;
        try
	{
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConexionBD.InsertarAlerta(con);
            ps.setInt(1, alerta_nueva.getId_alerta());
            ps.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            ps.setTime(3, java.sql.Time.valueOf(LocalTime.now(ZoneId.of("Europe/Madrid"))));
            ps.setInt(4, alerta_nueva.getCod_sistema_sistema_seguridad());
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