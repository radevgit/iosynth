/**
 * 
 */
package net.iosynth.device;

import net.iosynth.util.GeneratorDevCount;

/**
 * @author rradev
 *
 */
public class SDIDString extends SDID {
	private static final String format = "%06d";
	private String value;
	
	/**
	 * 
	 */
	public SDIDString() {
		value = new String("");
	}

	/* (non-Javadoc)
	 * @see net.iosynth.device.SDID#checkParameters()
	 */
	@Override
	public void checkParameters() {
		// nothing to do
	}
	
	/* (non-Javadoc)
	 * @see net.iosynth.device.Sampling#replicate()
	 */
	@Override
	public void replicate() {
		uuid = value + String.format(format, GeneratorDevCount.getNext());
	}

}
