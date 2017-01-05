/**
 * Contains configuration for Mqtt connection adapter
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
	public MqttConfigClass(){
		// no-args constructor
	}
}
