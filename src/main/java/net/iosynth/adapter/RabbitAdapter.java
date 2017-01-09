package net.iosynth.adapter;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

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
	private String queue;
	private int    qos;
	private String broker;
	private String session;
	private String clientId;
	
    private MemoryPersistence persistence;
    private MqttClient sampleClient;
    private MqttConnectOptions connOpts;
    
    private ConnectionFactory factory;
    private Connection connection;
    Channel channel;
    
    private BlockingQueue<Message> msgQueue;
    
    /**
     * For json deserialization
     * @param cfg 
     * @param msgQueue 
     */
    public RabbitAdapter(RabbitConfig cfg, BlockingQueue<Message> msgQueue){
		// Adapter default configuration
		this.queue = cfg.queue;
		this.qos = cfg.qos > 2 || cfg.qos < 0 ? 0 : cfg.qos;
		this.broker = cfg.broker;
		this.session = cfg.session;
		this.clientId = "iosynth-0.0.1 " + session;
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
			System.out.println("Connecting to broker: " + broker);
			connection = factory.newConnection();
			System.out.println("Connected");
			channel = connection.createChannel();
			channel.queueDeclare(queue, false, false, false, null);

			while (true) {
				final Message msg = msgQueue.take();
				channel.basicPublish("", queue, null, msg.getMsg().getBytes());

			}
		} catch (IOException ie) {
			ie.printStackTrace();
		} catch (TimeoutException te) {
			te.printStackTrace();
		} catch (InterruptedException ine) {
			ine.printStackTrace();
		}
		finally {
			try {
				channel.close();
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				e.printStackTrace();
			}
			System.out.println("Disconnected");
		}

	}
	
}
