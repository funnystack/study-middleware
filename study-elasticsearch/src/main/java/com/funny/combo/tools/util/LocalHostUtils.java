package com.funny.combo.tools.util;

import org.apache.commons.lang3.StringUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * 本地工具类
 * @author jinhaifeng
 * @date 2019-03-29 20:47 2019-03-30 19:12
 */
public class LocalHostUtils {

    private static String LOCAL_IP = null;

    /**
     * 获取服务器端IP地址
     * 优先从site-local地址获取
     * @return
     * @throws UnknownHostException
     */
    public static InetAddress getLocalHostLANAddress() throws UnknownHostException {
        try {
            InetAddress candidateAddress = null;
            // 遍历所有的网络接口
            for (Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces(); networkInterfaces.hasMoreElements();) {
                NetworkInterface networkInterface = (NetworkInterface) networkInterfaces.nextElement();
                // 在所有的接口下再遍历IP
                for (Enumeration<InetAddress> enumAddresses = networkInterface.getInetAddresses(); enumAddresses.hasMoreElements();) {
                    InetAddress inetAddress = enumAddresses.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {// 排除loopback类型地址
                        if (inetAddress.isSiteLocalAddress()) {
                            // 如果是site-local地址，就是它了
                            return inetAddress;
                        } else if (candidateAddress == null) {
                            // site-local类型的地址未被发现，先记录候选地址
                            candidateAddress = inetAddress;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress;
            }
            // 如果没有发现 non-loopback地址.只能用最次选的方案
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            if (jdkSuppliedAddress == null) {
                throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
            }
            return jdkSuppliedAddress;
        } catch (Exception e) {
            UnknownHostException unknownHostException = new UnknownHostException(
                    "Failed to determine LAN address: " + e);
            unknownHostException.initCause(e);
            throw unknownHostException;
        }
    }

    public static String getLocalIp() {
        if (StringUtils.isNotBlank(LOCAL_IP)) {
            return LOCAL_IP;
        }
        try {
            LOCAL_IP = getLocalHostLANAddress().getHostAddress();
            return LOCAL_IP;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }
}
