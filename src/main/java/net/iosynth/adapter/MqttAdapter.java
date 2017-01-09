package net.iosynth.adapter;

import java.util.concurrent.BlockingQueue;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @author rradev
 *
 */
public class MqttAdapter extends Thread {
	// Adapter default configuration
	private String topic;
	private int    qos;
	private String broker;
	private String session;
	private String clientId;
	
    private MemoryPersistence persistence;
    private MqttClient sampleClient;
    private MqttConnectOptions connOpts;
    private BlockingQueue<Message> msgQueue;
    
    /**
     * For json deserialization
     * @param cfg 
     * @param msgQueue 
     */
    public MqttAdapter(MqttConfig cfg, BlockingQueue<Message> msgQueue){
		// Adapter default configuration
		this.topic = cfg.topic;
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
		persistence = new MemoryPersistence();
		try {
			sampleClient = new MqttClient(broker, clientId, persistence);
			connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
		} catch (MqttException me) {
			System.out.println("reason " + me.getReasonCode());
			System.out.println("msg " + me.getMessage());
			System.out.println("loc " + me.getLocalizedMessage());
			System.out.println("cause " + me.getCause());
			System.out.println("excep " + me);
			me.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			System.out.println("Connecting to broker: " + broker);
			sampleClient.connect(connOpts);
			System.out.println("Connected");
			long k = 0;
			try {
				while (true) {
					final Message msg = msgQueue.take();
					if(k%100000==0){
						System.out.println("queue: " + 	msgQueue.size());
					}
					//System.out.println("Publishing message: " + msg.getId() + " " + msg.getMsg());
					MqttMessage message = new MqttMessage(msg.getMsg().getBytes());
					message.setQos(qos);
					sampleClient.publish(topic + session + "/" + msg.getId(), message);
					k++;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			sampleClient.disconnect();
			System.out.println("Disconnected");
		} catch (MqttException me) {
			System.out.println("reason " + me.getReasonCode());
			System.out.println("msg " + me.getMessage());
			System.out.println("loc " + me.getLocalizedMessage());
			System.out.println("cause " + me.getCause());
			System.out.println("excep " + me);
			me.printStackTrace();
		} finally {

		}
		
	}
	
}
