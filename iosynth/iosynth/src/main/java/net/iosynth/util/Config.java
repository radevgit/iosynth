/**
 * 
 */
package net.iosynth.util;

/**
 * @author rradev
 *
 */
public class Config {
	public String topic        = "iosynth/";
	public int    qos          = 2;
	public String brokerHost   = "localhost";
	public int    brokerPort   = 1883;

	/**
	 * 
	 */
	public Config() {
		
	}
	
	public void show(){
		System.out.println(topic);
		System.out.println(qos);
		System.out.println(brokerHost);
		System.out.println(brokerPort);
	}

}
