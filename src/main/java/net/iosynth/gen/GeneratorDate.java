/**
 * 
 */
package net.iosynth.gen;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.logging.Logger;

import javax.crypto.spec.IvParameterSpec;

/**
 * @author rradev
 *
 */
public class GeneratorDate {
	private Xoroshiro128 rnd;
	private SimpleDateFormat dateFmt;
	private SimpleDateFormat dateFmtE;
	private Locale loc;
	private String fmt;
	private static final String fmtE = "yyyy-MM-dd'T'HH:mm:ss";
	Calendar cal;
	long start;
	long end;
	
	private final Logger logger = Logger.getLogger(GeneratorIPv4.class.getName());

	/**
	 * @param rnd 
	 * @param a 
	 * @param b 
	 * @param loc 
	 * @param fmt s
	 * 
	 */
	public GeneratorDate(Xoroshiro128 rnd, String a, String b, Locale loc, String fmt) {
		this.rnd = rnd;
		if(loc == null) {
			this.loc = Locale.US;
		} else {
			this.loc = loc;
		}
		if(fmt == null) {
			this.fmt = fmtE;
		} else {
			this.fmt = fmt;
		}
		this.dateFmt = new SimpleDateFormat(this.fmt, this.loc);
		this.dateFmtE = new SimpleDateFormat(fmtE, Locale.US);
		this.cal = GregorianCalendar.getInstance();
		if(a == null || a.length() == 0){
			logger.severe("This is not a valid date: " + a);
			System.exit(1);
		}
		this.start = parseDate(a);
		if(b == null || b.length() == 0){
			this.end = System.currentTimeMillis();
		} else {
			this.end = parseDate(b);
		}
	}
	
	private long parseDate(String date){
		try {
			cal.setTime(dateFmtE.parse(date));
		} catch (ParseException e) {
			logger.severe("This is not a valid date: " + date);
			System.exit(1);
		}
		return cal.getTimeInMillis();
	}
	
	/**
	 * @return date
	 */
	public String getDate(){
		cal.setTimeInMillis(rnd.nextLong(end-start+1)+start);
		dateFmt.setCalendar(cal);
		return dateFmt.format(cal.getTime());
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// performance: 10^8 getDate() = 55s
		Xoroshiro128 rnd = new Xoroshiro128(123);
		GeneratorDate gen = new GeneratorDate(rnd, "1974-04-21T11:50:23", "1974-04-22T11:50:23", Locale.CHINESE, "E yyyy年MM月d日");
		long start = System.currentTimeMillis();
		for (int i = 0; i < 10; i++) {
			//gen.getDate();
			System.out.println(gen.getDate());
		}
		long end = System.currentTimeMillis();
		System.out.println(String.valueOf(end - start));
	}

}
