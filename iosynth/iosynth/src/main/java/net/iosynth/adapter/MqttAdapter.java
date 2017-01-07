package net.iosynth.adapter;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.google.gson.Gson;

import net.iosynth.util.Message;

/**
 * @author rradev
 *
 */
public class MqttAdapter extends Thread {
	// Adapter default configuration
	private String topic        = "iosynth/";
	private int    qos          = 0;
	private String broker       = "tcp://localhost:1883";
	private UUID uuid           = UUID.randomUUID();
	private String session      = Long.toString(uuid.getMostSignificantBits(), 36);
	private String clientId     = "iosynth-0.0.1 " + session;
	
	
    private MemoryPersistence persistence;
    private MqttClient sampleClient;
    private MqttConnectOptions connOpts;
    private BlockingQueue<Message> msgQueue;
    
	/**
	 * @param cfgJson
	 * @param msgQueue
	 */
	public MqttAdapter(String cfgJson, BlockingQueue<Message> msgQueue) {
		// set configuration from Json file
		Gson gson = new Gson();
		MqttConfigClass cfg = gson.fromJson(cfgJson, MqttConfigClass.class);
		if(cfg.topic != null){
			topic = cfg.topic;
		}
		qos = cfg.qos;
		if(cfg.broker != null){
			broker = cfg.broker;
		}
		if(cfg.session != null){
			session = cfg.session;
		}
		
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
