/*package org.smileframework.common.jdk;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;

*//**
 * @author: cz
 * @description: http工具类
 *//*
public abstract class HttpUtil {

    发送JSON到客户端
    public static Map<String,String> toJsonResult(String result, String message) {
        成功
        if (StringUtil.compare(result, Contants.SUCCESS)) {
            return ImmutableMap.of(Contants.RESULT, Contants.SUCCESS, Contants.MESSAGE, message);
        }
        失败
        return ImmutableMap.of(Contants.RESULT, Contants.FAIL, Contants.MESSAGE, message);
    }

    发送JSON到客户端,带数据格式的方式
    public static  Map<String,Object> toJsonData(String result,String message,Object data){
      成功
        if (StringUtil.compare(result, Contants.SUCCESS)) {
            return ImmutableMap.of(Contants.RESULT, Contants.SUCCESS, Contants.MESSAGE, message,Contants.DATA,data);
        }
        失败
        return ImmutableMap.of(Contants.RESULT, Contants.FAIL, Contants.MESSAGE, message,Contants.DATA, data);
    }
    发送结果到客户端
    public static void sendBooean(HttpServletResponse response, boolean result) {
        try {
            PrintWriter writer = response.getWriter();
            writer.write(String.valueOf(result));
            writer.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    向客户端发送一段动态内容
    public static void sendScript(HttpServletResponse response, String script) {
        try {
            response.setContentType("text/javascript;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(script);
            writer.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    以JSON格式的结果集发送到客户端
    @SuppressWarnings("rawtypes")
	public static Map toJsonResult(Exception exception) {
        return ImmutableMap.of(Contants.RESULT, Contants.FAIL, Contants.MESSAGE,
                exception.getMessage());
    }

    判断用户是否登陆 true
    public static boolean hasLogin(HttpServletRequest request) {
        String cookieValue = CookieUtil.getCookie(request, Contants.COOKIE_NAME);
        return StringUtil.notEmpty(cookieValue);
    }

    设置cookie
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String value) {
        CookieUtil.setCookie(request, response, Contants.COOKIE_NAME, value);
    }

    往客户端生成图像流
    public static void wrietImage(HttpServletResponse response, BufferedImage bufferedImage) {
        response.setDateHeader("Expires", 0);
        //设置用户返回
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        // return a jpeg
        response.setContentType("image/jpeg");
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            // write the data out
            ImageIO.write(bufferedImage, "jpg", out);
            //
            out.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    是否为异步请求
    public static boolean  notAsynch(HttpServletRequest request){
        return !request.getRequestURL().toString().endsWith(".json");
    }



}
*/