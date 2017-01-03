/**
 * 
 */
package net.iosynth.adapter;

/**
 * @author rradev
 *
 */
public class MqttConfigClass {
	public String topic;
	public int    qos;
	public String broker;
	public String session;

	/**
	 * 
	 */
	public MqttConfigClass() {
		
	}
	
	public void show(){
		System.out.println(topic);
		System.out.println(qos);
		System.out.println(broker);
		System.out.println(session);
	}

}
