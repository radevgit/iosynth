/**
 * Contains configuration for Mqtt connection adapter
 */
package net.iosynth.adapter;

/**
 * @author rradev
 *
 */
public class MqttConfigClass {
	protected String topic;
	protected int    qos;
	protected String broker;
	protected String session;
	protected MqttConfigClass(){
		// TODO: default values here
	}
}
