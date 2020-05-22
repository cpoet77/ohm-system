// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/1.
package cn.ohms.subsystem.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件工具
 * 解决生成jar包后读取资源文件问题
 * <p><b>具体参照各方法注释</b></p>
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public class FileUtil {
    /**
     * 读取文件内容
     *
     * @param fileName 文件名称
     * @return String 文本内容
     * @throws IOException IOException
     */
    @NotNull
    public static String readFileAsString(String fileName) throws IOException {
        InputStream in = getInputStream(fileName);
        StringBuilder builder = new StringBuilder();
        try (InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            char[] chars = new char[64];
            while (-1 != reader.read(chars)) {
                builder.append(String.valueOf(chars));
            }
        } finally {
            in.close();
        }
        return builder.toString();
    }

    /**
     * 获取输入流
     * <p><b>所有文件的读取应使用该方法，已防止jvm环境的问题</b></p>
     *
     * @param fileName 文件名
     * @return FileInputStream
     * @throws IOException IOException
     */
    public static InputStream getInputStream(String fileName) throws IOException {
        // 替换分隔符
        File file = new File(fileName);
        // 文件存在且可读的情况下，直接返回输入流
        if (file.exists() && file.isFile() && file.canRead()) {
            return (new FileInputStream(file));
        }
        // 使用类加载器进行加载
        return FileUtil.class.getResourceAsStream(fileName);
    }

    /**
     * 获取资源文件URL
     *
     * @param fileName 文件路径
     * @return URL
     */
    public static URL getSources(String fileName) {
        return (FileUtil.class.getResource(fileName));
    }

    /**
     * 文件分隔符替换
     * <div>
     *     <b>注意：</b>
     *     <p>
     *         1、开发的时候使用"/"作为默认分隔符
     *         2、除resource下的资源文件以外，尽量不要使用绝对路径
     *         3、resource下文件的读取使用绝对路径，"/"开始
     *     </p>
     * </div>
     *
     * @param path 路径
     * @return 替换以后
     */
    @NotNull
    public static String replaceSeparator(String path) {
        if (File.separator.equals("\\")) {
            return path.replaceAll("/", "\\\\");
        }
        return path;
    }

    /**
     * 获取文件后缀，带"."
     *
     * @param file file name
     * @return file postfix
     */
    public static String getFilePostfix(String file) {
        Pattern pattern = Pattern.compile("^.*(\\.[a-zA-Z0-9]{1,4})$");
        Matcher matcher = pattern.matcher(file);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return "";
    }

    /**
     * 去除文件后缀
     *
     * @param file file
     * @return file name
     */
    public static String getFileNameNoFix(String file) {
        Pattern pattern = Pattern.compile("^(^.*)\\.[a-zA-Z0-9]{1,4}$");
        Matcher matcher = pattern.matcher(file);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return "";
    }

    /**
     * 获取文件类型
     *
     * @param path Path
     * @return ContentType | null
     */
    @Nullable
    public static String getFileContentType(Path path) {
        try {
            return Files.probeContentType(path);
        } catch (IOException ignored) {
        }
        return null;
    }
}
