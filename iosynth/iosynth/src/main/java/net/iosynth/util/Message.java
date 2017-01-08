package net.iosynth.util;

/**
 * @author rradev
 *
 */
public class Message {
	private String id;
	private String msg;
	
	/**
	 * @param id
	 * @param msg
	 */
	public Message(String id, String msg){
		this.id  = id;
		this.msg = msg;
	}
	
	/**
	 * @return message id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
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
