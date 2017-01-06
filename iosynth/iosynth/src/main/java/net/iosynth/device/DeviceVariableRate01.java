/**
 * 
 */
package net.iosynth.device;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import net.iosynth.sensor.Sensor;
import net.iosynth.sensor.SensorCycleDouble01;
import net.iosynth.sensor.SensorCycleInt01;
import net.iosynth.sensor.SensorCycleString01;
import net.iosynth.sensor.SensorRandomDouble01;
import net.iosynth.sensor.SensorRandomInt01;
import net.iosynth.util.Delay;
import net.iosynth.util.Message;

/**
 * Example Device class
 * @author ross
 *
 */
public class DeviceVariableRate01 extends Device {

	/**
	 * 
	 */
	public DeviceVariableRate01() {		
		String stringVal[] = {new String("ON"), new String("OF")};
		addSensor(new String("door"), new SensorCycleString01(stringVal));
		
		String stringVal2[] = {new String("elevator_01"), new String("elevator_02"), new String("elevator_03"), new String("elevator_04")};
		addSensor(new String("1st floor"), new SensorCycleString01(stringVal2));
		
		addSensor(new String("state"), new SensorRandomInt01(0, 25));
		
		addSensor("count");
	}

	public void run() {
		for(final Sensor sensor : sensors) {
		    sensor.step(1);
		}
		getQueue().add(toJson());
		long delay = ThreadLocalRandom.current().nextLong(25*1000)+1000;
		//setDealy(delay);
		Delay d = new Delay(getDelayId(), delay);
		getDelayQueue().add(d);
	}
	

}

