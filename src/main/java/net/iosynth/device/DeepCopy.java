/**
 * 
 */
package net.iosynth.device;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.iosynth.sensor.Sensor;
import net.iosynth.sensor.SensorString;
import net.iosynth.sensor.SensorDoubleCycle;
import net.iosynth.sensor.SensorIntCycle;
import net.iosynth.sensor.SensorStringCycle;
import net.iosynth.sensor.SensorDefault;
import net.iosynth.sensor.SensorDoubleRandom;
import net.iosynth.sensor.SensorIntRandom;
import net.iosynth.sensor.SensorStringRandom;
import net.iosynth.util.RuntimeTypeAdapterFactory;

/**
 * @author rradev
 *
 */
public class DeepCopy {

	/**
	 * Returns a copy of the object, or null if the object cannot be serialized.
	 * @param orig source object
	 * @return identical object copy
	 */
	public static Object copyObject(Object orig) {
		Object obj = null;
		try {
			// Write the object out to a byte array
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bos);
			out.writeObject(orig);
			out.flush();
			out.close();

			// Make an input stream from the byte array and read
			// a copy of the object back in.
			ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
			obj = in.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	private static Gson getParser(){
		final net.iosynth.util.RuntimeTypeAdapterFactory<Device> deviceAdapter = RuntimeTypeAdapterFactory.of(Device.class, "type");
		deviceAdapter.registerSubtype(Device.class, "Device");
		deviceAdapter.registerSubtype(DeviceSimple.class, "DeviceSimple");
		
		
		final RuntimeTypeAdapterFactory<Sensor> sensorAdapter = RuntimeTypeAdapterFactory.of(Sensor.class, "type");
		sensorAdapter.registerSubtype(SensorString.class, "SensorString");
		sensorAdapter.registerSubtype(SensorDoubleCycle.class,    "SensorDoubleCycle");
		sensorAdapter.registerSubtype(SensorIntCycle.class,       "SensorIntCycle");
		sensorAdapter.registerSubtype(SensorStringCycle.class,    "SensorStringCycle");
		sensorAdapter.registerSubtype(SensorDefault.class,        "SensorDefault");
		sensorAdapter.registerSubtype(SensorDoubleRandom.class,   "SensorDoubleRandom");
		sensorAdapter.registerSubtype(SensorIntRandom.class,      "SensorIntRandom");
		sensorAdapter.registerSubtype(SensorStringRandom.class,   "SensorStringRandom");
		
		final RuntimeTypeAdapterFactory<Sampling> samplingAdapter = RuntimeTypeAdapterFactory.of(Sampling.class, "type");
		//samplingAdapterAdapter.registerSubtype(Sampling.class, "Sampling");
		samplingAdapter.registerSubtype(SamplingFixed.class, "SamplingFixed");
		samplingAdapter.registerSubtype(SamplingUniform.class, "SamplingUniform");
		
		//RuntimeTypeAdapterFactory<DeviceCopy> copyAdapter = RuntimeTypeAdapterFactory.of(DeviceCopy.class, "type");
		//copyAdapter.registerSubtype(DeviceCopySimple.class, "CopySimple");
		


		Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.registerTypeAdapterFactory(deviceAdapter)
				.registerTypeAdapterFactory(sensorAdapter)
				.registerTypeAdapterFactory(samplingAdapter)
				//.registerTypeAdapterFactory(copyAdapter)
				.create();
		return gson;
	}
	
	/**
	 * @param dev
	 * @param count
	 * @return list of devices
	 */
	public static List<Device> copyDevice(Device dev, int count){
		List<Device> devList = new ArrayList<Device>();
		final Gson gson = getParser();
		final String json = gson.toJson(dev);
		for(int i=0; i<count; i++){
			Device devT = gson.fromJson(json, Device.class);
			devList.add(devT);
		}
		
		return devList;
	}
}
