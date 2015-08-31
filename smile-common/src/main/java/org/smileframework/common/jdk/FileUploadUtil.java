package org.smileframework.common.jdk;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author: cz
 * @description:上传文件工具
 */
public class FileUploadUtil {

    public static final List<String> ALLOW_TYPES = Arrays.asList(
            "image/jpg", "image/jpeg", "image/png", "image/gif"
    );

    /*文件重命名*/
    public static String rename(String name) {
        int i = name.lastIndexOf(".");
        String str = name.substring(i);
        return new Date().getTime() + "" + new Random().nextInt(99999999) + str;
    }

    /*图片存放的目录
    例 2014/01/02的方式
    * */
    public static String imageDirectory() {
        return DateUtil.format(new Date(), DateUtil.DateFormatType.SIMPLE_TYPE_ONE).replace("-", "/");
    }


    /*校验文件类型是否允许上传*/
    public static boolean allowUpload(String postfix) {
        return ALLOW_TYPES.contains(postfix);
    }
}
