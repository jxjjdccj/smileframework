/*package org.smileframework.common.jdk;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ret.security.util.CryptoUtil;


public class CookieUtil {

    public static final String DEFAULT_ENCODING = "UTF-8";
    //public static final int MAX_AGE_YEAR = 60 * 60 * 24 * 365;
   // private static final int MAX_AGE_MONTH = (30 * 24 * 60 * 60); // session scope!
    private static final int MAX_AGE_DAY =60 * 60 * 24 * 365;


    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setDomain(getDomain(request));
        response.addCookie(cookie);
    }


    设置cookie值
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, int expire, boolean encry) {
        if (encry) {    加密
            value = CryptoUtil.desEncryptToHex(value, null);
        }
        try {
            value = URLEncoder.encode(value, DEFAULT_ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(expire);
        cookie.setPath("/");   //根目标可以访问cookie
        //1.同域可以访问  2.localhost时部分浏览器无法寫入
        cookie.setDomain(getDomain(request));
        response.addCookie(cookie);
    }

    设置cookie值
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value) {
        setCookie(request, response, name, value, MAX_AGE_DAY, true);
    }

    获取当前应用所在域
    protected static String getDomain(HttpServletRequest request) {
        String serverName = request.getServerName();
        int index = serverName.indexOf(".");
        if (index >= 0 && serverName.endsWith(".com")) {
            return serverName.substring(index + 1);
        }
        return serverName;
    }

    获取cookie值
    public static String getCookie(HttpServletRequest request, String name) {
        return getCookie(request, name, "", true);
    }


    获取cookie值
    public static String getCookie(HttpServletRequest request, String name, String defaultValue, boolean encrypt) {
        Cookie[] cookies = request.getCookies();
        //部分浏览器首交请求不带cookie,增加异常判断
        if (cookies == null) {
            return defaultValue;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                try {
                    defaultValue = URLDecoder.decode(cookie.getValue(), DEFAULT_ENCODING);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (encrypt) {
                    defaultValue = CryptoUtil.desDecryptFromHex(defaultValue, null);
                }
                return defaultValue;
            }
        }
        return defaultValue;
    }

}*/