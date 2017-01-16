/**
 * 
 */
package net.iosynth.device;

import java.util.List;
import java.util.UUID;

import net.iosynth.sensor.Sensor;
import net.iosynth.sensor.SensorUuid;
import net.iosynth.util.Xoroshiro128;

/**
 * @author rradev
 *
 */
public class DeviceSimple extends Device {
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
		uuid.checkParameters();
		sampling.checkParameters();
		for(final Sensor sen: sensors){
			sen.checkParameters();
		}
	}

	@Override
	public List<Device> replicate() {
		Xoroshiro128 rndT = this.rnd;
		List<Device> devList;
		Device devA[] = {this};
		devList = DevicesFromJson.copyDevice(devA, copy);
		int i = 0;
		for(Device dev: devList){
			dev.setRnd(rndT);
			dev.getUUID().replicate();
			dev.buildTopic();
			dev.getSampling().setRnd(dev.getRnd());
			dev.getSampling().replicate();
			final Sensor[] sens = dev.getSensors();
			for(Sensor sen: sens){
				sen.setRnd(dev.getRnd());
				sen.replicate();
				if(sen instanceof SensorUuid){
					((SensorUuid)sen).SetValue(dev.getUUID().getUUID());;
				}
			}
			// new generator
			rndT = rndT.copy();
			rndT.jump();
			i = i + 1;
		}
		
		return devList;
	}
	

}
