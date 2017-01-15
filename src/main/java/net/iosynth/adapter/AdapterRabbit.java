package net.iosynth.adapter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author rradev
 *
 */
public class AdapterRabbit extends Thread {
	// Adapter default configuration
	private String uri;
	private String exchange;
	private String topic;
	
	
    
    private ConnectionFactory factory;
    private Connection connection;
    Channel channel;
    
    private BlockingQueue<Message> msgQueue;
    private final Logger logger = Logger.getLogger(AdapterRabbit.class.getName());
    
    /**
     * For json deserialization
     * @param cfg 
     * @param msgQueue 
     * @throws URISyntaxException 
     */
    public AdapterRabbit(ConfigRabbit cfg, BlockingQueue<Message> msgQueue) throws URISyntaxException{
		// Adapter default configuration
    	this.uri      = cfg.uri;
    	this.exchange = cfg.exchange;
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
		factory = new ConnectionFactory();
		try {
			factory.setUri(uri);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.toString(), e);
			throw new URISyntaxException(uri, e.toString());
		}
	}

	@Override
	public void run() {
		try {
			logger.info("Connecting to: " + factory.getHost() + ":" + factory.getPort() + factory.getVirtualHost() + "    exchange: " + exchange + "    topic: " + topic);
			connection = factory.newConnection();
			logger.info("Connected");
			channel = connection.createChannel();
			channel.exchangeDeclare(exchange, "topic");
			//channel.queueDeclare(queue, false, false, false, null);
			
			long k = 0;
			while (true) {
				final Message msg = msgQueue.take();
				if (k % 100000 == 0) {
					logger.info("queue: " + msgQueue.size());
				}
				channel.basicPublish(exchange, topic + "." + msg.getId(), null, msg.getMsg().getBytes());
				k++;
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.toString(), e);
		} finally {
			try {
				if (channel != null) {
					channel.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (IOException e) {
				//
			} catch (TimeoutException e) {
				//
			}
			logger.info("Disconnected");
		}

	}
	
}
