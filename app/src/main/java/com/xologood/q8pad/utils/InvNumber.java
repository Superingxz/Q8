package com.xologood.q8pad.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wei on 2017/1/5.
 */

public class InvNumber {


    /**
     *生成单号
     * @param  i    1表示入库,2表示出库，
     * @param userId  用户id
     * @return
     */
    public static String getInvNumber(int i,int userId){
        DecimalFormat df=new DecimalFormat("000000");
        String str=df.format(userId);
        String time = new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date());
        int round = (int)(Math.random()*90)+10;

        String invNumber = null;
        if (i==1){
            invNumber = "A"+str+time+round;
        }else if (i==2){
            invNumber = "L"+str+time+round;
        }
        return invNumber;
    }


    public static String getInvNumber(int i,String userId){
        DecimalFormat df=new DecimalFormat("000000");
        String str=df.format(Integer.parseInt(userId));
        String time = new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date());
        int round = (int)(Math.random()*90)+10;

        String invNumber = null;
        if (i==1){
            invNumber = "A"+str+time+round;
        }else if (i==2){
            invNumber = "L"+str+time+round;
        }
        return invNumber;
    }
}
