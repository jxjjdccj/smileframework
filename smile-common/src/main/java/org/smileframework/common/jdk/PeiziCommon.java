/**
 * 配资使用的共通函数
 * @author kylin
 * @version 1.00
 * @createDate 2015-06-16
 * @updateDate
 *
 */
package org.smileframework.common.jdk;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public abstract class PeiziCommon
{
    // 画面上显示的页数按钮个数
    public static int INT_PAGE_BTN_CNT = 7;

    /**
     * 通过资源文件取得文字 kylin
     * 
     * @param List
     *            <Object> objList 判断对象
     * @return true:对象为空 false:对象不为空
     */
    public static String getMessage(String strMsgID, String... objects)
    {

        // 取得资源文件
        ResourceBundle bundle = ResourceBundle.getBundle("peizi",
                Locale.getDefault());

        // 在资源文件中，通过ID取得文字
        String strMsg = bundle.getString(strMsgID);

        // 对取到的文字，进行参数的格式化
        return String.format(strMsg, new Object[] { objects });
    }

    /**
     * 判断List对象是否为空
     * 
     * @author kylin
     * @param List
     *            <Object> objList 判断对象
     * @return true:对象为空 false:对象不为空
     */
    public static boolean isArrayEmpty(List<?> objList)
    {
        if (objList == null)
        {
            return true;
        }
        if (objList.size() == 0)
        {
            return true;
        }
        return false;
    }

    /**
     * 按数据记录数计算出页数
     * 
     * @param inCnt
     *            记录数
     * @return outPage 页数
     */
    public static int getMaxPage(int inCnt, int maxCnt)
    {
        int outPage = 0;

        outPage = inCnt / maxCnt;

        if (inCnt % maxCnt > 0)
        {
            outPage++;
        }

        return outPage;
    }

}
