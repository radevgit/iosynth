/**
 * 
 */
package net.iosynth.device;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import net.iosynth.sensor.Sensor;
import net.iosynth.sensor.SensorConstantString;
import net.iosynth.sensor.SensorCycleDouble01;
import net.iosynth.sensor.SensorCycleInt01;
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
		sensorAdapter.registerSubtype(SensorCycleInt01.class, "SensorCycleInt01");

		Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.registerTypeAdapterFactory(deviceAdapter)
				.registerTypeAdapterFactory(sensorAdapter)
				.create();
		
		Device devArr[] = new Device[2];
		devArr[0] = new DeviceFixedRate();
		devArr[1] = new DeviceFixedRate();
		String json = gson.toJson(devArr);
		System.out.println(json);
		
		
		Device[] devOut = gson.fromJson(json, Device[].class);
		int a=1;
	}
	
}
