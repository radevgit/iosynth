package net.iosynth.adapter;

import java.net.URISyntaxException;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
/**
 * @author rradev
 *
 */
public class AdapterCoap extends Thread {
	// Adapter default configuration
	private String uri;
	private String topic;
	
	private CoapClient coap;
	
    
    private BlockingQueue<Message> msgQueue;
    private final Logger logger = Logger.getLogger(AdapterRabbit.class.getName());
    
    /**
     * For json deserialization
     * @param cfg 
     * @param msgQueue 
     * @throws URISyntaxException 
     */
    public AdapterCoap(ConfigCoap cfg, BlockingQueue<Message> msgQueue) throws URISyntaxException{
		// Adapter default configuration
    	this.uri      = cfg.uri;
    	this.topic    = cfg.topic;
		setOptions(msgQueue);
		start();
    }
    
	/**
	 * @param msgQueue
	 * @throws URISyntaxException 
	 */
	public void setOptions(BlockingQueue<Message> msgQueue) throws URISyntaxException {
		this.msgQueue = msgQueue;
		coap = new CoapClient();
	}

	@Override
	public void run() {

		logger.info("Connecting to: " + coap.getURI());
		try {
			long k = 0;
			while (true) {
				Message msg;

				msg = msgQueue.take();

				if (k % 100000 == 0) {
					logger.info("queue: " + msgQueue.size());
				}
				String str = uri + "/" + topic + "/" + msg.getId();
				coap.setURI(str).post(msg.getMsg().getBytes(), MediaTypeRegistry.APPLICATION_JSON);
				k++;
			}
		} catch (InterruptedException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
	}
}
