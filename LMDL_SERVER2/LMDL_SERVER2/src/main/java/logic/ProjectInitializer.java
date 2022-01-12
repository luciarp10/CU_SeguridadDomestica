package logic;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import mqtt.MqttBroker;
import mqtt.MqttSuscriber;

/**
 *	ES: Clase encargada de inicializar el sistema
 *	
 */
@WebListener
public class ProjectInitializer implements ServletContextListener 
{
	
	@Override
	public void contextDestroyed(ServletContextEvent sce)
	{
	}
	
	@Override
	/**
	 *	ES: Metodo empleado para detectar la inicializacion del servidor<br>
	 * 	ES: Evento de contexto creado durante el arranque del servidor	<br>
	 */
	public void contextInitialized(ServletContextEvent sce)
	{
		Log.log.info("-->Suscribiendo topics<--");
		MqttBroker broker = new MqttBroker();
		MqttSuscriber suscriber = new MqttSuscriber();
		suscriber.searchTopicsToSuscribe(broker);
                
		Log.log.info("-->Iniciando el sistema<--");
		
	}	
}