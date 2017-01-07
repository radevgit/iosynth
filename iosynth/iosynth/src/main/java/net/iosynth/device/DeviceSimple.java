/**
 * 
 */
package net.iosynth.device;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.iosynth.sensor.Sensor;
import net.iosynth.sensor.SensorDefault;
import net.iosynth.util.DeepCopy;

/**
 * @author rradev
 *
 */
public class DeviceSimple extends Device {
	private static final long serialVersionUID = 1L;
	private final static String F1 = "%1d";
	private final static String F2 = "%02d";
	private final static String F3 = "%03d";
	private final static String F4 = "%04d";
	private final static String F5 = "%05d";
	private final static String F6 = "%06d";
	private final static String F7 = "%07d";
	private final static String F8 = "%08d";

	/**
	 * 
	 */
	public DeviceSimple() {
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see net.iosynth.device.Device#checkParameters()
	 */
	@Override
	public void checkParameters(){
		if(uuid.length()<1){
			uuid = UUID.randomUUID().toString();
		}
		arrival.checkParameters();
		for(final Sensor sen: sensors){
			sen.checkParameters();
		}
	}
	
	/* (non-Javadoc)
	 * @see net.iosynth.device.Device#replicate()
	 */
	@Override
	public List<Device> replicate() {
		List<Device> devList = new ArrayList<Device>();
		String format = getFormat(copy);
		for(int i=0; i<copy; i++){
			Device devNew = (Device) DeepCopy.copyObject(this);
			devNew.setId(this.getId() + String.format(format, i));
			devNew.getArrival().replicate();
			for(final Sensor sen: devNew.sensors){
				sen.replicate();
			}
			devList.add(devNew);
		}
		
		return devList;
	}
	
	/**
	 * Adds default sensor
	 * @param name
	 */
	public void addSensor(String name){
		SensorDefault sen = new SensorDefault();
		sen.setName(name);
		sensors.add(sen);
	}

	private String getFormat(int count){
		if(count<=10){
			return F1;
		}
		if(count<=100){
			return F2;
		}
		if(count<=1000){
			return F3;
		}
		if(count<=10000){
			return F4;
		}
		if(count<=100000){
			return F5;
		}
		if(count<=1000000){
			return F6;
		}
		if(count<=10000000){
			return F7;
		}
		if(count<=100000000){
			return F8;
		}
		return null;
	}

}
