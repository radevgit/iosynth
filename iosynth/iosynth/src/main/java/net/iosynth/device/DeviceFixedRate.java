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
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public DeviceFixedRate() {
		ArrivalFixed fixed = new ArrivalFixed();
		this.setArrival(fixed);
		addSensor("first");
		addSensor("second");
		int t[] = {1,2,3,4,5,6,7,8,9};
		SensorCycleInt sen = new SensorCycleInt(t);
		addSensor("xxx", sen);
	}

}
