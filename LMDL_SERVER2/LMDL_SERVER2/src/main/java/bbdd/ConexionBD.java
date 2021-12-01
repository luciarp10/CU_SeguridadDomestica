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
    
    public static PreparedStatement GetActuadores(Connection con){
        return getStatement(con, "SELECT * FROM LMDL_BD.actuador");
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
    
    public static PreparedStatement GetRegistrosActuadores(Connection con){
        return getStatement(con, "SELECT * FROM LMDL_BD.registro");
    }
    
    public static PreparedStatement GetRegistrosCamaras(Connection con){
        return getStatement(con, "SELECT * FROM LMDL_BD.registro_camara");
    }
    
    public static PreparedStatement GetSensores(Connection con){
        return getStatement(con, "SELECT * FROM LMDL_BD.sensor");
    }
    
    
    public static PreparedStatement InsertarRegistroSensor(Connection con) {
        return getStatement(con, "INSERT INTO LMDL_BD.registro_estadistico (fecha, valor, hora, id_sensor_sensor) VALUES (?,?,?,?)");
    }
    
    public static PreparedStatement InsertarRegistroActuador(Connection con){
        return getStatement(con, "INSERT INTO LMDL_BD.registro (hora_on, fecha_on, duracion, id_actuador_actuador) VALUES (?,?,?,?)");
    }
    /*
    
    public static PreparedStatement GetStations(Connection con) {
        return getStatement(con, "SELECT * FROM WHEATHERSTATION.STATION");
    }

    public static PreparedStatement GetStationsFromCity(Connection con) {
        return getStatement(con, "SELECT * FROM WHEATHERSTATION.STATION WHERE CITY_ID=?");
    }

    public static PreparedStatement GetStationSensors(Connection con) {
        return getStatement(con, "SELECT * FROM SENSORTYPE LEFT OUTER JOIN STATION_SENSORTYPE ON SENSORTYPE.ID=STATION_SENSORTYPE.SENSORTYPE_ID and STATION_ID=?;");
    }

    public static PreparedStatement GetStationSensorMeasurementLastDays(Connection con) {
        return getStatement(con, "SELECT date(DATE) as date, min(VALUE) as min, max(VALUE) as max, avg(VALUE) as avg, dayofweek(DATE) as dayofweek FROM MEASUREMENT WHERE STATION_ID=? AND SENSORTYPE_ID=? and date(DATE)>=date(now()) - INTERVAL ? DAY and DATE<=now() group by date(DATE) ORDER BY DATE ASC;");
    }

    public static PreparedStatement GetStationSensorMeasurementLastMonths(Connection con) {
        return getStatement(con, "SELECT month(DATE) as month,min(VALUE) as min, max(VALUE) as max, avg(VALUE) as avg FROM MEASUREMENT WHERE STATION_ID=? AND SENSORTYPE_ID=? and date(DATE)>=date(now()) - INTERVAL ? DAY group by month(DATE) ORDER BY DATE ASC;");
    }

    public static PreparedStatement GetCities(Connection con) {
        return getStatement(con, "SELECT * FROM CITY;");
    }

    public static PreparedStatement InsertWeatherForecast(Connection con) {
        return getStatement(con, "INSERT INTO MEASUREMENT (STATION_ID, SENSORTYPE_ID, DATE, VALUE) VALUES (?,?,?,?) ON duplicate key update STATION_ID=?, SENSORTYPE_ID=?, DATE=?, VALUE=?;");
    }

    public static PreparedStatement GetStationSensorMeasurementMonth(Connection con) {
        return getStatement(con, "SELECT month(DATE) as date,  min(VALUE) as min, max(VALUE) as max, avg(VALUE) as avg FROM MEASUREMENT WHERE STATION_ID=? AND SENSORTYPE_ID=? group by month(DATE) ORDER BY DATE ASC;");
    }

    public static PreparedStatement GetLastValueStationSensor(Connection con) {
        return getStatement(con, "select * from MEASUREMENT where STATION_ID=? AND SENSORTYPE_ID= ? ORDER BY DATE LIMIT 1;");
    }

    public static PreparedStatement GetInfoFromStation(Connection con) {
        return getStatement(con, "SELECT * FROM WHEATHERSTATION.STATION WHERE ID=?;");
    }

    public static PreparedStatement InsertnewMeasurement(Connection con) {
        return getStatement(con, "INSERT INTO MEASUREMENT (STATION_ID, SENSORTYPE_ID, DATE, VALUE) VALUES (?,?,?,?) ON duplicate key update STATION_ID=?, SENSORTYPE_ID=?, DATE=?, VALUE=?;");
    }


    public static PreparedStatement GetDataBD(Connection con) {
        return getStatement(con, "SELECT * FROM UBICOMP.MEASUREMENT");
    }

    public static PreparedStatement SetDataBD(Connection con) {
        return getStatement(con, "INSERT INTO UBICOMP.MEASUREMENT VALUES (?,?)");
    }*/
}