package com.spring.cloud.redis.utils;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * @ClassName LockUtils
 * @Description 工具类
 * @Author zgw
 * @Date 2019/5/16 17:45
 **/
public class LockUtils {

    public LockUtils() {
    }

    public static String getLocalIP() {
        InetAddress ia = null;

        try {
            ia = getLocalHostLANAddress();
        } catch (UnknownHostException var2) {
        }

        return ia == null ? "127.0.0.1" : ia.getHostAddress();
    }

    public static String getLocalMAC() {
        try {
            InetAddress ia = getLocalHostLANAddress();
            byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < mac.length; ++i) {
                if (i != 0) {
                    sb.append("-");
                }

                String s = Integer.toHexString(mac[i] & 255);
                sb.append(s.length() == 1 ? 0 + s : s);
            }

            return sb.toString().toUpperCase().replaceAll("-", "");
        } catch (Exception var5) {
            throw new IllegalStateException("getLocalMAC error");
        }
    }

    private static InetAddress getLocalHostLANAddress() throws UnknownHostException {
        try {
            InetAddress candidateAddress = null;
            Enumeration ifaces = NetworkInterface.getNetworkInterfaces();

            while (ifaces.hasMoreElements()) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                Enumeration inetAddrs = iface.getInetAddresses();

                while (inetAddrs.hasMoreElements()) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {
                        if (inetAddr.isSiteLocalAddress()) {
                            return inetAddr;
                        }

                        if (candidateAddress == null) {
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }

            if (candidateAddress != null) {
                return candidateAddress;
            } else {
                InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
                if (jdkSuppliedAddress == null) {
                    throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
                } else {
                    return jdkSuppliedAddress;
                }
            }
        } catch (Exception var5) {
            UnknownHostException unknownHostException = new UnknownHostException("Failed to determine LAN address: " + var5);
            unknownHostException.initCause(var5);
            throw unknownHostException;
        }
    }

    public static String getJvmPid() {
        String pid = ManagementFactory.getRuntimeMXBean().getName();
        int indexOf = pid.indexOf(64);
        if (indexOf > 0) {
            pid = pid.substring(0, indexOf);
            return pid;
        } else {
            throw new IllegalStateException("ManagementFactory error");
        }
    }
}
