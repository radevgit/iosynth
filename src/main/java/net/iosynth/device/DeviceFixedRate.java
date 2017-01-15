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
		SamplingFixed fixed = new SamplingFixed();
		this.setSampling(fixed);
	}

}
