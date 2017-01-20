/**
 * 
 */
package net.iosynth.device;

import net.iosynth.util.GeneratorStaticMAC;

/**
 * @author rradev
 *
 */
public class SDIDMac64 extends SDID {

	/**
	 * 
	 */
	public SDIDMac64() {
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
		sdid = GeneratorStaticMAC.getNext64();
	}

}