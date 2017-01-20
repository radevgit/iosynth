/**
 * 
 */
package net.iosynth.gen;

import java.util.Locale;

/**
 * @author rradev
 *
 */
public class GeneratorCountry {
	private Xoroshiro128 rnd;
	private Locale loc;
	private final String[] locales;
	
	/**
	 * @param rnd 
	 * 
	 */
	public GeneratorCountry(Xoroshiro128 rnd) {
		this.rnd = rnd;
		this.loc = Locale.US;
		this.locales = Locale.getISOCountries();
	}
	
	/**
	 * @return country name
	 */
	public String getCountry(){
		String code = locales[rnd.nextInt(locales.length)];
		Locale tmp = new Locale("", code);
		return tmp.getDisplayCountry(loc);
	}
	
	/**
	 * @return country name
	 */
	public String getCode(){
		return locales[rnd.nextInt(locales.length)];
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// performance: 10^8 getCountry() = 222s
		Xoroshiro128 rnd = new Xoroshiro128(123);
		GeneratorCountry gen = new GeneratorCountry(rnd);
		long start = System.currentTimeMillis();
		for(int i=0; i<100000000; i++){
			gen.getCountry();
			//System.out.println(gen.getCountry());
		}
		long end = System.currentTimeMillis();
		System.out.println(String.valueOf(end-start));
	}


}
