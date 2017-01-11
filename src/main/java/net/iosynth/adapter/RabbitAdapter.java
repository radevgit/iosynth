package net.iosynth.adapter;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author rradev
 *
 */
public class RabbitAdapter extends Thread {
	// Adapter default configuration
	private String topic;
	private String exchange;
	private String broker;
    
    private ConnectionFactory factory;
    private Connection connection;
    Channel channel;
    
    private BlockingQueue<Message> msgQueue;
    private final Logger logger = Logger.getLogger(RabbitAdapter.class.getName());
    
    /**
     * For json deserialization
     * @param cfg 
     * @param msgQueue 
     */
    public RabbitAdapter(RabbitConfig cfg, BlockingQueue<Message> msgQueue){
		// Adapter default configuration
		this.topic = cfg.topic;
		this.exchange = cfg.exchange;
		this.broker = cfg.broker;
		setOptions(msgQueue);
		start();
    }
    
	/**
	 * @param msgQueue
	 */
	public void setOptions(BlockingQueue<Message> msgQueue) {
		this.msgQueue = msgQueue;
		factory = new ConnectionFactory();
		factory.setHost(broker);
		
	}

	@Override
	public void run() {
		try {
			logger.info("Connecting to broker: " + broker);
			connection = factory.newConnection();
			logger.info("Connected");
			channel = connection.createChannel();
			channel.exchangeDeclare(exchange, "topic");
			//channel.queueDeclare(queue, false, false, false, null);

			while (true) {
				final Message msg = msgQueue.take();
				channel.basicPublish(exchange, topic + "." + msg.getId(), null, msg.getMsg().getBytes());
				logger.info(topic + "." + msg.getId());
			}
		} catch (IOException ie) {
			logger.log(Level.SEVERE, ie.toString(), ie);
		} catch (TimeoutException te) {
			logger.log(Level.SEVERE, te.toString(), te);
		} catch (InterruptedException ine) {
			logger.log(Level.SEVERE, ine.toString(), ine);
		}
		finally {
			try {
				channel.close();
				connection.close();
			} catch (IOException e) {
				//
			} catch (TimeoutException e) {
				//
			}
			logger.info("Disconnected");
			System.exit(1);
		}

	}
	
}
