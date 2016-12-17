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
public class Device01 extends Device {

	/**
	 * 
	 */
	public Device01() {
		int intVal[] = {1,5,7,8,13,12,11,10,9,9,9,10,11,12,13,14,14,15,16,17,19,21,25,30,50,60,55,50,40,33,21,12,6,5,2};
		addSensor(new String("state0"), new SensorCycleInt01(intVal));
		
		double doubleVal[] = {1.1, 1.2, 1.3, 1.2, 1.4};
		addSensor(new String("state1"), new SensorCycleDouble01(doubleVal));
		
		String stringVal[] = {new String("ON"), new String("OF")};
		addSensor(new String("door"), new SensorCycleString01(stringVal));
		
		String stringVal2[] = {new String("elevator_01"), new String("elevator_02"), new String("elevator_03"), new String("elevator_04")};
		addSensor(new String("level1"), new SensorCycleString01(stringVal2));
		
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
	
	public Message toJson(){
		StringBuilder m = new StringBuilder();
		m.append("{");
		for(Map.Entry<String, Sensor> sen : sens.entrySet()) {
		    String name = sen.getKey();
		    Sensor sensor = sen.getValue();
		    m.append("\"");
		    m.append(name);
		    m.append("\":");
		    m.append(sensor.getString());
		    m.append(",");
		}
		m.deleteCharAt(m.length()-1) ; // remove last comma
		m.append("}");
		return new Message(getId(), m.toString());
	}
	
	

}
