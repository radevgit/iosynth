/**
 * 
 */
package net.iosynth.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;


/**
 * @author rradev
 *
 */
public class MqttConfigLoader {

	/**
	 * 
	 */
	public MqttConfigLoader() {
		// TODO Auto-generated constructor stub
	}
	
	public void DeviceFromJson(){

		MqttConfig cfg = new MqttConfig();
		Map<String,Object> args1 = new HashMap<String, Object>();
		args1.put(JsonWriter.PRETTY_PRINT, true);
		//args1.put(JsonWriter.TYPE, false);
		String json = JsonWriter.objectToJson(cfg, args1);
		System.out.println(json);
		System.out.println("end");
		
		InputStream inputStream = null;
		/*
		try {
			inputStream = new FileInputStream("/home/ross/tmp/MqttConfig.json");
			Map<String, Object> args = new HashMap<String, Object>();
			//args.put(JsonReader.USE_MAPS, true);
			MqttConfig obj = (MqttConfig) JsonReader.jsonToJava(inputStream, args);
			int a = 1;
			obj.show();
			
			
			//Map<String,Object> args1 = new HashMap<String, Object>();
			//args1.put(JsonWriter.PRETTY_PRINT, true);
			//String json = JsonWriter.objectToJson(cfg, args1);
			//System.out.println(json);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MqttConfigLoader c = new MqttConfigLoader();
		c.DeviceFromJson();

	}

}
