package net.iosynth.app;

public class Message {
	private String id;
	private String msg;
	
	public Message(String id, String msg){
		this.id  = id;
		this.msg = msg;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
