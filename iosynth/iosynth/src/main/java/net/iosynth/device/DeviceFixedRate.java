/**
 * 
 */
package net.iosynth.device;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.iosynth.sensor.Sensor;
import net.iosynth.sensor.SensorCycleInt01;

/**
 * @author rradev
 *
 */
public class DeviceFixedRate extends Device {
	/**
	 * 
	 */
	public DeviceFixedRate() {
		addSensor("first");
		addSensor("second");
		int t[] = {1,2,3,4,5,6,7,8,9};
		SensorCycleInt01 sen = new SensorCycleInt01(t);
		addSensor("xxx", sen);
	}

	/* (non-Javadoc)
	 * @see net.iosynth.device.Device#run()
	 */
	@Override
	public void run() {
		for(final Sensor sensor : sensors) {
		    sensor.step(1);
		}
		getQueue().add(toJson());
	}

}
