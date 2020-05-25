// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/7.
package cs.ohms.subsystem.utils;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * 关于string的一些常用方法
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public class NStringUtil {
    // 数字字符0-9
    public final static char[] NS_NUMS = {0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39};
    // 字母字符[a-zA-Z]
    public final static char[] NS_LETTER =  {0x61,0x62,0x63,0x64,0x65,0x66,0x67,0x68
                                            ,0x69,0x6a,0x6b,0x6c,0x6d,0x6e,0x6f,0x70
                                            ,0x71,0x72,0x73,0x74,0x75,0x76,0x77,0x78
                                            ,0x79,0x7a,0x41,0x42,0x43,0x44,0x45,0x46
                                            ,0x49,0x4a,0x4b,0x4c,0x4d,0x4e,0x4f,0x50
                                            ,0x51,0x52,0x53,0x54,0x55,0x56,0x57,0x58
                                            ,0x59,0x5a,0x47,0x48};

    /**
     * 判断string是否有效
     * @param s string
     * @return 无效返回true|否则false
     */
    public static boolean isEmpty(final String s){
        return (s == null || s.isEmpty());
    }

    /**
     * 产生指定长度的字母字符串
     * @param len 长度
     * @return 结果
     */
    @NotNull
    public static String randWord(int len){
        return randChar(NS_LETTER, len);
    }

    /**
     * 产生指定长度的数字字符串
     * @param len 长度
     * @return 数字字符串
     */
    @NotNull
    public static String randNum(int len){
        return randChar(NS_NUMS, len);
    }

    /**
     * 产生指定长度的数字与字母混合字符串
     * @param len 长度
     * @return 生成结果
     */
    @NotNull
    public static String randWordNum(int len){
        char[] chars = new char[NS_LETTER.length + NS_NUMS.length];
        System.arraycopy(NS_NUMS, 0, chars, 0, NS_NUMS.length);
        System.arraycopy(NS_LETTER, 0, chars, NS_NUMS.length, NS_LETTER.length);
        return randChar(chars, len);
    }

    /**
     * 由给定字符表中产生指定长度的字符串
     * @param chars 字符表
     * @param len 生成的长度
     * @return 生成结果
     */
    @NotNull
    public static String randChar(char[] chars, int len){
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for(int i = 0;i < len; ++i){
            builder.append(chars[random.nextInt(chars.length)]);
        }
        return builder.toString();
    }

    /**
     * 字符串拼接方法
     * @param format 格式
     *               <p><b>说明:</b>会按顺序替换format中{}符号,也可以指定下标{0}。使用{}的时候需要转义\\{}</p>
     * @param param 替换参数
     * @return 拼接结果
     */
    @NotNull
    public static String joint(@NotNull String format, Object ... param){
        if(isEmpty(format) || param.length == 0){
            return format;
        }

        char[] arr = format.toCharArray();
        StringBuilder builder = new StringBuilder();
        int index = 0;
        int len = format.length();
        for(int i = 0; i < len; ++i){
            if(arr[i] == '\\' && ++i < len){
                builder.append(arr[i]);
            }else if(arr[i] == '{'){
                if(i != 0 && arr[i - 1] == '\\'){
                    builder.append(arr[i]);
                }else {
                    StringBuilder numBuilder = new StringBuilder();
                    while (++i < len && arr[i] != '}') {
                        numBuilder.append(arr[i]);
                    }
                    String numStr = numBuilder.toString();
                    int num = NStringUtil.isEmpty(numStr) ? index++ : Integer.parseInt(numStr);
                    if (num < param.length) {
                        builder.append(param[num]);
                    } else {
                        builder.append(arr[i - 1]);
                    }
                }
            }else {
                builder.append(arr[i]);
            }
        }
        return builder.toString();
    }
}
