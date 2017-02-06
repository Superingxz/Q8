package com.mview.medittext.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wei on 2016/12/23.
 */
public class Keybase {

    /*
 *获得keybase
 *
 */
    public static String getKeyBase(){
        String time = new SimpleDateFormat("yyyy-MM-dd-HH").format(new Date());
        String keyBase = time+"6859854323KJNd@!@^(";
        String keyBaseMD5 = MD5.stringToMD5(keyBase);
        return keyBaseMD5;
    }
}
