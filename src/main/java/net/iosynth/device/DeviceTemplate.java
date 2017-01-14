/**
 * 
 */
package net.iosynth.device;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.iosynth.sensor.Sensor;


/**
 * @author rradev
 *
 */
public class DeviceTemplate {
	private String jsonIn;
	private String template[];
	private int    idx[];
	Logger logger = Logger.getLogger(DeviceTemplate.class.getName());
	
	/**
	 * @param jsonFileName 
	 * 
	 */
	public DeviceTemplate(String jsonFileName, Sensor sensors[]) {
		jsonIn = null;
		try {
			jsonIn = new String(Files.readAllBytes(Paths.get(jsonFileName)));
		} catch (IOException ie) {
			logger.log(Level.SEVERE, ie.toString(), ie);
			System.exit(1);
		}
		idx = new int[sensors.length];
		parseJson(jsonIn);
		if(template.length - 1 != sensors.length){
			logger.severe("Sensors does not match json template");
			System.exit(1);
		}
		matchVariables(sensors);
		jsonIn = null;
	}
	
	/**
	 * @param jsonIn
	 */
	public void parseJson(String jsonIn){
		template = jsonIn.split("\"\\$");
	}
	
	/**
	 * @param sensors
	 */
	public void matchVariables(Sensor sensors[]){
		String tmp[];
		for (int i = 1; i < template.length; i++) {
			tmp = template[i].split("\"", 2);
			if (tmp.length < 2) {
				logger.severe("Parser error: " + template[i]);
				System.exit(1);
			}
			template[i] = tmp[1];
			for (int j = 0; j < sensors.length; j++) { // find sensor mapping
				String str = sensors[j].getName();
				if (str.equals("$" + tmp[0])) {
					idx[i - 1] = j;
					break;
				}
			}
		}
	}
	
	/**
	 * @param sensors
	 * @return device json
	 */
	public String getJson(Sensor sensors[]){
		StringBuilder b = new StringBuilder(4096);
		b.append(template[0]);
		for(int i = 0; i < sensors.length; i++){
			b.append(sensors[i].getString()).append(template[i+1]);
		}
		return b.toString();
	}
    
}
