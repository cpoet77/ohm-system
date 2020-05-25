// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/1.
package cs.ohms.subsystem.utils;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * UUID生成器
 * 生成8位、16位或32位uuid
 * @see UUID
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public class UUIDUtil {
    /**
     * 生成32位有连接符uuid
     * @return uuid string
     */
    @NotNull
    public static String uuid32(){
        return UUID.randomUUID().toString();
    }

    /**
     * 生成32位无连接符uuid
     * @return uuid string
     */
    @NotNull
    public static String uuid32pure(){
        return uuid32().replaceAll("-", "");
    }

    /**
     * 生成8位有连接符uuid
     * @return uuid string
     */
    @NotNull
    public static String uuid8(){
        StringBuilder builder = new StringBuilder(uuid8pure());
        builder.insert(3, '-');
        builder.insert(6, '-');
        return builder.toString();
    }

    /**
     * 生成8位无连接符uuid
     * @return uuid string
     */
    @NotNull
    public static String uuid8pure(){
        return NSMathUtil.dec2hex(UUID.randomUUID().getLeastSignificantBits());
    }

    /**
     * 生成16位有连接符uuid
     * @return uuid string
     */
    @NotNull
    public static String uuid16(){
        StringBuilder builder = new StringBuilder(uuid16pure());
        builder.insert(6, '-');
        builder.insert(11, '-');
        return builder.toString();
    }

    /**
     * 生成16位无连接符uuid
     * @return uuid string
     */
    @NotNull
    public static String uuid16pure(){
        return(uuid8pure() + uuid8pure());
    }
}
