/**
 * 
 */
package net.iosynth.adapter;

import java.util.UUID;

/**
 * @author rradev
 *
 */
public class ConfigMqtt {
    /**
     * 
     */
    protected String uri;
    protected String topic;
    protected int qos;
    protected UUID uuid;
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
	public ConfigMqtt(){
    	// Adapter default configuration.
		this.uri          = "tcp://localhost:1883";
		this.topic        = "iosynth";
    	this.qos          = 0;
    	this.clients      = 1;
    	this.seed         = 2052703995999047696L; // magic number
    }

}
