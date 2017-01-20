/**
 * 
 */
package net.iosynth.device;

import net.iosynth.gen.GeneratorIPv4Static;

/**
 * @author rradev
 *
 */
public class SDIDIPv4 extends SDID {
	String prefix;
	/**
	 * 
	 */
	public SDIDIPv4() {
		// nothing to do
	}

	/* (non-Javadoc)
	 * @see net.iosynth.device.SDID#checkParameters()
	 */
	@Override
	public void checkParameters() {
		// nothing to do
	}

	/* (non-Javadoc)
	 * @see net.iosynth.device.SDID#replicate(java.lang.String)
	 */
	@Override
	public void replicate() {
		GeneratorIPv4Static.setPrefix(prefix);
		sdid = GeneratorIPv4Static.getNextIPv4();
	}


}
