package bbdd;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import logic.Log;


public class ConexionBD {
    
    public Connection obtainConnection(boolean autoCommit) throws NullPointerException {
        Connection con = null;
        int intentos = 5;
        for (int i = 0; i < intentos; i++) {
            Log.logbd.info("Intento {} de conectar con la base de datos", i);
            try {
                Context ctx = new InitialContext();
                // Get the connection factory configured in Tomcat
                DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/ubicomp");

                // Obtiene una conexion
                con = ds.getConnection();
                Calendar calendar = Calendar.getInstance();
                java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
                Log.logbd.info("Conexión creada. Identificación de la base de datos: {} obtenida en {}", con.toString(), date.toString());
                con.setAutoCommit(autoCommit);
                Log.logbd.info("Conexión obtenida en el intento: " + i);
                i = intentos;
            } catch (NamingException ex) {
                Log.logbd.error("Error realizando la conexión en: {} = {}", i, ex);
            } catch (SQLException ex) {
                Log.logbd.error("ERROR sql adquiriendo la conexión en el intento:{ }= {}", i, ex);
                throw (new NullPointerException("SQL connection is null"));
            }
        }
        return con;
    }

    public void closeTransaction(Connection con) {
        try {
            con.commit();
            Log.logbd.debug("Transacción cerrada");
        } catch (SQLException ex) {
            Log.logbd.error("Error cerrando la transacción: {}", ex);
        }
    }

    public void cancelTransaction(Connection con) {
        try {
            con.rollback();
            Log.logbd.debug("Transaction cancelada");
        } catch (SQLException ex) {
            Log.logbd.error("ERROR sql cancelando la transacción: {}", ex);
        }
    }

    public void closeConnection(Connection con) {
        try {
            Log.logbd.info("Closing the connection");
            if (null != con) {
                Calendar calendar = Calendar.getInstance();
                java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
                Log.logbd.debug("Connection closed. Bd connection identifier: {} obtained in {}", con.toString(), date.toString());
                con.close();
            }

            Log.logbd.info("The connection has been closed");
        } catch (SQLException e) {
            Log.logbd.error("ERROR sql closing the connection: {}", e);
            e.printStackTrace();
        }
    }

    public static PreparedStatement getStatement(Connection con, String sql) {
        PreparedStatement ps = null;
        try {
            if (con != null) {
                ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            }
        } catch (SQLException ex) {
            Log.logbd.warn("ERROR sql creating PreparedStatement:{} ", ex);
        }

        return ps;
    }

    //************** LLAMADAS A LA BASE DE DATOS ***************************//
    
    public static PreparedStatement GetSistemas(Connection con){
        return getStatement(con, "SELECT * FROM LMDL_BD.sistema_seguridad");
    }
    
    public static PreparedStatement GetSistemasCliente(Connection con){
        return getStatement(con, "SELECT * FROM LMDL_BD.sistema_seguridad where id_cliente_cliente=?");
    }
    
    
    public static PreparedStatement GetSistemaUsuario(Connection con) {
        return getStatement (con, "SELECT * FROM LMDL_BD.identificacion WHERE nombre=?");
    }

    
    public static PreparedStatement GetActuadores(Connection con){
        return getStatement(con, "SELECT * FROM LMDL_BD.actuador inner join LMDL_BD.habitacion on id_habitacion_habitacion=id_habitacion inner join "
                + "LMDL_BD.sistema_seguridad on cod_sistema=cod_sistema_sistema_seguridad WHERE cod_sistema=?");
    }
    
    public static PreparedStatement GetClientes(Connection con){
        return getStatement(con, "SELECT * FROM LMDL_BD.cliente");
    }
    public static PreparedStatement GetHabitaciones(Connection con){
        return getStatement(con, "SELECT * FROM LMDL_BD.habitacion");
    }
    
    public static PreparedStatement GetIdentificaciones (Connection con){
        return getStatement(con, "SELECT * FROM LMDL_BD.identificacion");
    }
    
    public static PreparedStatement GetIdentificacionesSistema(Connection con) {
        return getStatement(con, "SELECT * FROM LMDL_BD.identificacion WHERE cod_sistema_sistema_seguridad=?");
    }
    
    public static PreparedStatement GetRegistrosActuadores(Connection con){
        return getStatement(con, "SELECT * FROM LMDL_BD.registro_actuador");
    }
    
    public static PreparedStatement GetRegistrosCamaras(Connection con){
        return getStatement(con, "SELECT * FROM LMDL_BD.registro_camara");
    }
    
    public static PreparedStatement GetSensores(Connection con){
        return getStatement(con, "SELECT * FROM LMDL_BD.sensor");
    }
    
    
    public static PreparedStatement GetSensoresHabitacion (Connection con){
        return getStatement (con, "SELECT * FROM LMDL_BD.sensor WHERE id_habitacion_habitacion=?");
    }
    
    public static PreparedStatement GetActuadoresHabitacion (Connection con){
        return getStatement (con, "SELECT * FROM LMDL_BD.actuador WHERE id_habitacion_habitacion=?");
    }
    
    public static PreparedStatement GetRegistrosEstadisticosHabitacion(Connection con){
        return getStatement (con, "SELECT fecha, valor, hora, id_sensor_sensor FROM LMDL_BD.registro_estadistico "
                + "INNER JOIN LMDL_BD.sensor on id_sensor_sensor=id_sensor INNER JOIN LMDL_BD.habitacion on "
                + "id_habitacion_habitacion=id_habitacion WHERE id_habitacion=?");
    }
    
    public static PreparedStatement GetRegistrosActuadoresSistema(Connection con){
        return getStatement(con,"SELECT hora_on, fecha_on, duracion, id_actuador_actuador FROM LMDL_BD.registro_actuador "
                + "INNER JOIN LMDL_BD.actuador on id_actuador_actuador=id_actuador INNER JOIN LMDL_BD.habitacion on "
                + "id_habitacion_habitacion=id_habitacion  INNER JOIN "
                + "LMDL_BD.sistema_seguridad on cod_sistema = cod_sistema_sistema_seguridad WHERE cod_sistema=?" );
    }
    
    public static PreparedStatement GetRegistrosEstadisticosHabitacionFecha(Connection con){
        return getStatement(con, "SELECT fecha, valor, hora, id_sensor_sensor FROM LMDL_BD.registro_estadistico "
                + "INNER JOIN LMDL_BD.sensor on id_sensor_sensor=id_sensor "
                + "INNER JOIN LMDL_BD.habitacion on id_habitacion_habitacion=id_habitacion "
                + "WHERE id_habitacion_habitacion=? and fecha>=? and fecha <=?" ); //Desde x fecha hasta y fecha 
        }
    
    public static PreparedStatement GetUltimoRegistroSensor(Connection con){
        return getStatement(con, "SELECT * FROM LMDL_BD.registro_estadistico where"
                + " id_sensor_sensor=? order by fecha desc limit 1");
    }
    
    public static PreparedStatement GetRegistrosAlertas(Connection con){
        return getStatement(con, "SELECT * FROM LMDL_BD.alerta "
                + "INNER JOIN LMDL_BD.sistema_seguridad on cod_sistema=cod_sistema_sistema_seguridad where cod_sistema=?");
    }
    
    public static PreparedStatement GetContrasenaUsuario(Connection con){
        return getStatement(con, "SELECT password from LMDL_BD.identificacion WHERE nombre=? ");
    }
    
    public static PreparedStatement GetUsuario_QR(Connection con){
        return getStatement (con, "SELECT nombre from LMDL_BD.identificacion WHERE codigo_qr=? and cod_sistema_sistema_seguridad=?");
    }
    
    //Pendiente de borrar porque si al final la relación entre sistema_seguridad e identificacion es 1:n, no hace falta. Por seguridad, lo que tiene sentido es que sea así. 
    public static PreparedStatement GetSistemasUsuario(Connection con){
        return getStatement (con, "SELECT nombre from LMDL_BD.sistema_seguridad inner join LMDL_BD.identificacion ON cod_sistema=cod_sistema_sistema_seguridad where identificacion.nombre=?");
    }
    
    
    public static PreparedStatement GetHabitacionesSistema(Connection con){
        return getStatement(con, "SELECT * FROM LMDL_BD.habitacion WHERE "
                + "cod_sistema_sistema_seguridad=?");
    }
    
    //Para añadir las alertas con id correlativos
    public static PreparedStatement GetIdUltimaAlerta(Connection con){
        return getStatement(con, "SELECT id_alerta FROM LMDL_BD.alerta where cod_sistema_sistema_seguridad=? order by id_alerta desc limit 1");
    }
    
    public static PreparedStatement GetSimulaciones(Connection con){
        return getStatement(con, "SELECT * FROM LMDL_BD.registro INNER JOIN LMDL_BD.actuador ON id_actuador_actuador=id_actuador "
                + "INNER JOIN LMDL_BD.sistema_seguridad on cod_sistema=cod_sistema_sistema_seguridad "
                + "WHERE cod_sistema=? and tipo='zumbador'"); //Importante registrar los zumbadores con tipo=zumbador. 
    }
    
    public static PreparedStatement GetRegFotosDia(Connection con){
        return getStatement(con, "SELECT fecha, foto, hora, id_sensor_sensor FROM LMDL_BD.registro_camara INNER JOIN LMDL_BD.sensor ON id_sensor=id_sensor_sensor"
                + "INNER JOIN LMDL_BD.sistema_seguridad on cod_sistema=cod_sistema_sistema_seguridad WHERE fecha=? and cod_sistema=?");
    }
    
    public static PreparedStatement InsertarRegistroSensor(Connection con) {
        return getStatement(con, "INSERT INTO LMDL_BD.registro_estadistico (fecha, valor, hora, id_sensor_sensor) VALUES (?,?,?,?)");
    }
    
    public static PreparedStatement InsertarRegistroActuador(Connection con){
        return getStatement(con, "INSERT INTO LMDL_BD.registro (hora_on, fecha_on, duracion, id_actuador_actuador) VALUES (?,?,?,?)");
    }
    
    public static PreparedStatement InsertarAlerta(Connection con){
        return getStatement(con, "INSERT INTO LMDL_BD.alerta (id_alerta, fecha, hora, info, cod_sistema_sistema_seguridad) VALUES (?,?,?,?,?)");
    }
    
    public static PreparedStatement CambiarEstadoSistema(Connection con){
        return getStatement(con, "UPDATE LMDL_BD.sistema_seguridad set estado=? where cod_sistema=?");
    }
        
    public static PreparedStatement GetEstadoSistema(Connection con){
        return getStatement(con, "SELECT estado from LMDL_BD.sistema_seguridad where cod_sistema=?");
    }
    
    public static PreparedStatement InsertarIdentificacion(Connection con){
        return getStatement(con, "INSERT INTO LMDL_BD.identificacion VALUES (?,?,?,?,?)");
    }
    
    public static PreparedStatement BorrarIdentificacion (Connection con){
        return getStatement(con, "DELETE FROM LMDL_BD.identificacion WHERE nombre=?");
    }
}