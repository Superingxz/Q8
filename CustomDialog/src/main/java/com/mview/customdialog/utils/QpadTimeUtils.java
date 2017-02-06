package com.mview.customdialog.utils;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by xiao on 2016/12/25 0025.
 */
public class QpadTimeUtils {
    public static String dateFormatDate(Date date) {
        SimpleDateFormat s = new SimpleDateFormat("yyyy/M/d EEEE HH:mm");

        return s.format(date);
    }

    public static String formatDate(Date date){
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formater.format(date);
    }

    /**
     * String 转   Date
     * @param time	yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date getDateByString(String time) {
        Date date = null;
        if (time == null)
            return date;
        String date_format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(date_format);
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getDateByString(String time, String dformat) {
        Date date = null;
        if (time == null)
            return time;
        String date_format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(date_format);
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat s = new SimpleDateFormat(dformat);
        return s.format(date);

    }

    public static String getShortTime(Date date) {
        String shortstring = null;
        // String time = timestampToStr(dateline);
        // Date date = getDateByString(time);
        if (date == null)
            return shortstring;

        long now = Calendar.getInstance().getTimeInMillis();
        long deltime = (now - date.getTime()) / 1000;
        if (deltime > 365 * 24 * 60 * 60) {
            shortstring = (int) (deltime / (365 * 24 * 60 * 60)) + "年前";
        } else if (deltime > 24 * 60 * 60) {
            shortstring = (int) (deltime / (24 * 60 * 60)) + "天前";
        } else if (deltime > 60 * 60) {
            shortstring = (int) (deltime / (60 * 60)) + "小时前";
        } else if (deltime > 60) {
            shortstring = (int) (deltime / (60)) + "分钟前";
        } else {
            shortstring = "刚刚";
        }
        return shortstring;
    }

    public static String getShortTime(long deltime) {
        String shortstring = null;
        // String time = timestampToStr(dateline);
        // Date date = getDateByString(time);
        if (deltime == 0)
            return shortstring;

        long now = Calendar.getInstance().getTimeInMillis();
        deltime = (now - deltime) / 1000;
        if (deltime > 365 * 24 * 60 * 60) {
            shortstring = (int) (deltime / (365 * 24 * 60 * 60)) + "年前";
        } else if (deltime > 24 * 60 * 60) {
            shortstring = (int) (deltime / (24 * 60 * 60)) + "天前";
        } else if (deltime > 60 * 60) {
            shortstring = (int) (deltime / (60 * 60)) + "小时前";
        } else if (deltime > 60) {
            shortstring = (int) (deltime / (60)) + "分前";
        } else {
            shortstring = "刚刚";
        }
        return shortstring;
    }

    // Timestamp转化为String:
    public static String timestampToStr(long dateline) {
        Timestamp timestamp = new Timestamp(dateline * 1000);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 定义格式，不显示毫秒
        return df.format(timestamp);
    }


    public static String getNowTime() {
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return s.format(new Date());
    }

    public static String getNowTime(int min) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, min);// 对小时数进行+2操作,同理,减2为-2
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return s.format(calendar.getTime());
    }

    public static Date getNowDate(int min) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, min);// 对小时数进行+2操作,同理,减2为-2
        return calendar.getTime();
    }

    public static String getAddMinDate(String sdate, int min) {
        Date date = null;
        if (sdate == null)
            return sdate;
        String date_format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(date_format);
        try {
            date = format.parse(sdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, min);// 对小时数进行+2操作,同理,减2为-2
        return format.format(calendar.getTime());
    }


    public static long getTimeStampFromString(String sdate) {
        Date date = null;
        String date_format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(date_format);
        try {
            date = format.parse(sdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.getTimeInMillis();
    }


    public static long getTimeFromString(String sdate) {
        Date date = null;
        String date_format = "yyyy-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(date_format);
        try {
            date = format.parse(sdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.getTimeInMillis();
    }







    /**
     * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
     */
    public static String getNextDay(String nowdate, String delay) {
        try{
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date d = format.parse(nowdate);

            long newTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
            d.setTime(newTime * 1000);
            String mdate = format.format(d);

            return mdate;

        }catch(Exception e){

            return "";

        }
    }

    public static String getHourFormat(int min) {
        String timeH = "";
        if (min < 60){
            timeH = min + "分钟";
        }else {
            timeH = String.valueOf(min / 60) + "小时";

            if (min % 60 > 0)
                timeH += String.valueOf(min % 60) + "分钟";
        }
        return timeH;
    }

    //精确到小数点后两位
    public static String getDecimalFormat(float number){
        DecimalFormat formater = new DecimalFormat("#0.##");
        return formater.format(number);
    }






    /**
     * 获取该日期具体时间  传入 type  返回time
     * @param type 0 yyyy-MM-dd HH:mm:ss
     * @param type 1 yyyy-MM-dd HH:mm
     * @param type 2 yyyy-MM-dd
     *
     * @param time 0 yyyy
     * @param time 1 MM-dd
     * @param time 2 yyyy-MM-dd
     * @param time 3 HH:mm
     * @param time 4 HH:mm:ss
     * @param time 5 MM
     * @param time 6 dd
     * @return
     */
    public static String getYearMonthDay(String time, int type, int num){
        SimpleDateFormat sdf_1 = null;
        switch (type) {
            case 0:
                sdf_1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                break;
            case 1:
                sdf_1 =  new SimpleDateFormat("yyyy-MM-dd HH:mm");
                break;
            case 2:
                sdf_1 =  new SimpleDateFormat("yyyy-MM-dd");
                break;
            default:
                break;
        }

        Date date = null;
        try {
            date = sdf_1.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf_2 = null;

        switch (num) {
            case 0:
                sdf_2 = new SimpleDateFormat("yyyy");
                break;
            case 1:
                sdf_2 = new SimpleDateFormat("MM-dd");
                break;
            case 2:
                sdf_2 = new SimpleDateFormat("yyyy-MM-dd");
                break;
            case 3:
                sdf_2 = new SimpleDateFormat("HH:mm");
                break;
            case 4:
                sdf_2 = new SimpleDateFormat("HH:mm:ss");
                break;
            case 5:
                sdf_2 = new SimpleDateFormat("MM");
                break;
            case 6:
                sdf_2 = new SimpleDateFormat("dd");
                break;
            default:
                break;
        }
        return sdf_2.format(date);
    }

    /**
     * 时间转换格式
     * @param day   2015-01-01
     * @param num 0:2015-1-1
     * @return
     */
    public static String getConversionFormatDay(String day, int num){
        SimpleDateFormat sdf_1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf_1.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat sdf_2 = null;

        switch (num) {
            case 0:
                sdf_2 = new SimpleDateFormat("yyyy-M-d");
                break;
            default:
                break;
        }

        return sdf_2.format(date);
    }


    /**
     * 时间转换格式
     * @param time  	04:01
     * @param num 0: 	4:1
     * @param num 1: 	04:01
     * @return
     */
    public static String getConversionFormatTime(String time, int num){
        SimpleDateFormat sdf_1 = new SimpleDateFormat("HH:mm");
        Date date = null;
        try {
            date = sdf_1.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat sdf_2 = null;

        switch (num) {
            case 0:
                sdf_2 = new SimpleDateFormat("H:m");
                break;
            case 1:
                sdf_2 = new SimpleDateFormat("HH:mm");
                break;
            default:
                break;
        }

        return sdf_2.format(date);
    }

    /**
     * String 转  Date
     * @param time  2015-11-20
     * @param num 0:yyyy-MM-dd HH:mm:ss
     * @param num 1:yyyy-MM-dd HH:mm
     * @param num 2:yyyy-MM-dd
     * @param num 3:HH:mm
     * @return
     */
    public static Date getConversionFormatDateByString(String time, int num) {
        Date date = null;
        if (time == null)
            return date;

        SimpleDateFormat format = null;

        switch (num) {
            case 0:
                format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                break;
            case 1:
                format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                break;
            case 2:
                format = new SimpleDateFormat("yyyy-MM-dd");
                break;
            case 3:
                format = new SimpleDateFormat("HH:mm");
                break;
            default:
                break;
        }

        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     *   Date 转 String
     * @param date  2015-11-20
     * @param num 0:yyyy-MM-dd HH:mm:ss
     * @param num 1:yyyy-MM-dd HH:mm
     * @param num 2:yyyy-MM-dd
     * @param num 3:HH:mm:ss
     * @param num 4:yyyy-MM
     * @return
     */
    public static String getConversionFormatStringByDate(Date date, int num) {
        SimpleDateFormat format = null;
        switch (num) {
            case 0:
                format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                break;
            case 1:
                format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                break;
            case 2:
                format = new SimpleDateFormat("yyyy-MM-dd");
                break;
            case 3:
                format = new SimpleDateFormat("HH:mm:ss");
                break;
            case 4:
                format = new SimpleDateFormat("yyyy-MM");
                break;
            default:
                break;
        }
        return format.format(date);
    }

    /**
     * 获取相隔	小时数  (保留一个小数点)
     * @param startTime
     * @param endTime
     * @param num 0:yyyy-MM-dd HH:mm:ss
     * @param num 1:yyyy-MM-dd HH:mm
     * @param num 2:yyyy-MM-dd
     * @return
     */
    public static float getBeApartHour(String startTime, String endTime, int num){
        Date end = QpadTimeUtils.getConversionFormatDateByString(endTime, num);
        Date start = QpadTimeUtils.getConversionFormatDateByString(startTime, num);

        float time = (float)(end.getTime()-start.getTime())/(float)(1000 * 60 * 60);
        return time;
    }


    /**
     * 获取相隔	分钟数  (整数)
     * @param startTime
     * @param endTime
     * @param num 0:yyyy-MM-dd HH:mm:ss
     * @param num 1:yyyy-MM-dd HH:mm
     * @param num 2:yyyy-MM-dd
     * @return
     */
    public static long getBeApartMinute(String startTime, String endTime, int num){
        Date end = QpadTimeUtils.getConversionFormatDateByString(endTime, num);
        Date start = QpadTimeUtils.getConversionFormatDateByString(startTime, num);
        long time = (end.getTime()-start.getTime())/(1000*60);
        return time;
    }


    /**
     * 获取相隔	毫秒  (整数)
     * @param startTime
     * @param endTime
     * @param num 0:yyyy-MM-dd HH:mm:ss
     * @param num 1:yyyy-MM-dd HH:mm
     * @param num 2:yyyy-MM-dd
     * @return
     */
    public static long getBeApartMillisecond(String startTime, String endTime, int num){
        Date end = QpadTimeUtils.getConversionFormatDateByString(endTime, num);
        Date start = QpadTimeUtils.getConversionFormatDateByString(startTime, num);
        long time = end.getTime()-start.getTime();
        return time;
    }

    /**
     * 获取当前时间
     * @return	String
     * @param num 0:yyyy-MM-dd
     * @param num 1:yyyy-MM-dd HH:mm
     */
    public static String getNowDayAndTime(int num) {
        SimpleDateFormat format = null;
        switch (num) {
            case 0:
                format = new SimpleDateFormat("yyyy-MM-dd");
                break;
            case 1:
                format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                break;
            default:
                break;
        }
        return format.format(new Date());
    }
}
