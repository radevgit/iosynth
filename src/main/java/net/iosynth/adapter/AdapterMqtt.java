package net.iosynth.adapter;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @author rradev
 *
 */
public class AdapterMqtt extends Thread {
	// Adapter default configuration
	private String uri;
	private String topic;
	private int    qos;
	private String session;
	private String clientId;
	
    private MemoryPersistence persistence;
    private MqttClient sampleClient;
    private MqttConnectOptions connOpts;
    private BlockingQueue<Message> msgQueue;
    
    private final Logger logger = Logger.getLogger(AdapterMqtt.class.getName());
    
    /**
     * For json deserialization
     * @param cfg 
     * @param msgQueue 
     * @throws MqttException 
     */
    public AdapterMqtt(ConfigMqtt cfg, BlockingQueue<Message> msgQueue) throws MqttException{
		// Adapter default configuration
    	this.uri   = cfg.uri;
    	this.topic = cfg.topic;
		this.qos   = cfg.qos > 2 || cfg.qos < 0 ? 0 : cfg.qos; 
		this.session = cfg.session;
		this.clientId = "iosynth-" + UUID.randomUUID();
		setOptions(msgQueue);
		start();
    }
    
	/**
	 * @param msgQueue
	 * @throws MqttException 
	 */
	public void setOptions(BlockingQueue<Message> msgQueue) throws MqttException {
		this.msgQueue = msgQueue;
		persistence = new MemoryPersistence();
		try {
			sampleClient = new MqttClient(uri, clientId, persistence);
			connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
		} catch (MqttException me) {
			logger.log(Level.SEVERE, me.toString(), me);
			throw me;
		}
	}

	@Override
	public void run() {
		try {
			logger.info("Connecting to: " + uri + "    topic: " + topic);
			sampleClient.connect(connOpts);
			logger.info("Connected");
			long k = 0;
			try {
				final String prefix = topic + "/";
				while (true) {
					final Message msg = msgQueue.take();
					if (k % 100000 == 0) {
						logger.info("queue: " + msgQueue.size());
					}
					MqttMessage message = new MqttMessage(msg.getMsg().getBytes());
					message.setQos(qos);
					sampleClient.publish(prefix + msg.getId(), message);
					k++;
				}
			} catch (InterruptedException ex) {
				logger.log(Level.SEVERE, ex.toString(), ex);
			}

			sampleClient.disconnect();
			logger.info("Disconnected");
		} catch (MqttException me) {
			logger.log(Level.SEVERE, me.toString(), me);
		} finally {

		}

	}
	
}
