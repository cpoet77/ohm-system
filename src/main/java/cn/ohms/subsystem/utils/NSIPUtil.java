// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/13.
package cn.ohms.subsystem.utils;

import cn.nsleaf.utils.NSimpleHttp;
import cn.nsleaf.utils.NSimpleHttpException;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;

/**
 * IP地址查询的简单实现
 * <p>不保证持续可用性，但是该接口是目前免费且最精准的</p>
 * <p><b>会使用到易班jdk中的HTTPSimple请求工具</b></p>
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public class NSIPUtil {
    // 请求的api地址
    private final static String GET_API = "https://whois.pconline.com.cn/ipJson.jsp?ip={}&json=true";

    /**
     * 发起请求，获取json字符串
     * @param ipAddr ip地址
     * @return json字符串，返回结果
     */
    public static String get(String ipAddr) throws NSimpleHttpException {
        if(null == ipAddr){
            return null;
        }
        return NSimpleHttp.GET(NStringUtil.joint(GET_API, ipAddr));
    }


    /**
     * 根据请求信息，获取ip地址
     * @param request HttpServletRequest
     * @return ip地址
     */
    public static String getIpAddress(@NotNull HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
