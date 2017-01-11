/**
 * 
 */
package net.iosynth.adapter;

import java.util.UUID;

/**
 * @author rradev
 *
 */
public class RabbitConfig {
    /**
     * 
     */
    public long seed;
    protected String exchange;
    protected String topic;
    protected int qos;
    protected String broker;
    protected UUID uuid;
    protected String session;

	/**
	 * 
	 */
	public RabbitConfig(){
    	// Global configuration
    	this.seed         = 2052703995999047696L; // magic number
    	// Adapter default configuration
    	this.topic        = "iosynth";
    	this.exchange     = "iosynth";
    	this.broker       = "localhost";
    	UUID uuid         = UUID.randomUUID();
    	this.session      = Long.toString(uuid.getMostSignificantBits(), 36);
    }

}
