
package com.doll.utils;



public class Hash {

    public static String intToStr(int i) {
        return String.valueOf(i);
    }
    
    public static String longToStr(long i) {
        return String.valueOf(i);
    }
    
    public static String floatToStr(float f) {
        return String.valueOf(f);
    }
    
    
    public static int toInt(Object o) {
    	if(o!=null){
    		return Integer.parseInt(o.toString());
    	}else{
    		return 0;
    	}
        
    } 
    
    public static long toLong(Object o) {
        return Long.parseLong(o.toString());
    }
    
    public static float toFloat(Object o) {
        return Float.parseFloat(o.toString());
    } 
    
    public static String toStr(Object o) {
        return (String)o;
    } 
}
