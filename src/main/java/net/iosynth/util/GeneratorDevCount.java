/**
 * 
 */
package net.iosynth.util;

/**
 * @author rradev
 *
 */
public class GeneratorDevCount {
	private static int count = 0;
	
	/**
	 * @return
	 */
	public static int getNext(){
		count += 1;
		return count;
	}
	
}
