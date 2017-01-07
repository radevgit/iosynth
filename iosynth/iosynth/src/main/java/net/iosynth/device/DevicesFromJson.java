/**
 * 
 */
package net.iosynth.device;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import net.iosynth.sensor.Sensor;
import net.iosynth.sensor.SensorConstantString;
import net.iosynth.sensor.SensorCycleDouble;
import net.iosynth.sensor.SensorCycleInt;
import net.iosynth.sensor.SensorCycleString;
import net.iosynth.sensor.SensorDefault;
import net.iosynth.sensor.SensorRandomDouble;
import net.iosynth.sensor.SensorRandomInt;
import net.iosynth.sensor.SensorRandomString;
import net.iosynth.util.DeepCopy;

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
		sensorAdapter.registerSubtype(SensorCycleDouble.class,    "SensorCycleDouble");
		sensorAdapter.registerSubtype(SensorCycleInt.class,       "SensorCycleInt");
		sensorAdapter.registerSubtype(SensorCycleString.class,    "SensorCycleString");
		sensorAdapter.registerSubtype(SensorDefault.class,        "SensorDefault");
		sensorAdapter.registerSubtype(SensorRandomDouble.class,   "SensorRandomDouble");
		sensorAdapter.registerSubtype(SensorRandomInt.class,      "SensorRandomInt");
		sensorAdapter.registerSubtype(SensorRandomString.class,   "SensorRandomString");
		
		RuntimeTypeAdapterFactory<Arrival> arrivalAdapter = RuntimeTypeAdapterFactory.of(Arrival.class, "type");
		//arrivalAdapter.registerSubtype(Arrival.class, "Arrival");
		arrivalAdapter.registerSubtype(ArrivalFixed.class, "ArrivalFixed");
		arrivalAdapter.registerSubtype(ArrivalUniform.class, "ArrivalUniform");
		
		//RuntimeTypeAdapterFactory<DeviceCopy> copyAdapter = RuntimeTypeAdapterFactory.of(DeviceCopy.class, "type");
		//copyAdapter.registerSubtype(DeviceCopySimple.class, "CopySimple");
		


		Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.registerTypeAdapterFactory(deviceAdapter)
				.registerTypeAdapterFactory(sensorAdapter)
				.registerTypeAdapterFactory(arrivalAdapter)
				//.registerTypeAdapterFactory(copyAdapter)
				.create();
		return gson;
	}
	
	public List<Device> build(String json){
		Gson gson = getParser();
		Device[] devIn = gson.fromJson(json, Device[].class);
		
		for(final Device dev: devIn){
			dev.checkParameters();
		}
		
		int devCount = 0;
		List<Device> devOut = new ArrayList<Device>();
		for(int i=0; i<devIn.length; i++){
			List<Device> devList = devIn[i].replicate(); 
			devOut.addAll(devList);
			devCount = devCount + devList.size();
		}
		System.out.println("Devices created: " + String.valueOf(devCount));
		return devOut;
	}


	
	public static void main(String[] args) {
		DevicesFromJson d = new DevicesFromJson();
		Gson gson = d.getParser();
		
		
		Device[] devIn = gson.fromJson(test, Device[].class);
		for(final Device dev: devIn){
			dev.checkParameters();
		}
		
		List<Device> devOut = new ArrayList<Device>();
		for(int i=0; i<devIn.length; i++){
			List<Device> devList = devIn[i].replicate(); 
			devOut.addAll(devList);
		}
		
		DeepCopy deep = new DeepCopy();
		System.out.println(gson.toJson(devIn));
		System.out.println("___________________");
		System.out.println(gson.toJson(devOut));
	}
	
	static String test = "[{'type':'DeviceSimple','uuid':'xxx', 'copy':2, 'sensors':[{'type':'SensorRandomDouble01', 'min':5}]   }]";
	
}
