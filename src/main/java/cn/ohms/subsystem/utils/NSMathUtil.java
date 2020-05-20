// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/2.
package cn.ohms.subsystem.utils;

import org.jetbrains.annotations.NotNull;

/**
 * 简单数学方法实现
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public class NSMathUtil {
    /* 0-9,a - f */
    private final static char[] BASE = {
            0x30, 0x31, 0x32, 0x33,
            0x34, 0x35, 0x36, 0x37,
            0x38, 0x39, 0x61, 0x62,
            0x63, 0x64, 0x65, 0x66};

    /**
     * 10进制转16进制
     * 其它参考Integer.toHexString()
     * @see Integer
     * @param dec 10进制数
     * @return 16进制字符串
     */
    @NotNull
    public static String dec2hex(int dec){
        StringBuilder builder = new StringBuilder();
        do{
            builder.append(BASE[dec & 0xf]);
            dec >>>= 4;
        }while (dec > 0);
        return builder.reverse().toString();
    }

    /**
     * 10进制转16进制
     * 其它参考Long.toHexString()
     * @see Long
     * @param dec 10进制数
     * @return 16进制字符串
     */
    @NotNull
    public static String dec2hex(long dec){
        int h = (int)(dec >>> 32);
        int l = (int)dec;
        return h > 0 ? (dec2hex(h) + dec2hex(l)) : dec2hex(l);
    }

    /**
     * 产生int随机数
     * @param max 最大值
     * @return 伪随机数
     */
    public static int randomInt(int max){
        return randomInt(0, max);
    }

    /**
     * 产生int随机数
     * @param min 最小值
     * @param max 最大值
     * @return 伪随机数
     */
    public static int randomInt(int min, int max){
        return (int)randomDouble(min, max);
    }

    /**
     * 产生double随机数
     * @param max 最大值
     * @return 伪随机数
     */
    public static double randomDouble(double max){
        return randomDouble(0, max);
    }

    /**
     * 产生double随机数
     * @param min 最小值
     * @param max 最大值
     * @return 伪随机数
     */
    public static double randomDouble(double min, double max){
        return (Math.random() * (max - min) + min);
    }
}
