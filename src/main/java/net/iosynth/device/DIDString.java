/**
 * 
 */
package net.iosynth.device;

import net.iosynth.util.GeneratorDevCount;

/**
 * @author rradev
 *
 */
public class DIDString extends DID {
	private static final String format = "%6d";

	/**
	 * 
	 */
	public DIDString() {
		// nothing to do
	}

	/* (non-Javadoc)
	 * @see net.iosynth.device.DID#checkParameters()
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
		uuid = uuid + String.format(format, GeneratorDevCount.getNext());
	}

}
