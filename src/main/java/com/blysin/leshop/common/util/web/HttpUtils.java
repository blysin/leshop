package com.blysin.leshop.common.util.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * http 工具类
 */
public class HttpUtils {
    public static String getIpAddr(HttpServletRequest request) {
        String ipString = request.getHeader("x-forwarded-for");
        if (ipString == null || ipString.length() == 0 || "unknown".equalsIgnoreCase(ipString)) {
            ipString = request.getHeader("Proxy-Client-IP");
        }
        if (ipString == null || ipString.length() == 0 || "unknown".equalsIgnoreCase(ipString)) {
            ipString = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipString == null || ipString.length() == 0 || "unknown".equalsIgnoreCase(ipString)) {
            ipString = request.getRemoteAddr();
        }
        String[] ips = ipString.split(",");
        String ip = "127.0.0.1";
        if (ips != null && ips.length > 0) {
            ip = ips[0];
        }
        //System.out.println("ip="+ip);

        return ip;
    }
}
