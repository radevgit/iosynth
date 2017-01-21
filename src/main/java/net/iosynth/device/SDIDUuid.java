/**
 * 
 */
package net.iosynth.device;

import java.util.UUID;

import net.iosynth.gen.GeneratorUUIDStatic;

/**
 * @author rradev
 *
 */
public class SDIDUuid extends SDID {

	/**
	 * 
	 */
	public SDIDUuid() {
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
		sdid = GeneratorUUIDStatic.getUUID();
	}

}
