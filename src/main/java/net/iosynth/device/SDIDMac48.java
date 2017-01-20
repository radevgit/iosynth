/**
 * 
 */
package net.iosynth.device;

import net.iosynth.gen.GeneratorMACStatic;

/**
 * @author rradev
 *
 */
public class SDIDMac48 extends SDID {
	String prefix;
	/**
	 * 
	 */
	public SDIDMac48() {
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
		GeneratorMACStatic.setPrefix(prefix);
		sdid = GeneratorMACStatic.getNext48();
	}


}
