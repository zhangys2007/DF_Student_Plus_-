package com.example.df.zhiyun.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimeUtils {
    private static String[] WEEK_NAMES = {"周日","周一","周二","周三","周四","周五","周六"};
    private static SimpleDateFormat m = new SimpleDateFormat("MM", Locale.getDefault());
    private static SimpleDateFormat d = new SimpleDateFormat("dd", Locale.getDefault());
    private static SimpleDateFormat md = new SimpleDateFormat("MM-dd", Locale.getDefault());
    private static SimpleDateFormat ym = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
    private static SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private static SimpleDateFormat ymdSlash = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private static SimpleDateFormat ymdDot = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
    private static SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private static SimpleDateFormat ymdhmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
    private static SimpleDateFormat ymdhm = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
    private static SimpleDateFormat hm = new SimpleDateFormat("HH:mm", Locale.getDefault());
    private static SimpleDateFormat mdhm = new SimpleDateFormat("MM月dd日 HH:mm", Locale.getDefault());
    private static SimpleDateFormat mdhmLink = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());

    public static long parseYmdhms(String time){
        try {
            return ymdhms.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
    public static long parseYmdhm(String time){
        try {
            return ymdhm.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
    /**
     * 年月日[2015-07-28]
     *
     * @param timeInMills
     * @return
     */
    public static String getYmd(long timeInMills) {
        return ymd.format(new java.util.Date(timeInMills));
    }

    /**
     * 年月日[2015/07/28]
     *
     * @param timeInMills
     * @return
     */
    public static String getYmdSlash(long timeInMills) {
        return ymdSlash.format(new java.util.Date(timeInMills));
    }

    /**
     * 年月日[2015.07.28]
     *
     * @param timeInMills
     * @return
     */
    public static String getYmdDot(long timeInMills) {
        return ymdDot.format(new java.util.Date(timeInMills));
    }

    public static String getYmdhms(long timeInMills) {
        return ymdhms.format(new java.util.Date(timeInMills));
    }

    public static String getYmdhmsS(long timeInMills) {
        return ymdhmss.format(new java.util.Date(timeInMills));
    }

    public static String getYmdhm(long timeInMills) {
        return ymdhm.format(new java.util.Date(timeInMills));
    }

    public static String getHm(long timeInMills) {
        return hm.format(new java.util.Date(timeInMills));
    }

    public static String getMd(long timeInMills) {
        return md.format(new java.util.Date(timeInMills));
    }

    public static String getYM(long timeInMills) {
        return ym.format(new java.util.Date(timeInMills));
    }

    public static String getMdhm(long timeInMills) {
        return mdhm.format(new java.util.Date(timeInMills));
    }

    public static String getMdhmLink(long timeInMills) {
        return mdhmLink.format(new java.util.Date(timeInMills));
    }

    public static String getM(long timeInMills) {
        return m.format(new java.util.Date(timeInMills));
    }

    public static String getD(long timeInMills) {
        return d.format(new java.util.Date(timeInMills));
    }

    /**
     * 是否是今天
     *
     * @param timeInMills
     * @return
     */
    public static boolean isToday(long timeInMills) {
        String dest = getYmd(timeInMills);
        String now = getYmd(Calendar.getInstance().getTimeInMillis());
        return dest.equals(now);
    }

    /**
     * 是否是同一天
     *
     * @param aMills
     * @param bMills
     * @return
     */
    public static boolean isSameDay(long aMills, long bMills) {
        String aDay = getYmd(aMills);
        String bDay = getYmd(bMills);
        return aDay.equals(bDay);
    }

    public static boolean isSameDate(String one,String two){
        long time1;
        long time2;
        try {
            time1 = Long.parseLong(one);
            time2 = Long.parseLong(two);
        }catch (Exception e){
            return false;
        }


        return TimeUtils.isSameDay(time1,time2);
    }

    /**
     * 获取年份
     *
     * @param mills
     * @return
     */
    public static int getYear(long mills) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mills);
        return calendar.get(Calendar.YEAR);
    }

    public static String getAgeStr(long birth){
        if(birth == 0){
            return "";
        }else{
            return ""+ getAge(birth)+"岁";
        }
    }

    /**
     * 多少岁
     * @param birth
     * @return
     */
    public static int getAge(long birth) {
        Calendar cal = Calendar.getInstance();

        if (cal.getTimeInMillis() < birth) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTimeInMillis(birth);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            }else{
                age--;
            }
        }
        return age;
    }

    /**
     * 获取月份
     *
     * @param mills
     * @return
     */
    public static int getMonth(long mills) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mills);
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getDay(long mills) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mills);
        return calendar.get(Calendar.DATE);
    }

    public static boolean isAM(long mills){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mills);
        return calendar.get(Calendar.HOUR_OF_DAY) < 12;
    }

    public static String getAMstr(long mills){
        return isAM(mills)?"上午":"下午";
    }


    /**
     * 获取月份的天数
     *
     * @param mills
     * @return
     */
    public static int getDaysInMonth(long mills) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mills);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        switch (month) {
            case Calendar.JANUARY:
            case Calendar.MARCH:
            case Calendar.MAY:
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.OCTOBER:
            case Calendar.DECEMBER:
                return 31;
            case Calendar.APRIL:
            case Calendar.JUNE:
            case Calendar.SEPTEMBER:
            case Calendar.NOVEMBER:
                return 30;
            case Calendar.FEBRUARY:
                return (year % 4 == 0) ? 29 : 28;
            default:
                throw new IllegalArgumentException("Invalid Month");
        }
    }


    /**
     * 获取星期,0-周日,1-周一，2-周二，3-周三，4-周四，5-周五，6-周六
     *
     * @param mills
     * @return
     */
    public static int getWeek(long mills) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mills);

        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 获取星期
     * @param mills
     * @return
     */
    public static String getWeekName(long mills){
        return WEEK_NAMES[getWeek(mills)];
    }

    /**
     * 获取当月第一天的时间（毫秒值）
     *
     * @param mills
     * @return
     */
    public static long getFirstOfMonth(long mills) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mills);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        return calendar.getTimeInMillis();
    }

    /**
     * 毫秒转化为00：00
     * @param duration
     * @return
     */
    public static String formatMusicTime(long duration) {
        String time = "";
        long minute = duration / 60000;
        long seconds = duration % 60000;
        long second = Math.round((int) seconds / 1000);
        if (minute < 10) {
            time += "0";
        }
        time += minute + ":";
        if (second < 10) {
            time += "0";
        }
        time += second;
        return time;
    }
}
