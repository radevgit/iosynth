/**
 * 
 */
package net.iosynth.util;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



/**
 * @author rradev
 *
 */
public class JsonDevice {
	String UUID;

	
	/**
	 * 
	 */
	public JsonDevice() {

	}
	
	public void add(String name, JsonSensor sen){
		
	}

	
	public static void main(String[] args) {
		JsonDevice d = new JsonDevice();
		
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String str = gson.toJson(d);
		System.out.println(str);
		
		JsonDevice d2 = gson.fromJson(str, JsonDevice.class);
		System.out.println(gson.toJson(d2));
		

	}

}
