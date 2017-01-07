/**
 * 
 */
package net.iosynth.device;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.iosynth.sensor.Sensor;
import net.iosynth.sensor.SensorCycleInt;

/**
 * @author rradev
 *
 */
public class DeviceFixedRate extends DeviceSimple {
	/**
	 * 
	 */
	public DeviceFixedRate() {
		ArrivalFixed fixed = new ArrivalFixed();
		this.setArrival(fixed);
		addSensor("first");
		addSensor("second");
		int t[] = {1,2,3,4,5,6,7,8,9};
		SensorCycleInt sen = new SensorCycleInt(t);
		addSensor("xxx", sen);
	}

}
