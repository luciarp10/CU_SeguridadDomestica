package logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Log
{
	public static Logger log = LogManager.getLogger("log");
	public static Logger logbd = LogManager.getLogger("logbd");
	public static Logger logmqtt = LogManager.getLogger("logmqtt");
}