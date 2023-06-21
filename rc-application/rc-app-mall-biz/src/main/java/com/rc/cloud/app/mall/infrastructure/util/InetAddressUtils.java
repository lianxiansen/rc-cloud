package com.rc.cloud.app.mall.infrastructure.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

/**
 * @Author:chenjianxiang
 * @Date 2021/2/9
 * @Description:
 */
public class InetAddressUtils {

    private static final String IPV4_QUAD_REGEX = "(?:[0-9]|[1-9][0-9]|1[0-9][0-9]|2(?:[0-4][0-9]|5[0-5]))";

    private static final String IPV4_REGEX = "(?:" + IPV4_QUAD_REGEX + "\\.){3}" + IPV4_QUAD_REGEX + "$";

    private static final Pattern IPV4_PATTERN = Pattern.compile(IPV4_REGEX);

    private static final String PRIVATE_CLASS_A_REGEX =
            "10\\.(?:" + IPV4_QUAD_REGEX + "\\.){2}" + IPV4_QUAD_REGEX + "$";

    private static final Pattern PRIVATE_CLASS_A_PATTERN = Pattern.compile(PRIVATE_CLASS_A_REGEX);

    private static final String PRIVATE_CLASS_B_SUBSET = "(?:1[6-9]|2[0-9]|3[0-1])";

    private static final String PRIVATE_CLASS_B_REGEX =
            "172\\." + PRIVATE_CLASS_B_SUBSET + "\\." + IPV4_QUAD_REGEX + "\\." + IPV4_QUAD_REGEX + "$";

    private static final Pattern PRIVATE_CLASS_B_PATTERN = Pattern.compile(PRIVATE_CLASS_B_REGEX);

    private static final String PRIVATE_CLASS_C_REGEX =
            "192\\.168\\." + IPV4_QUAD_REGEX + "\\." + IPV4_QUAD_REGEX + "$";

    private static final Pattern PRIVATE_CLASS_C_PATTERN = Pattern.compile(PRIVATE_CLASS_C_REGEX);

    private InetAddressUtils() {
        throw new UnsupportedOperationException("InetAddressUtil is static class, can not be construct to a instance");
    }

    /**
     * 是否是ip v4
     *
     * @param str
     * @return
     */
    public static boolean isIPv4(String str) {
        return IPV4_PATTERN.matcher(str).matches();
    }

    /**
     * 是否是A类私有网段ip
     *
     * @param str
     * @return
     */
    public static boolean isIPv4PrivateClassA(String str) {
        return PRIVATE_CLASS_A_PATTERN.matcher(str).matches();
    }

    /**
     * 是否是B类私有网段ip
     *
     * @param str
     * @return
     */
    public static boolean isIPv4PrivateClassB(String str) {
        return PRIVATE_CLASS_B_PATTERN.matcher(str).matches();
    }

    /**
     * 是否是C类私有网段ip
     *
     * @param str
     * @return
     */
    public static boolean isIPv4PrivateClassC(String str) {
        return PRIVATE_CLASS_C_PATTERN.matcher(str).matches();
    }

    /**
     * 是否是私有网段
     *
     * @param str
     * @return
     */
    public static boolean isIPv4Private(String str) {
        return isIPv4PrivateClassA(str) || isIPv4PrivateClassB(str) || isIPv4PrivateClassC(str);
    }

    /**
     * 获取本机HostName，解析异常时返回空字符串
     *
     * @return
     */
    public static String getLocalHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            // host = "hostname: hostname"
            String host = e.getMessage();
            if (host != null) {
                int colon = host.indexOf(':');
                if (colon > 0) {
                    return host.substring(0, colon);
                }
            }
            return "";
        }
    }

    /**
     * 获取request请求ip地址呀
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {

        String Xip = request.getHeader("X-Real-IP");

        String XFor = request.getHeader("X-Forwarded-For");

        //多次反向代理后会有多个ip值，第一个ip才是真实ip
        if (StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {

            int index = XFor.indexOf(",");

            if (index != -1) {

                return XFor.substring(0, index);
            } else {

                return XFor;
            }
        }

        XFor = Xip;

        if (StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor))
            return XFor;

        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor))
            XFor = request.getHeader("Proxy-Client-IP");

        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor))
            XFor = request.getHeader("WL-Proxy-Client-IP");

        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor))
            XFor = request.getHeader("HTTP_CLIENT_IP");

        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor))
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");

        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor))
            XFor = request.getRemoteAddr();

        return XFor;
    }


    public static String getIpAdd(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
