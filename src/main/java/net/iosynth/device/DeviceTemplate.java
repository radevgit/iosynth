/**
 * 
 */
package net.iosynth.device;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.IllegalFormatException;
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
	 * @param sensors 
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
		parseJson(jsonIn);
		idx = new int[template.length-1];
		for(int i=0; i<idx.length; i++){
			idx[i] = 7777; // magic number
		}
		matchVariables(sensors);
		jsonIn = null;
	}
	
	/**
	 * @param jsonIn
	 */
	public void parseJson(String jsonIn){
		template = jsonIn.split("\\{\\$");  // "{$variable}"
	}
	
	/**
	 * @param sensors
	 */
	public void matchVariables(Sensor sensors[]){
		String tmp[];
		for (int i = 1; i < template.length; i++) {
			tmp = template[i].split("}", 2);
			if (tmp.length < 2) {
				logger.severe("Parser error: " + template[i]);
				System.exit(1);
			}
			template[i] = tmp[1];
			final String match = "{$" + tmp[0] + "}"; 
			for (int j = 0; j < sensors.length; j++) { // find sensor mapping
				String str = sensors[j].getName();
				if (str.equals(match)) {
					idx[i - 1] = j;
					break;
				}
			}
			if(idx[i-1] == 7777){
				logger.severe("Cannot match template and sensors:" + match);
				System.exit(1);
			}
		}
	}
	
	/**
	 * @param sensors
	 * @return device json
	 */
	public String getJson(Sensor sensors[]) {
		StringBuilder b = new StringBuilder(4096);
		b.append(template[0]);
		for (int i = 0; i < sensors.length; i++) {
			try {
				b.append(sensors[i].getString()).append(template[i + 1]);
			} catch (IllegalFormatException e) {
				logger.severe("Illegal sensor format: " + sensors[i].getName() + " " + sensors[i].getFormat());
			}
		}

		return b.toString();
	}
    
}
