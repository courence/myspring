/**
 * 
 */
package com.courence;

/**
 * Description:
 * 
 * @author jh date Oct 9, 2016 version V1.0
 */
public class TmpTest {

	public static void main(String[] args) {
		String str1 = new StringBuilder("是的").append("撒地方").toString();
		System.out.println(str1.intern()==str1);
		String str2 = new StringBuilder("ja1").append("va").toString();
		System.out.println(str2.intern()==str2);
	}


}
