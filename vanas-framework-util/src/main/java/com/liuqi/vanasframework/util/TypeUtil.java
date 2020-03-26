package com.liuqi.vanasframework.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 类说明 <br>
 *     类型转化工具
 *
 * @author : alexliu
 * @version v1.0 , Create at 20:13 2020-03-26
 */
public class TypeUtil {

    public static <T> T cast(Object obj , Class<T> clazz){
        if(clazz == null){
            throw new IllegalArgumentException("clazz is null");
        }
        if(clazz == obj.getClass()){
            return (T) obj;
        }
        if(obj instanceof Map){
            if(clazz == Map.class){
                return (T) obj;
            }
        }
        if(obj instanceof List){
            if(clazz == List.class){
                return (T) obj;
            }
        }
        if(obj instanceof Collection){
            if(clazz == List.class){
                return (T) obj;
            }
        }
        if(clazz.isAssignableFrom(obj.getClass())){
            return (T) obj;
        }
        if(clazz == boolean.class || clazz == Boolean.class){
            return (T) castToBoolean(obj);
        }
        if(clazz == byte.class || clazz == Byte.class){
            return (T) castToByte(obj);
        }
        if(clazz == char.class || clazz == Character.class){
            return (T) castToChar(obj);
        }
        if(clazz == short.class || clazz == Short.class){
            return (T) castToShort(obj);
        }
        if(clazz == int.class || clazz == Integer.class){
            return (T) castToInt(obj);
        }
        if(clazz == long.class || clazz == Long.class){
            return (T) castToLong(obj);
        }
        if(clazz == float.class || clazz == Float.class){
            return (T) castToFloat(obj);
        }
        if(clazz == double.class || clazz == Double.class){
            return (T) castToDouble(obj);
        }
        if(clazz == String.class){
            return (T) castToString(obj);
        }
        if(clazz == BigDecimal.class){
            return (T) castToBigDecimal(obj);
        }
        if(clazz == BigInteger.class){
            return (T) castToBigInteger(obj);
        }
        if(clazz == Date.class){
            return (T) castToDate(obj);
        }

        throw new RuntimeException("unKnow your classType : " + clazz.getName());
    }


    public static Integer castToInt(Object value){
        if(value == null){
            return null;
        }
        if(value instanceof Integer){
            return (Integer) value;
        }
        if(value instanceof Number){
            return ((Number) value).intValue();
        }
        if(value instanceof String){
            String strVal = (String) value;
            if(strVal.length() == 0 //
                    || "null".equals(strVal) //
                    || "NULL".equals(strVal)){
                return null;
            }
            return Integer.parseInt(strVal);
        }
        if(value instanceof Boolean){
            return ((Boolean) value).booleanValue() ? 1 : 0;
        }
        throw new RuntimeException("can not cast to int, value : " + value);
    }

    public static Date castToDate(Object value){
        if(value == null){
            return null;
        }

        if(value instanceof Date){
            return (Date)value;
        }
        throw new RuntimeException("can not cast to date, value : " + value);
    }

    public static String castToString(Object value){
        if(value == null){
            return null;
        }
        return value.toString();
    }


    public static Byte castToByte(Object value){
        if(value == null){
            return null;
        }
        if(value instanceof Number){
            return ((Number) value).byteValue();
        }
        if(value instanceof String){
            String strVal = (String) value;
            if(strVal.length() == 0 //
                    || "null".equals(strVal) //
                    || "NULL".equals(strVal)){
                return null;
            }
            return Byte.parseByte(strVal);
        }
        throw new RuntimeException("can not cast to byte, value : " + value);
    }

    public static Character castToChar(Object value){
        if(value == null){
            return null;
        }
        if(value instanceof Character){
            return (Character) value;
        }
        if(value instanceof String){
            String strVal = (String) value;
            if(strVal.length() == 0){
                return null;
            }
            if(strVal.length() != 1){
                throw new RuntimeException("can not cast to char, value : " + value);
            }
            return strVal.charAt(0);
        }
        throw new RuntimeException("can not cast to char, value : " + value);
    }

    public static byte[] castToBytes(Object value){
        if(value instanceof byte[]){
            return (byte[]) value;
        }
        throw new RuntimeException("can not cast to int, value : " + value);
    }

    public static Boolean castToBoolean(Object value){
        if(value == null){
            return null;
        }
        if(value instanceof Boolean){
            return (Boolean) value;
        }
        if(value instanceof Number){
            return ((Number) value).intValue() == 1;
        }
        if(value instanceof String){
            String strVal = (String) value;
            if(strVal.length() == 0 //
                    || "null".equals(strVal) //
                    || "NULL".equals(strVal)){
                return null;
            }
            if("true".equalsIgnoreCase(strVal) //
                    || "1".equals(strVal)){
                return Boolean.TRUE;
            }
            if("false".equalsIgnoreCase(strVal) //
                    || "0".equals(strVal)){
                return Boolean.FALSE;
            }
            if("Y".equalsIgnoreCase(strVal) //
                    || "T".equals(strVal)){
                return Boolean.TRUE;
            }
            if("F".equalsIgnoreCase(strVal) //
                    || "N".equals(strVal)){
                return Boolean.FALSE;
            }
        }
        throw new RuntimeException("can not cast to boolean, value : " + value);
    }

    public static Short castToShort(Object value){
        if(value == null){
            return null;
        }
        if(value instanceof Number){
            return ((Number) value).shortValue();
        }
        if(value instanceof String){
            String strVal = (String) value;
            if(strVal.length() == 0 //
                    || "null".equals(strVal) //
                    || "NULL".equals(strVal)){
                return null;
            }
            return Short.parseShort(strVal);
        }
        throw new RuntimeException("can not cast to short, value : " + value);
    }

    public static BigDecimal castToBigDecimal(Object value){
        if(value == null){
            return null;
        }
        if(value instanceof BigDecimal){
            return (BigDecimal) value;
        }
        if(value instanceof BigInteger){
            return new BigDecimal((BigInteger) value);
        }
        String strVal = value.toString();
        if(strVal.length() == 0){
            return null;
        }
        if(value instanceof Map && ((Map) value).size() == 0){
            return null;
        }
        return new BigDecimal(strVal);
    }

    public static BigInteger castToBigInteger(Object value){
        if(value == null){
            return null;
        }
        if(value instanceof BigInteger){
            return (BigInteger) value;
        }
        if(value instanceof Float || value instanceof Double){
            return BigInteger.valueOf(((Number) value).longValue());
        }
        String strVal = value.toString();
        if(strVal.length() == 0 //
                || "null".equals(strVal) //
                || "NULL".equals(strVal)){
            return null;
        }
        return new BigInteger(strVal);
    }

    public static Float castToFloat(Object value){
        if(value == null){
            return null;
        }
        if(value instanceof Number){
            return ((Number) value).floatValue();
        }
        if(value instanceof String){
            String strVal = value.toString();
            if(strVal.length() == 0 //
                    || "null".equals(strVal) //
                    || "NULL".equals(strVal)){
                return null;
            }
            if(strVal.indexOf(',') != 0){
                strVal = strVal.replaceAll(",", "");
            }
            return Float.parseFloat(strVal);
        }
        throw new RuntimeException("can not cast to float, value : " + value);
    }

    public static Double castToDouble(Object value){
        if(value == null){
            return null;
        }
        if(value instanceof Number){
            return ((Number) value).doubleValue();
        }
        if(value instanceof String){
            String strVal = value.toString();
            if(strVal.length() == 0 //
                    || "null".equals(strVal) //
                    || "NULL".equals(strVal)){
                return null;
            }
            if(strVal.indexOf(',') != 0){
                strVal = strVal.replaceAll(",", "");
            }
            return Double.parseDouble(strVal);
        }
        throw new RuntimeException("can not cast to double, value : " + value);
    }

    public static Long castToLong(Object value){
        if(value == null){
            return null;
        }
        if(value instanceof Number){
            return ((Number) value).longValue();
        }
        if(value instanceof String){
            String strVal = (String) value;
            if(strVal.length() == 0 //
                    || "null".equals(strVal) //
                    || "NULL".equals(strVal)){
                return null;
            }
            try{
                return Long.parseLong(strVal);
            } catch(NumberFormatException ex){
                //
            }
        }

        throw new RuntimeException("can not cast to long, value : " + value);
    }
}