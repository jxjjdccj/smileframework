package org.smileframework.common.jdk;

import java.util.regex.Pattern;

/**
 * @author: cz
 * @description: 正则表达式工具类
 */
public abstract class PattrnUtil {

    /**
     * 校验昵称是否包含敏感词
     *
     * @param content
     * @return
     */
    public static boolean hasIllegalWord(String content) {
        String firstRegex = "[\\s,\\.;\\:\"'!%^&\\*\\(\\)\\[\\]\\{\\}<>\\?/\\\\~`@#$\\-=\\+\\|]";
        //String chineseRegex = "[^\\u4e00-\\u9fa5]";
        if (content == null || content.trim().length() == 0) return false;
        // 移除空格和标点符号
        content = content.replaceAll(firstRegex, "");
        /*// 移除非中文部分
        String chinese = content.replaceAll(chineseRegex, "");
        if (chinese.length() > 0) {
            if (hasWord(chineseArray, chinese) >= 0) return true;
        }
        if (content.length() > 0) {
            if (hasWord(firstArray, txt) >= 0) return true;
        }*/
        return Pattern.matches("^[a-zA-Z\u4e00-\u9fa5][a-zA-Z0-9\u4e00-\u9fa5_]+$", content);
    }

}
