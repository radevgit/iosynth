package net.iosynth.sensor;

public abstract class Sensor {
	protected String name;
	protected long epoch;
	
	public Sensor(){
		this.epoch = 0L;
	}
	
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
	
	public long getEpoch(){
		return epoch;
	}
	
	public abstract void step(long step);
	public abstract String getString();
}
