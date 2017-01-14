/**
 * 
 */
package net.iosynth.adapter;

import java.util.UUID;

/**
 * @author rradev
 *
 */
public class ConfigRabbit {
    protected String uri;
    protected String exchange;
    protected String topic;
    protected int qos;
    protected UUID uuid;
    protected String session;
    /**
     * 
     */
    public int clients;
    /**
     * random generator sees
     */
    public long seed;
    
	/**
	 * 
	 */
	public ConfigRabbit(){
		// Adapter default configuration
    	this.uri          = "amqp://localhost:5672";
    	this.exchange     = "iosynth";
    	this.topic        = "device";
    	this.clients      = 1;
    	this.seed         = 2052703995999047696L; // magic number
    }

}
