/**
 * 
 */
package net.iosynth.device;

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
	}

}
