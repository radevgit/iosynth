/**
 * 
 */
package net.iosynth.device;

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
public class DeviceFromJson {

	
	public DeviceFromJson(){
		
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream("/home/ross/tmp/Device.json");
			Map<String, Object> args = new HashMap<String, Object>();
			args.put(JsonReader.USE_MAPS, true);
			Object obj = JsonReader.jsonToJava(inputStream, args);
			int a = 1;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DeviceFromJson d = new DeviceFromJson();

	}

}
