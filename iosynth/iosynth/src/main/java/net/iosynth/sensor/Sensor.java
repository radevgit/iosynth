package net.iosynth.sensor;

public abstract class Sensor {
	protected long epoch;
	
	public Sensor(){
		this.epoch = 0L;
	}
	
	public long getEpoch(){
		return epoch;
	}
	
	public abstract void step(long step);
	public abstract String getString();
}
