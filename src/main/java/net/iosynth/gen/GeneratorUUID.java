/**
 * 
 */
package net.iosynth.gen;


/**
 * @author rradev
 *
 */
public class GeneratorUUID {
	private Xoroshiro128 rnd;
	private int s;
	private final byte[] state = new byte[16];
	private long mostSigBits;
	private long leastSigBits;
	
	private static final String dash = "-";
	/**
	 * @param rnd 
	 * 
	 */
	public GeneratorUUID(Xoroshiro128 rnd) {
		this.rnd = rnd;
	}
	
	/**
	 * @param rnd
	 */
	public void setRnd(Xoroshiro128 rnd){
		this.rnd = rnd;
	}
	
	/**
	 * @return uuid
	 */
	public String getUUID(){
		rnd.nextBytes(state);
		state[6]  &= 0x0f;
        state[6]  |= 0x40;
        state[8]  &= 0x3f;
        state[8]  |= 0x80;
        UUID(state);
        
        return (digits(mostSigBits >> 32, 8) + dash +
        		digits(mostSigBits >> 16, 4) + dash +
        		digits(mostSigBits, 4) + dash +
        		digits(leastSigBits >> 48, 4) + dash +
        		digits(leastSigBits, 12));

	}
	private String digits(long val, int digits) {
        long hi = 1L << (digits * 4);
        return Long.toHexString(hi | (val & (hi - 1))).substring(1);
    }
	
    private void UUID(byte[] data) {
        long msb = 0;
        long lsb = 0;
        for (int i=0; i<8; i++)
            msb = (msb << 8) | (data[i] & 0xff);
        for (int i=8; i<16; i++)
            lsb = (lsb << 8) | (data[i] & 0xff);
        this.mostSigBits = msb;
        this.leastSigBits = lsb;
    }
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// performance: 10^8 getUUID() = 56s
		Xoroshiro128 rnd = new Xoroshiro128(123);
		GeneratorUUID gen = new GeneratorUUID(rnd);
		long start = System.currentTimeMillis();
		for(int i=0; i<100000000; i++){
			gen.getUUID();
			//System.out.println(gen.getUUID());
		}
		long end = System.currentTimeMillis();
		System.out.println(String.valueOf(end-start));
	}

}
