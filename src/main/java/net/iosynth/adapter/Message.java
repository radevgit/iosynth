package net.iosynth.adapter;

/**
 * @author rradev
 *
 */
public class Message {
	private String topic;
	private String msg;
	
	/**
	 * @param topic
	 * @param msg
	 */
	public Message(String topic, String msg){
		this.topic  = topic;
		this.msg = msg;
	}
	
	/**
	 * @return message topic
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * @param topic
	 */
	public void setTopic(String id) {
		this.topic = id;
	}

	/**
	 * @return message
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
