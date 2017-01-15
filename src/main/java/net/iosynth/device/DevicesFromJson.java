/**
 * 
 */
package net.iosynth.device;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.iosynth.sensor.Sensor;
import net.iosynth.sensor.SensorString;
import net.iosynth.sensor.SensorDoubleCycle;
import net.iosynth.sensor.SensorIntCycle;
import net.iosynth.sensor.SensorStringCycle;
import net.iosynth.sensor.SensorDefault;
import net.iosynth.sensor.SensorEpoch;
import net.iosynth.sensor.SensorDoubleRandom;
import net.iosynth.sensor.SensorIntRandom;
import net.iosynth.sensor.SensorStringRandom;
import net.iosynth.sensor.SensorTimestamp;
import net.iosynth.util.RuntimeTypeAdapterFactory;
import net.iosynth.util.Xoroshiro128;


/**
 * @author rradev
 *
 */
public class DevicesFromJson {
	private static Gson gson;
	
	private final Logger logger = Logger.getLogger(DevicesFromJson.class.getName());
	/**
	 * 
	 */
	public DevicesFromJson() {
		
	}
	
	private static Gson getParser(){
		if (gson != null) {
			return gson;
		}
		final net.iosynth.util.RuntimeTypeAdapterFactory<Device> deviceAdapter = RuntimeTypeAdapterFactory.of(Device.class, "type");
		//deviceAdapter.registerSubtype(Device.class, "Device");
		deviceAdapter.registerSubtype(DeviceSimple.class, "DeviceSimple");
		
		
		final RuntimeTypeAdapterFactory<Sensor> sensorAdapter = RuntimeTypeAdapterFactory.of(Sensor.class, "type");
		sensorAdapter.registerSubtype(SensorString.class,         "SensorString");
		sensorAdapter.registerSubtype(SensorEpoch.class,          "SensorEpoch");
		sensorAdapter.registerSubtype(SensorTimestamp.class,      "SensorTimestamp");
		
		sensorAdapter.registerSubtype(SensorDefault.class,        "SensorDefault");
		
		sensorAdapter.registerSubtype(SensorDoubleCycle.class,    "SensorDoubleCycle");
		sensorAdapter.registerSubtype(SensorIntCycle.class,       "SensorIntCycle");
		sensorAdapter.registerSubtype(SensorStringCycle.class,    "SensorStringCycle");
		
		sensorAdapter.registerSubtype(SensorDoubleRandom.class,   "SensorDoubleRandom");
		sensorAdapter.registerSubtype(SensorIntRandom.class,      "SensorIntRandom");
		sensorAdapter.registerSubtype(SensorStringRandom.class,   "SensorStringRandom");
		
		final RuntimeTypeAdapterFactory<Sampling> samplingAdapter = RuntimeTypeAdapterFactory.of(Sampling.class, "type");
		//samplingAdapter.registerSubtype(Sampling.class, "Sampling");
		samplingAdapter.registerSubtype(SamplingFixed.class, "SamplingFixed");
		samplingAdapter.registerSubtype(SamplingUniform.class, "SamplingUniform");
		
		//RuntimeTypeAdapterFactory<DeviceCopy> copyAdapter = RuntimeTypeAdapterFactory.of(DeviceCopy.class, "type");
		//copyAdapter.registerSubtype(DeviceCopySimple.class, "CopySimple");
		


		final Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.registerTypeAdapterFactory(deviceAdapter)
				.registerTypeAdapterFactory(sensorAdapter)
				.registerTypeAdapterFactory(samplingAdapter)
				//.registerTypeAdapterFactory(copyAdapter)
				.create();
		return gson;
	}
	
	/**
	 * @param json
	 * @param seed 
	 * @return List of devices from json definition
	 */
	public List<Device> build(String json, long seed){
		final Gson gson = getParser();
		Device[] devIn = gson.fromJson(json, Device[].class);
		
		for(final Device dev: devIn){
			dev.checkParameters();
		}
		
		if(seed == 2052703995999047696L){ // magic number
			seed = System.currentTimeMillis();
		}
		Xoroshiro128 rnd = new Xoroshiro128(seed);
		
		int devCount = 0;
		List<Device> devOut = new ArrayList<Device>();
		for(int i=0; i<devIn.length; i++){
			devIn[i].setRnd(rnd);
			List<Device> devList = devIn[i].replicate();
			DeviceTemplate devTempate = new DeviceTemplate(devIn[i].getJson_template(), devIn[i].getSensors());
			for(Device dev: devList){
				dev.setDeviceTemplate(devTempate);
				dev.setJson_template(null);
			}
			devOut.addAll(devList);
			rnd = devList.get(devList.size() - 1).getRnd().copy();  // get the last generated rnd
			rnd.jump();
			devCount = devCount + devList.size();
			devList = null;
		}
		devIn = null; // clear the initial array
		logger.info("Devices created: " + String.valueOf(devCount));
		return devOut;
	}

	/** 
	 * Hack to parse. If in array it is ok, if single object parse err.
	 * @param dev 
	 * @param count 
	 * @return List of replica devices
	 */
	public static List<Device> copyDevice(Device dev[], int count){
		List<Device> devList = new ArrayList<Device>();
		final Gson gson = getParser();
		final String json = gson.toJson(dev);
		for (int i = 0; i < count; i++) {
			Device[] devT = gson.fromJson(json, Device[].class);
			devList.add(devT[0]);
		}

		return devList;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Gson gson = getParser();
		
		
		Device[] devIn = gson.fromJson(test, Device[].class);
		for(final Device dev: devIn){
			dev.checkParameters();
		}
		
		List<Device> devOut = new ArrayList<Device>();
		//for(int i=0; i<devIn.length; i++){
		//	List<Device> devList = devIn[i].replicate(); 
		//	devOut.addAll(devList);
		//}
		
		System.out.println(gson.toJson(devIn));
		System.out.println("___________________");
		System.out.println(gson.toJson(devOut));
	}
	
	static String test = "[{'type':'DeviceSimple','uuid':'xxx', 'copy':2, 'sensors':[{'type':'SensorTimestamp'}, {'type':'SensorDoubleRandom', 'min':5}]   }]";
	
}
