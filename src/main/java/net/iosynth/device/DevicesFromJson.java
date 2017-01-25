/**
 * 
 */
package net.iosynth.device;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.iosynth.gen.GeneratorIPv4Static;
import net.iosynth.gen.GeneratorMACStatic;
import net.iosynth.gen.GeneratorUUIDStatic;
import net.iosynth.gen.Xoroshiro128;
import net.iosynth.sensor.Sensor;
import net.iosynth.sensor.SensorBoolean;
import net.iosynth.sensor.SensorCountry;
import net.iosynth.sensor.SensorTimeStamp;
import net.iosynth.sensor.SensorDevIPv4;
import net.iosynth.sensor.SensorDevMAC48;
import net.iosynth.sensor.SensorDevMAC64;
import net.iosynth.sensor.SensorDevTopic;
import net.iosynth.sensor.SensorDevUUID;
import net.iosynth.sensor.SensorString;
import net.iosynth.sensor.SensorDoubleCycle;
import net.iosynth.sensor.SensorDoubleExponential;
import net.iosynth.sensor.SensorDoubleNormal;
import net.iosynth.sensor.SensorIntCycle;
import net.iosynth.sensor.SensorStringCycle;
import net.iosynth.sensor.SensorDevEpoch;
import net.iosynth.sensor.SensorIPv4;
import net.iosynth.sensor.SensorDoubleUniform;
import net.iosynth.sensor.SensorDoubleWalk;
import net.iosynth.sensor.SensorIntUniform;
import net.iosynth.sensor.SensorIntWalk;
import net.iosynth.sensor.SensorMAC48;
import net.iosynth.sensor.SensorMAC64;
import net.iosynth.sensor.SensorStringRandom;
import net.iosynth.sensor.SensorDevTimeStamp;
import net.iosynth.sensor.SensorUUID;
import net.iosynth.util.RuntimeTypeAdapterFactory;


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
		deviceAdapter.registerSubtype(DeviceSimple.class, "Simple");
		
		final RuntimeTypeAdapterFactory<Sensor> sensorAdapter = RuntimeTypeAdapterFactory.of(Sensor.class, "type");
		sensorAdapter.registerSubtype(SensorDevTopic.class,       "dev.topic");
		sensorAdapter.registerSubtype(SensorDevUUID.class,        "dev.uuid");
		sensorAdapter.registerSubtype(SensorDevIPv4.class,        "dev.ipv4");
		sensorAdapter.registerSubtype(SensorDevMAC48.class,       "dev.mac48");
		sensorAdapter.registerSubtype(SensorDevMAC64.class,       "dev.mac64");
		sensorAdapter.registerSubtype(SensorDevEpoch.class,       "dev.epoch");
		sensorAdapter.registerSubtype(SensorDevTimeStamp.class,   "dev.timestamp");
		
		sensorAdapter.registerSubtype(SensorUUID.class,           "uuid");
		sensorAdapter.registerSubtype(SensorIPv4.class,           "ipv4");
		sensorAdapter.registerSubtype(SensorMAC48.class,          "mac48");
		sensorAdapter.registerSubtype(SensorMAC64.class,          "mac64");
		
		sensorAdapter.registerSubtype(SensorBoolean.class,        "boolean");
		
		sensorAdapter.registerSubtype(SensorDoubleCycle.class,         "double_cycle");
		sensorAdapter.registerSubtype(SensorDoubleWalk.class,          "double_walk");
		sensorAdapter.registerSubtype(SensorDoubleUniform.class,       "double_uniform");
		sensorAdapter.registerSubtype(SensorDoubleNormal.class,        "double_normal");
		sensorAdapter.registerSubtype(SensorDoubleExponential.class,   "double_exp");
		
		sensorAdapter.registerSubtype(SensorIntCycle.class,        "int_cycle");
		sensorAdapter.registerSubtype(SensorIntWalk.class,         "int_walk");
		sensorAdapter.registerSubtype(SensorIntUniform.class,      "int_uniform");
		
		sensorAdapter.registerSubtype(SensorString.class,         "string");
		//sensorAdapter.registerSubtype(SensorStringCycle.class,    "StringCycle");
		//sensorAdapter.registerSubtype(SensorStringRandom.class,   "StringRandom");
		
		sensorAdapter.registerSubtype(SensorCountry.class,        "country");
		sensorAdapter.registerSubtype(SensorTimeStamp.class,      "timestamp");
		
		final RuntimeTypeAdapterFactory<Sampling> samplingAdapter = RuntimeTypeAdapterFactory.of(Sampling.class, "type");
		samplingAdapter.registerSubtype(SamplingFixed.class,         "fixed");
		samplingAdapter.registerSubtype(SamplingUniform.class,       "uniform");
		samplingAdapter.registerSubtype(SamplingNormal.class,        "normal");
		samplingAdapter.registerSubtype(SamplingExponential.class,   "exp");
		

		final Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.registerTypeAdapterFactory(deviceAdapter)
				.registerTypeAdapterFactory(sensorAdapter)
				.registerTypeAdapterFactory(samplingAdapter)
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
		// Initialize all static generators;
		GeneratorUUIDStatic.setRnd(rnd);
		rnd = rnd.copy();
		rnd.jump();
		GeneratorMACStatic.setRnd(rnd);
		GeneratorMACStatic.getRandom48();
		rnd = rnd.copy();
		rnd.jump();
		GeneratorIPv4Static.setRnd(rnd);
		GeneratorIPv4Static.getRandomIPv4();
		rnd = rnd.copy();
		rnd.jump();
		
		
		int devCount = 0;
		List<Device> devOut = new ArrayList<Device>();
		for(int i=0; i<devIn.length; i++){
			devIn[i].setRnd(rnd);
			List<Device> devList = devIn[i].replicate();
			if (devIn[i].getJson_template() != null) {
				DeviceTemplate devTempate = new DeviceTemplate(devIn[i].getJson_template(), devIn[i].getSensors());
				for (Device dev : devList) {
					dev.setDeviceTemplate(devTempate);
					dev.setJson_template(null);
				}
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
	
	static String test = "[{'type':'Simple','sdid':{'type':'String', 'value':'dev'}, 'copy':1, 'sensors':[{'type':'Timestamp'}, {'type':'DoubleRandom', 'min':5}, {'type':'Country','name':'country'} ]   }]";
	
}
