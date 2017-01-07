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
import net.iosynth.sensor.SensorCycleString01;
import net.iosynth.sensor.SensorDefault;
import net.iosynth.sensor.SensorRandomDouble01;
import net.iosynth.sensor.SensorRandomInt01;
import net.iosynth.sensor.SensorRandomString01;

/**
 * @author rradev
 *
 */
public class DevicesFromJson {

	/**
	 * 
	 */
	public DevicesFromJson() {
		// TODO Auto-generated constructor stub
	}
	
	public Gson getParser(){
		RuntimeTypeAdapterFactory<Device> deviceAdapter = RuntimeTypeAdapterFactory.of(Device.class, "type");
		//deviceAdapter.registerSubtype(Device.class, "Device");
		deviceAdapter.registerSubtype(DeviceFixedRate.class, "DeviceSimple");
		
		
		RuntimeTypeAdapterFactory<Sensor> sensorAdapter = RuntimeTypeAdapterFactory.of(Sensor.class, "type");
		sensorAdapter.registerSubtype(SensorConstantString.class, "SensorConstantString");
		sensorAdapter.registerSubtype(SensorCycleDouble01.class,  "SensorCycleDouble01");
		sensorAdapter.registerSubtype(SensorCycleInt01.class,     "SensorCycleInt01");
		sensorAdapter.registerSubtype(SensorCycleString01.class,  "SensorCycleString01");
		sensorAdapter.registerSubtype(SensorDefault.class,        "SensorDefault");
		sensorAdapter.registerSubtype(SensorRandomDouble01.class, "SensorRandomDouble01");
		sensorAdapter.registerSubtype(SensorRandomInt01.class,    "SensorRandomInt01");
		sensorAdapter.registerSubtype(SensorRandomString01.class, "SensorRandomString01");
		
		RuntimeTypeAdapterFactory<Arrival> arrivalAdapter = RuntimeTypeAdapterFactory.of(Arrival.class, "type");
		//arrivalAdapter.registerSubtype(Arrival.class, "Arrival");
		arrivalAdapter.registerSubtype(ArrivalFixed.class, "ArrivalFixed");
		arrivalAdapter.registerSubtype(ArrivalUniform.class, "ArrivalUniform");

		Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.registerTypeAdapterFactory(deviceAdapter)
				.registerTypeAdapterFactory(sensorAdapter)
				.registerTypeAdapterFactory(arrivalAdapter)
				.create();
		return gson;
	}
	
	public Device[] build(String json){
		Gson gson = getParser();
		Device[] devIn = gson.fromJson(json, Device[].class);
		
		for(final Device dev: devIn){
			dev.checkParameters();
		}
		
		// TODO fix for replication and check the configuration.
		Device[] devOut = new Device[devIn.length];
		for(int i=0; i<devIn.length; i++){
			devOut[i] = devIn[i];
		}
		return devOut;
	}


	
	public static void main(String[] args) {
		DevicesFromJson d = new DevicesFromJson();
		Gson gson = d.getParser();
		
		
		Device[] devOut = gson.fromJson(test, Device[].class);
		for(final Device dev: devOut){
			dev.checkParameters();
		}
		
		System.out.println(gson.toJson(devOut));
	}
	
	static String test = "[{'type':'DeviceSimple','uuid':'', 'sensors':[{'type':'SensorRandomDouble01', 'min':5}]   }]";
	
}
