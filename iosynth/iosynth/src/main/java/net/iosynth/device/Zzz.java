/**
 * 
 */
package net.iosynth.device;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import net.iosynth.sensor.Sensor;
import net.iosynth.sensor.SensorConstantString;
import net.iosynth.sensor.SensorCycleDouble01;
import net.iosynth.sensor.SensorDefault;

/**
 * @author rradev
 *
 */
public class Zzz {

	/**
	 * 
	 */
	public Zzz() {
		// TODO Auto-generated constructor stub
	}


	
	public static void main(String[] args) {
		RuntimeTypeAdapterFactory<Device> deviceAdapter = RuntimeTypeAdapterFactory.of(Device.class, "type");
		deviceAdapter.registerSubtype(DeviceFixedRate.class, "DeviceFixedRate");
		
		
		RuntimeTypeAdapterFactory<Sensor> sensorAdapter = RuntimeTypeAdapterFactory.of(Sensor.class, "type");
		sensorAdapter.registerSubtype(SensorDefault.class, "SensorDefault");

		Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.registerTypeAdapterFactory(deviceAdapter)
				.registerTypeAdapterFactory(sensorAdapter)
				.create();
		
		DeviceFixedRate dev = new DeviceFixedRate();
		
		System.out.println(gson.toJson(dev));
	}
	
}
