/**
 * 
 */
package net.iosynth.util;


/**
 * @author rradev
 *
 */
public final class GeneratorMAC {	
	/*
     * RNG used by this class to create random
     * based MACs. In a holder class to defer initialization.
     */
    private static class Holder {
    	static long seed;
        static final Xoroshiro128 rng = new Xoroshiro128(seed);
        static final int state[] = new int[6];
        static boolean first = true;
    }
	
    /**
     * @param seed
     */
    public static void setSeed(long seed){
    	Holder.seed = seed;
    }
	
	/**
	 * @return SDIDMac48
	 */
	public static String getRandom48(){
		return getRandom(0);
	}
	/**
	 * @return MAC64
	 */
	public static String getRandom64(){
		return getRandom(1);
	}
	
	/**
	 * @return SDIDMac48
	 */
	public static String getNext48(){
		if(Holder.first == true){
			Holder.first = false;
			return getRandom(0);
		} else {
			return getNext(0);
		}
	}
	
	/**
	 * @return MAC64
	 */
	public static String getNext64(){
		if(Holder.first == true){
			Holder.first = false;
			return getRandom(1);
		} else {
			return getNext(1);
		}
	}

	protected static String getRandom(int f){
		Xoroshiro128 rnd = Holder.rng;
		int state[] = Holder.state;
		state[0] = rnd.nextInt(128)*2;
		state[1] = rnd.nextInt(256);
		state[2] = rnd.nextInt(256);
		state[3] = rnd.nextInt(256);
		state[4] = rnd.nextInt(256);
		state[5] = rnd.nextInt(256);
		switch (f) {
		case 0:
			return format48();
		case 1:
			return format64();
		default:
			return null;
		}
	}
	
	private static String getNext(int f){
		int state[] = Holder.state;
		state[5] += 1;
		if (state[5] > 255) {
			state[5] = 0;
			state[4] += 1;
			if (state[4] > 255) {
				state[4] = 0;
				state[3] += 1;
				if (state[3] > 255) {
					state[3] = 0;
				}
			}
		}
		switch (f) {
		case 0:
			return format48();
		case 1:
			return format64();
		default:
			return null;
		}
	}
	
	private static String format48(){
		int state[] = Holder.state;
		StringBuilder b = new StringBuilder();
		b.append(String.format("%02X", state[0])).append(":")
		 .append(String.format("%02X", state[1])).append(":")
		 .append(String.format("%02X", state[2])).append(":")
		 .append(String.format("%02X", state[3])).append(":")
		 .append(String.format("%02X", state[4])).append(":")
		 .append(String.format("%02X", state[5]));
		return b.toString();
	}
	
	private static String format64(){
		int state[] = Holder.state;
		StringBuilder b = new StringBuilder();
		b.append(String.format("%02X", state[0])).append(":")
		 .append(String.format("%02X", state[1])).append(":")
		 .append(String.format("%02X", state[2])).append(":FFFE:")
		 .append(String.format("%02X", state[3])).append(":")
		 .append(String.format("%02X", state[4])).append(":")
		 .append(String.format("%02X", state[5]));
		return b.toString();
	}	

	/**
	 * @param args
	 */
	public static void main(String[] args){
		for(int i=0; i< 300; i++){
			System.out.println(GeneratorMAC.getNext48());
		}
	}

}
