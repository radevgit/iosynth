/**
 * 
 */
package net.iosynth.util;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.iosynth.device.Device;
import net.iosynth.sensor.Sensor;

/**
 * @author rradev
 *
 */
public class DeviceFixedRate extends Device {
	String deviceType;
	/**
	 * 
	 */
	public DeviceFixedRate() {
		deviceType = DeviceFixedRate.class.getSimpleName();
		addSensor("prime");
	}

	/* (non-Javadoc)
	 * @see net.iosynth.device.Device#run()
	 */
	@Override
	public void run() {
		for(Map.Entry<String, Sensor> sen : sensors.entrySet()) {
		    String name = sen.getKey();
		    Sensor sensor = sen.getValue();
		    sensor.step(1);
		}
		getQueue().add(toJson());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DeviceFixedRate d = new DeviceFixedRate();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String str = gson.toJson(d);
		System.out.println(str);
		
		Device d2 = gson.fromJson(str, DeviceFixedRate.class);
		System.out.println(gson.toJson(d2));
	}

}
