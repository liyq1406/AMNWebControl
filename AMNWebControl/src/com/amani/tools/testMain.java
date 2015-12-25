package com.amani.tools;

import java.util.ArrayList;
import java.util.Random;

public class testMain {

	/**
	 * @param args
	 * Oct 18, 201311:54:12 AM
	 * Administrator
	 */
	public static void main(String[] args) {
		System.out.println(getFixLenthString(12));
	}

    /*
     * 返回长度为【strLength】的随机数，在前面补0
     */
    private static String getFixLenthString(int iRandLen) {
        String strRandCode="";
    	ArrayList list = new ArrayList();
        for (int i = 0; i < 10; i++)
                list.add(i);
       for (char c = 'a'; c <= 'z'; c++)
                list.add(c);
       for (int i = 0; i < iRandLen; i++) {
                int mathInt;
                mathInt=(int) (Math.random()*list.size());
                strRandCode=strRandCode+list.get(mathInt);
                list.remove(mathInt);
        }
        return strRandCode;
    }
}
