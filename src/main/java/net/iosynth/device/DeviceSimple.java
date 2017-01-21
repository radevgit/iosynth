/**
 * 
 */
package net.iosynth.device;

import java.util.List;
import java.util.UUID;

import net.iosynth.gen.GeneratorDevCount;
import net.iosynth.gen.GeneratorIPv4Static;
import net.iosynth.gen.GeneratorMACStatic;
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
			//dev.getSDID().replicate();
			replicateID(dev);
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
	
	/**
	 * Replicates all ids: ip, mac, uuid
	 * @param dev
	 */
	public void replicateID(Device dev){
		if(dev.uuid != null){
			if(dev.uuid.length() != 0 || dev.uuid.contains("%")){
				dev.uuid = String.format(dev.uuid, GeneratorDevCount.getNext());
			} else {
				dev.uuid =  UUID.randomUUID().toString();
			}
		}
		if(dev.ipv4 != null){
			if(dev.ipv4.length() != 0){
				GeneratorIPv4Static.setPrefix(dev.ipv4);
			}
			dev.ipv4 = GeneratorIPv4Static.getNextIPv4();
		}
		if(dev.mac48 != null){
			if(dev.mac48.length() != 0){
				GeneratorMACStatic.setPrefix(dev.mac48);
			}
			dev.mac48 = GeneratorMACStatic.getNext48();
		}
		if(dev.mac64 != null){
			if(dev.mac64.length() != 0){
				GeneratorMACStatic.setPrefix(dev.mac64);
			}
			dev.mac64 = GeneratorMACStatic.getNext64();
		}
	}
	

	
	
	
	
}
