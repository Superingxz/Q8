package com.xologood.q8pad.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 17-1-10.
 */

public class StringUtils {
    /**
     * 判断str1字符串是否包含字子字符串str2
     * @param str1
     * @param str2
     * @return
     */
    public static boolean ifIndexOf(String str1,String str2){
        boolean b = false;
        if(str1.indexOf(str2) != -1){
            b = true;
        }
        return b;
    }

    public static boolean IsDigitNumber(String str) {
        return str.matches("[0-9]+");

    }

    /**
     * 截取生产日期 年月日
     *
     * @param str
     * @return
     */
    public static String GetCreationDate(String str) {
        String creationDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(str);
            creationDate = sdf.format(date);
        } catch (ParseException e) {

        }
        return creationDate;
    }

}
