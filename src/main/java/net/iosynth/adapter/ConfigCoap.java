/**
 * 
 */
package net.iosynth.adapter;

import java.util.UUID;

/**
 * @author rradev
 *
 */
public class ConfigCoap {
    /**
     * 
     */
    public long seed;
    protected String uri;
    protected String exchange;
    protected String topic;
    protected int qos;
    protected String broker;
    protected UUID uuid;
    protected String session;

	/**
	 * 
	 */
	public ConfigCoap(){
    	// Global configuration
    	this.seed         = 2052703995999047696L; // magic number
    	// Adapter default configuration
    	this.uri          = "amqp://localhost:5672";
    	this.exchange     = "iosynth";
    	this.topic        = "device";
    	UUID uuid         = UUID.randomUUID();
    	this.session      = Long.toString(uuid.getMostSignificantBits(), 36);
    }

}
