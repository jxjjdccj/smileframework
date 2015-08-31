package org.smileframework.common.jdk;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author: cz
 * @description:日期工具类,负责返回指定格式的日期字符串
 */
public abstract class DateUtil {

    private static Calendar calendar = Calendar.getInstance();
    private static SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getInstance();

    /* 日期类型枚举 */
    public enum DateFormatType {
        DEFAULT_TYPE("yyyy-MM-dd HH:mm:ss"),
        SIMPLE_TYPE_ONE("yyyy-MM-dd"),
        SIMPLE_TYPE_TWO("yyyyMMdd"),
        SIMPLE_TYPE_THREE("yyyy.MM.dd"),
        FULL_TYPE("yyyyMMddHHmmss"),
        FULL_SPILE_TYPE("yyyy.MM.dd HH:mm:ss"),
        SIMPLE_VIRGULE_TYPE("yyyy/MM/dd"),
        FULL_VIRGULE_TYPE("yyyy/MM/dd HH:mm:ss"),
        SIMPLE_CN_TYPE("yyyy年MM月dd日"),
        MONTH_CN_TYPE("MM月dd日"),
        FULL_CN_TYPE("yyyy年MM月dd日 HH时mm分ss秒"),
        FUll_TIME_TYPE("HH:mm:ss"),
        HOUR_MINUTE_TIME_TYPE("HH:mm"),
        SIMPLET_TYPE_THREE("MM-dd"),
        TYPE_WITHOUT_SEC("yyyy-MM-dd HH:mm"),
        SIMPLET_TYPE_FOUE("HH时mm分");
        private final String value;

        DateFormatType(String formatStr) {
            this.value = formatStr;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 获取所有的日期格式化类型
     *
     * @return
     */
    public static String[] getAllFormatTypes() {
        DateFormatType[] types = DateFormatType.values();
        List<String> values = new ArrayList<String>();
        for (DateFormatType type : types) {
            values.add(type.getValue());
        }
        return values.toArray(new String[values.size()]);
    }

    /**
     * 解析日期 字符串
     *
     * @param date --日期字符串
     * @param type --日期类型
     * @return Date
     */
    public static Date parse(String date, DateFormatType type) {
        simpleDateFormat.applyPattern(type.getValue());
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 以默认方式生成格式化日期字符串
     *
     * @param date --日期
     * @return String
     */
    public static final String format(Date date) {
        if (ObjectUtil.isEmpty(date)) {
            return "";
        }
        simpleDateFormat.applyPattern(DateFormatType.DEFAULT_TYPE.getValue());
        return simpleDateFormat.format(date);

    }

    /**
     * 通过指定格式化类型生成日期字符串
     *
     * @param date --日期
     * @param type --格式化类型
     * @return String
     */
    public static String format(Date date, DateFormatType type) {
        if (date == null) {
            return "";
        }
        simpleDateFormat.applyPattern(type.getValue());
        return simpleDateFormat.format(date);
    }

    /**
     * 根据timeStamp格式化日期字符串
     *
     * @param longDate --时间
     * @param type     --日期格式化类型
     * @return String
     */

    public static String format(long longDate, DateFormatType type) {
        return format(new Date(longDate), type);
    }

    /**
     * 当前日期字符处格式化
     *
     * @param type --日期格式化类型
     * @return String
     */
    public static String format(DateFormatType type) {
        return format(new Date(), type);
    }

    /**
     * 格式化long类型时间
     *
     * @param dateLong
     * @return
     */
    public static final String format(long dateLong) {
        return format(new Date(dateLong));
    }

    /**
     * 得到日期时间格式例如 2002-03-15 02:32:25
     */
    public static final String formatTime(java.util.Date date) {
        return (new SimpleDateFormat(DateFormatType.DEFAULT_TYPE.getValue())).format(date);
    }


    /**
     * 得到小时，分的格式例如 02:32
     */
    public static final String getFormatTime(java.util.Date date) {
        String Tempstr;
        int i;
        SimpleDateFormat mydate = new SimpleDateFormat(" MMM dd H mm ss,yyyy");
        Tempstr = "";

        mydate.applyPattern("H");
        i = Integer.parseInt(mydate.format(date));
        if (i < 10) {
            Tempstr = Tempstr + "0" + String.valueOf(i) + ":";
        } else {
            Tempstr = Tempstr + String.valueOf(i) + ":";
        }

        mydate.applyPattern("mm");
        i = Integer.parseInt(mydate.format(date));
        if (i < 10) {
            Tempstr = Tempstr + "0" + String.valueOf(i);
        } else {
            Tempstr = Tempstr + String.valueOf(i);
        }

        return Tempstr;
    }

    /**
     * 得到小时的格式例如 02
     */
    public static final String getFormatHour(java.util.Date date) {
        String Tempstr;
        int i;
        SimpleDateFormat mydate = new SimpleDateFormat(" MMM dd H mm ss,yyyy");
        Tempstr = "";

        mydate.applyPattern("H");
        i = Integer.parseInt(mydate.format(date));
        Tempstr = Tempstr + String.valueOf(i);
        return Tempstr;
    }

    /**
     * 得到小时的格式例如 02
     */
    public static final String getFormatMinute(Date date) {

        String Tempstr;
        int i;
        SimpleDateFormat mydate = new SimpleDateFormat(" MMM dd H mm ss,yyyy");
        Tempstr = "";

        mydate.applyPattern("mm");
        i = Integer.parseInt(mydate.format(date));
        Tempstr = Tempstr + String.valueOf(i);
        return Tempstr;
    }

    /**
     * 根据日期得到年份字符串
     */
    public static final String getYear(Date date) {
        String Tempstr;
        int i;
        SimpleDateFormat mydate = new SimpleDateFormat(" MMM dd H mm ss,yyyy");
        Tempstr = "";
        mydate.applyPattern("yyyy");
        i = Integer.parseInt(mydate.format(date));
        Tempstr = Tempstr + String.valueOf(i);
        return Tempstr;
    }

    /**
     * 根据日期得到月份字符串
     */
    public static final String getMonth(Date date) {
        String Tempstr;
        int i;
        SimpleDateFormat mydate = new SimpleDateFormat(" MM dd H mm ss,yyyy");
        Tempstr = "";
        mydate.applyPattern("MM");
        i = Integer.parseInt(mydate.format(date));
        Tempstr = Tempstr + String.valueOf(i);
        return Tempstr;
    }

    /**
     * 根据日期得到日期字符串
     */
    public static final String getDay(Date date) {
        String Tempstr;
        int i;
        SimpleDateFormat mydate = new SimpleDateFormat(" MM dd H mm ss,yyyy");
        Tempstr = "";
        mydate.applyPattern("dd");
        i = Integer.parseInt(mydate.format(date));
        Tempstr = Tempstr + String.valueOf(i);
        return Tempstr;
    }

    /**
     * 根据日期得到小时字符串
     */
    public static final String getHour(Date date) {
        String Tempstr;
        int i;
        SimpleDateFormat mydate = new SimpleDateFormat(" MM dd H mm ss,yyyy");
        Tempstr = "";
        mydate.applyPattern("H");
        i = Integer.parseInt(mydate.format(date));
        Tempstr = Tempstr + String.valueOf(i);
        return Tempstr;
    }

    /**
     * 用于只输入年月日生成long型的时间格式
     */
    public static final long getTimeLong(int yy, int mm, int dd) {
        calendar.clear();
        calendar.set(yy, mm - 1, dd, 0, 0, 0);
        return calendar.getTimeInMillis();

    }

    /**
     * 用于输入年月日小时分生成long型的时间格式
     */
    public static final long getTimeLong(int yy, int mm, int dd, int h, int m) {
        calendar.clear();
        calendar.set(yy, mm - 1, dd, h, m, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 用于只输入年，月生成long型的时间格式
     */
    public static final long getTimeLong(int yy, int mm) {
        calendar.clear();
        calendar.set(yy, mm - 1, 0, 0, 0, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 根据输入的初始日期和新增的月份到新增月份后的总时间
     */
    public static final long getTotalTime(Date startTime, long month) {
        calendar.setTime(startTime);
        calendar.add(Calendar.MONTH, (int) month);
        return calendar.getTimeInMillis();
    }

    /**
     * 用于输入年月日小时分秒生成long型的时间格式
     */
    public static final long getTimeLong(int yy, int mm, int dd, int h, int m,
                                         int sec) {
        calendar.clear();
        calendar.set(yy, mm - 1, dd, h, m, sec);
        return calendar.getTimeInMillis();
    }

    /**
     * 根据输入一个时间得到和现在的时间差
     */
    public static final String getLeaveTime(long tagTime) {
        long nowTime = System.currentTimeMillis();
        long leaveTime = 0;
        if (nowTime > tagTime)
            leaveTime = (nowTime - tagTime) / 1000;
        else
            leaveTime = (tagTime - nowTime) / 1000;
        long date = 0;
        long hour = 0;
        long minute = 0;
        // int second = 0;

        long dateTime = 0;
        long hourTime = 0;
        // long minuteTime = 0;

        String strDate = "";
        String strHour = "";
        String strMinute = "";
        // String strSecond = "";

        date = leaveTime / 86400;
        dateTime = date * 86400;
        hour = (leaveTime - dateTime) / 3600;
        hourTime = hour * 3600;
        minute = (leaveTime - dateTime - hourTime) / 60;
        // minuteTime = minute*60;

        // second = leaveTime - dateTime - hourTime-minuteTime;

        if (date > 0)
            strDate = date + "天";
        if (hour > 0 || (minute > 0 && date > 0))
            strHour = hour + "小时";
        if (minute > 0)
            strMinute = minute + "分";
        // if(second>0)
        // strSecond = second+"秒";

        return strDate + strHour + strMinute;
    }

    public static Date getStartDateByDate(Date date) {
        Date firstDate = null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        firstDate = cal.getTime();
        return firstDate;
    }

    public static Date getCrelineExpireDate(Date date) {
        Date firstDate = null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 14);
        cal.set(Calendar.MINUTE, 54);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        firstDate = cal.getTime();
        return firstDate;
    }

    public static String get4yMd(Date date) {
        return getDateFormate(date, "yyyy-MM-dd");
    }

    public static String getDateFormate(Date date, String formate) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat simpleDateFormate = new SimpleDateFormat(formate);
        return simpleDateFormate.format(date);
    }

    public static int getDayDif(Date date0, Date date1) {
        return DateUtil.getHourDif(date0, date1) / 24;
    }

    public static int getHourDif(Date date0, Date date1) {
        return (int) DateUtil.getMinuteDif(date0, date1) / 60;
    }

    public static long getMinuteDif(Date date0, Date date1) {
        return DateUtil.getSecondDif(date0, date1) / 60;
    }

    public static long getSecondDif(Date date0, Date date1) {
        return DateUtil.getMillisecondDif(date0, date1) / 1000;
    }

    public static long getMillisecondDif(Date date0, Date date1) {
        if (date0 == null || date1 == null) {
            return 0;
        }
        return date0.getTime() - date1.getTime();
    }


    /**
     * 根据输入的天数，获取增加的日期。
     * 例如获取当前的日期加一天
     *
     * @param date0
     * @param date1
     * @return
     */
    public static Date addDay(Date date, int iDate) {
        return addDate(date, Calendar.DAY_OF_MONTH, iDate);
    }

    public static Date addMinute(Date date, int iMinute) {
        return addDate(date, Calendar.MINUTE, iMinute);
    }
    
/*    
    *//**
     * 根据输入的月份，获取增加的月份
     * 例如获取当前月份加一天
     * @param date0
     * @param date1
     * @return
     *//*
    public static Date addMonth(Date date, int iMonth)
    {
        return addDate(date, Calendar.MONTH, iMonth);
    }
    
    */

    /**
     * 根据输入的年份，获取增加的年份
     * 例如获取当前的年份的加一天
     *
     * @param date0
     * @param date1
     * @return
     *//*
    public static Date addYear(Date date, int iYear)
    {
        return addDate(date, Calendar.YEAR, iYear);
    }
    */
    private static Date addDate(Date date, int iArg0, int iDate) {
        Calendar canlendar = Calendar.getInstance();
        canlendar.setTime(date);
        canlendar.add(iArg0, iDate);
        return canlendar.getTime();
    }

    /*获取指定日期之前的日期*/
    public static Date subtract(Date date, int num) {
        Calendar canlendar = Calendar.getInstance();
        canlendar.setTime(date);
        canlendar.add(Calendar.DATE, -num);
        return canlendar.getTime();
    }


    public static Date getEndDateByDate(Date date) {
        Date firstDate = null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        firstDate = cal.getTime();
        return firstDate;
    }

    /**
     * 用年，月，日，时，分，秒构造日期类型
     *
     * @param iYear
     * @param iMonth
     * @param iDate
     * @param iHour
     * @param iMinute
     * @param iSecond
     * @return
     */
    public static Date getDate(int iYear, int iMonth, int iDate, int iHour,
                               int iMinute, int iSecond) {
        iMonth--;
        Calendar canlendar = Calendar.getInstance();
        canlendar.clear();
        canlendar.set(iYear, iMonth, iDate, iHour, iMinute, iSecond);
        return canlendar.getTime();
    }

    /**
     * 构造日期
     *
     * @param iYear
     * @param iMonth
     * @param iDate
     * @return
     */
    public static Date getDate(int iYear, int iMonth, int iDate) {
        return DateUtil.getDate(iYear, iMonth, iDate, 0, 0, 0);
    }

    /*获取当年最大日期*/
    public static String getCurrentYearMaxDate() {
        Calendar canlendar = Calendar.getInstance();
        Date date = canlendar.getTime();
        String year = getYear(date);
        canlendar.clear();
        date = getDate(Integer.parseInt(year), 12, 31);
        return format(date, DateFormatType.SIMPLE_TYPE_ONE);
    }

    /*获取当年最大日期*/
    public static String getCurrentYearMaxDate(int year) {
        Date date = new Date();
        date = getDate(year, 12, 31);
        return format(date, DateFormatType.SIMPLE_TYPE_ONE);
    }

    /*获取一个月后的时间*/
    public static String getOneMonthLaterDate(String baseDate) {
        Date date = new Date();
        if (!StringUtil.isEmpty(baseDate)) {
            date = getDate(baseDate);
        }
        return format(new Date(getTotalTime(date, 1)), DateFormatType.SIMPLE_TYPE_ONE);
    }

    public static void main(String args[]) {
        System.out.println(getOneMonthLaterDate("2015-09-11"));
    }

    /*2015-12-31格式的字符串转化成日期*/
    public static Date getDate(String date) {
        return DateUtil.getDate(Integer.parseInt(date.split("-")[0]), Integer.parseInt(date.split("-")[1]), Integer.parseInt(date.split("-")[2]));
    }


    /**
     * 友好的方式显示时间
     * 截止时间时间到起始时间间隔的时间描述
     *
     * @param fromTime 起始时间
     * @param toTime   截止时间
     * @param format   格式化
     * @param days     超过此天数，将按format格式化显示实际时间
     * @return
     */
    public static String getFriendlyDate(Date fromTime, Date toTime, int days, DateFormatType type) {
        long distanceInMinutes = (toTime.getTime() - fromTime.getTime()) / 60000;
        StringBuffer message = new StringBuffer();
        if (distanceInMinutes == 0) {
            //message = "几秒钟前";
            message.append("几秒钟前");
        } else if (distanceInMinutes >= 1 && distanceInMinutes < 60) {
            // message = distanceInMinutes + "分钟前";
            message.append(distanceInMinutes).append("分钟前");
        } else if (distanceInMinutes >= 60 && distanceInMinutes < 1400) {
            //message = (distanceInMinutes / 60) + "小时前";
            message.append(distanceInMinutes / 60).append("小时前");
        } else if (distanceInMinutes >= 1440 && distanceInMinutes <= (1440 * days)) {
            //message = (distanceInMinutes / 1440) + "天前";
            message.append(distanceInMinutes / 1440).append("天前");
        } else {
            //message = DateUtil.format(fromTime);
            message.append(DateUtil.format(fromTime, type));
        }
        return message.toString();
    }


    /*计算操盘日期*/
    public static String getOperatorDate(Date insertDate) {
        int days = Math.abs(DateUtil.getDayDif(DateUtil.getStartDateByDate(new Date()), DateUtil.getStartDateByDate(insertDate)));
        return getOperatorDate(days);
    }

    /*计算操盘日期*/
    public static String getOperatorDate(int days) {
        int years = days / 365;
        int month = days / 30 - years * 12;
        days = days - years * 365 - month * 30;

        StringBuffer str = new StringBuffer();
        if (years > 0) {
            str.append(years).append("年");
        }
        if (month > 0) {
            str.append(month).append("月");
        }
        if (days >= 0) {
            str.append(days).append("天");
        }
        return str.toString();
    }


    /*判断是否非周末，周末为false,非周为true*/
    public static boolean notWeekend(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return false;
        }
        return true;
    }

    /*获取两个日期的间隔*/
    public static int between(Date smdate, Date bdate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

}