/**
 * 
 */
package net.iosynth.device;

import net.iosynth.gen.GeneratorStaticMAC;

/**
 * @author rradev
 *
 */
public class SDIDMac48 extends SDID {

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
		sdid = GeneratorStaticMAC.getNext48();
	}

}
