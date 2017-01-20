/**
 * 
 */
package net.iosynth.device;

import java.util.List;

import net.iosynth.gen.Xoroshiro128;
import net.iosynth.sensor.Sensor;

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
		sdid.checkParameters();
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
		for(Device dev: devList){
			dev.setRnd(rndT);
			dev.getSDID().replicate();
			dev.buildTopic();
			dev.getSampling().setRnd(dev.getRnd());
			dev.getSampling().replicate();
			final Sensor[] sens = dev.getSensors();
			for(Sensor sen: sens){
				sen.setDev(dev);
				sen.setRnd(dev.getRnd());
				sen.replicate();
			}
			// new generator
			rndT = rndT.copy();
			rndT.jump();
		}
		
		return devList;
	}
	

}
