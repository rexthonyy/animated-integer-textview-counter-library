package com.rexthonyy.animatedintegertextviewcounter.helpers;

public class Utils {
    public static boolean isFloat(String text){
        try{
            Float.parseFloat(text);
            return true;
        }catch(NumberFormatException e){
        }catch(NullPointerException e){
        }
        return false;
    }
}
