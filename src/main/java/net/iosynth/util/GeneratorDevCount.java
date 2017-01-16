/**
 * 
 */
package net.iosynth.util;

/**
 * @author rradev
 *
 */
public final class GeneratorDevCount {
	private static int count = -1;
	
	/**
	 * @return devices count
	 */
	public static int getNext(){
		count += 1;
		return count;
	}
	
}
