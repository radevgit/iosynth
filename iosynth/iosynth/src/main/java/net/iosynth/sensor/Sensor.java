package net.iosynth.sensor;

public abstract class Sensor {
	protected String name;
	protected long epoch;
	
	public Sensor(){
		this.name  = new String("sensor");
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
	
	abstract public void step(long step);
	abstract public String getString();
	abstract public void checkParameters();
}
