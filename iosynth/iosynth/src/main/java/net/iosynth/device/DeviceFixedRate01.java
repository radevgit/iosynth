/**
 * 
 */
package net.iosynth.device;

import java.util.Map;

import net.iosynth.sensor.Sensor;
import net.iosynth.sensor.SensorCycleDouble01;
import net.iosynth.sensor.SensorCycleInt01;
import net.iosynth.sensor.SensorCycleString01;
import net.iosynth.sensor.SensorRandomDouble01;
import net.iosynth.util.Message;

/**
 * Example Device class
 * @author ross
 *
 */
public class DeviceFixedRate01 extends Device {

	/**
	 * 
	 */
	public DeviceFixedRate01() {
		int intVal[] = {1,5,7,8,13,12,11,10,9,9,9,10,11,12,13,14,14,15,16,17,19,21,25,30,50,60,55,50,40,33,21,12,6,5,2};
		addSensor(new String("state0"), new SensorCycleInt01(intVal));
		
		double doubleVal[] = {1.1, 1.2, 1.3, 1.2, 1.4};
		addSensor(new String("state1"), new SensorCycleDouble01(doubleVal));
		
		addSensor(new String("temp01"), new SensorRandomDouble01(15, 25));
		
		addSensor("default");
	}

	public void run() {
		for(Map.Entry<String, Sensor> sen : sens.entrySet()) {
		    String name = sen.getKey();
		    Sensor sensor = sen.getValue();
		    sensor.step(1);
		}
		getQueue().add(toJson());
	}
	

}
