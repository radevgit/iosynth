/**
 * 
 */
package net.iosynth.util;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.iosynth.gen.GeneratorString;
import net.iosynth.gen.Xoroshiro128;

/**
 * @author rradev
 *
 */
public class Exp1 {

	public Exp1() {

	}
	
	public void sss(){
		String topic = "aaaaa/{$uuid}/bbbbb";
		
		Pattern p = Pattern.compile("\\{\\$uuid}");
		String res = p.matcher(topic).replaceAll("xxx");
		System.out.println(res);
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Exp1 e = new Exp1();
		e.sss();
	}

	
}
