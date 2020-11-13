package com.mvvmhabit.utils;

public class NumberFormatUtils {

    public static int getInteger(Object intValue, int defaultValue) {

        if(intValue == null || "".equals(intValue.toString().trim())){
            return defaultValue;
        }

        try {
            return Integer.valueOf(intValue.toString());
        } catch (Exception e) {

            try{
                return Double.valueOf(intValue.toString()).intValue();
            }catch (Exception eDouble){
                return defaultValue;
            }
        }
    }

    public static long getLong(Object longValue, long defaultValue) {

        if(longValue == null || "".equals(longValue.toString().trim())){
            return defaultValue;
        }

        try {
            return Long.valueOf(longValue.toString());
        } catch (Exception e) {

            try{
                return Double.valueOf(longValue.toString()).longValue();
            }catch (Exception eDouble){
                return defaultValue;
            }
        }
    }

    public static float getFloat(Object floatValue, float defaultValue) {

        if(floatValue == null || "".equals(floatValue.toString().trim())){
            return defaultValue;
        }

        try {
            return Float.valueOf(floatValue.toString());
        } catch (Exception e) {

            try{
                return Double.valueOf(floatValue.toString()).floatValue();
            }catch (Exception eDouble){
                return defaultValue;
            }
        }
    }

    public static double getDouble(Object doubleValue, double defaultValue) {

        if(doubleValue == null || "".equals(doubleValue.toString().trim())){
            return defaultValue;
        }

        try {
            return Double.valueOf(doubleValue.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

}
