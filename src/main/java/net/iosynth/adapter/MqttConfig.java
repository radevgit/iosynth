/**
 * 
 */
package net.iosynth.adapter;

import java.util.UUID;

/**
 * @author rradev
 *
 */
public class MqttConfig {
    /**
     * 
     */
    protected String uri;
    protected String topic;
    protected String session;
    protected int qos;
    protected UUID uuid;
    /**
     * random generator sees
     */
    public long seed;
	/**
	 * 
	 */
	public MqttConfig(){
    	// Adapter default configuration.
		this.uri          = "tcp://localhost:1883";
		this.topic        = "iosynth";
    	UUID uuid         = UUID.randomUUID();
    	this.session      = Long.toString(uuid.getMostSignificantBits(), 36);
    	this.qos          = 0;
    	this.seed         = 2052703995999047696L; // magic number
    }

}
