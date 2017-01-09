/**
 * 
 */
package net.iosynth.util;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;

/**
 * @author rradev
 *
 */
public class RabbSend {
	private final static String QUEUE_NAME = "hello";

	/**
	 * 
	 */
	public RabbSend() {
		// TODO Auto-generated constructor stub
	}

	public void send() throws IOException, TimeoutException{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
	    String message = "Hello World!";
	    channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
	    System.out.println(" [x] Sent '" + message + "'");
	    channel.close();
	    connection.close();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RabbSend rabb = new RabbSend();
		try {
			rabb.send();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
