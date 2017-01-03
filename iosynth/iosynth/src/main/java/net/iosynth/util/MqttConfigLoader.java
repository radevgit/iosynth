/**
 * 
 */
package net.iosynth.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import net.iosynth.adapter.MqttConfigClass;

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
		//MqttConfig cfg = new MqttConfig();
		Gson gson = new Gson();
		String json = null;;
		try {
			json = new String(Files.readAllBytes(Paths.get("/home/ross/tmp/MqttConfig.json")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MqttConfigClass cfg = gson.fromJson(json, MqttConfigClass.class);
		System.out.println(gson.toJson(cfg));
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MqttConfigLoader c = new MqttConfigLoader();
		c.DeviceFromJson();

	}

}
