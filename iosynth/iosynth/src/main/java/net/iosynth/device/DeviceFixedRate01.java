/**
 * 
 */
package net.iosynth.device;

import java.util.Map;

import net.iosynth.sensor.Sensor;
import net.iosynth.sensor.SensorConstantString;
import net.iosynth.sensor.SensorCycleDouble01;
import net.iosynth.sensor.SensorCycleInt01;
import net.iosynth.sensor.SensorCycleString01;
import net.iosynth.sensor.SensorRandomDouble01;
import net.iosynth.sensor.SensorRandomString01;
import net.iosynth.util.Message;

/**
 * Example Device class
 * @author ross
 *
 */
public class DeviceFixedRate01 extends DeviceSimple {

	/**
	 * 
	 */
	public DeviceFixedRate01() {
		
		int intVal[] = {1,5,7,8,13,12,11,10,9,9,9,10,11,12,13,14,14,15,16,17,19,21,25,30,50,60,55,50,40,33,21,12,6,5,2};
		addSensor(new String("state"), new SensorCycleInt01(intVal));
		
		double doubleVal[] = {1.1, 1.2, 1.3, 1.2, 1.4};
		addSensor(new String("level"), new SensorCycleDouble01(doubleVal));
		
		addSensor(new String("temp"), new SensorRandomDouble01(15, 25));
		
		String stringVal[] = {new String("red"), new String("yellow"), new String("green"), new String("yellow")};
		addSensor(new String("semafor"), new SensorCycleString01(stringVal));
		
		String stringVal2[] = {
				new String("Alfa"), new String("Bravo"), new String("Charlie"), new String("Delta"),
				new String("Echo"), new String("Foxtrot"), new String("Golf"), new String("Hotel"),
				new String("India"), new String("Juliett"), new String("Kilo"), new String("Lima"),
				new String("Mike"), new String("November"), new String("Oscar"), new String("Papa"),
				new String("Quebec"), new String("Romeo"), new String("Sierra"), new String("Tango"),
				new String("Uniform"), new String("Victor"), new String("Whiskey"), new String("Xray"),
				new String("Yankee"), new String("Zulu")};
		addSensor(new String("word"), new SensorRandomString01(stringVal2));
		
		addSensor("prime");
	}

	public void run() {
		for(final Sensor sensor : sensors) {
		    String name = sensor.getName();
		    sensor.step(1);
		}
		getQueue().add(toJson());
	}
	

}
