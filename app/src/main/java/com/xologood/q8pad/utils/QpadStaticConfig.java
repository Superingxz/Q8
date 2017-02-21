package com.xologood.q8pad.utils;

import android.os.Environment;

import com.xologood.q8pad.bean.ScanMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 17-2-9.
 */

public class QpadStaticConfig {

    public List<ScanMessage> scen = new ArrayList<>();
    /**
     * 缓存文件夹路径 参数配置
     */
    public final static class CACHE_PATH {

        public final static String SD_DATA = Environment
                .getExternalStorageDirectory().getAbsolutePath()
                + "/q8pad/data";
        //文件存储位置
        public final static String SD_DOWNLOAD = Environment
                .getExternalStorageDirectory().getAbsolutePath()
                + "/q8pad/download";
        //日志存储位置
        public final static String SD_LOG = Environment
                .getExternalStorageDirectory().getAbsolutePath()
                + "/q8pad/log";
        //图片存储位置
        public final static String SD_KTIMAGE = Environment
                .getExternalStorageDirectory().getAbsolutePath()
                + "/q8pad/ktimage";
        //图片缓存位置
        public final static String SD_IMAGECACHE = Environment
                .getExternalStorageDirectory().getAbsolutePath()
                + "/q8pad/imagecache";
    }

    /**
     * 获取屏幕尺寸
     */
    public final static class SCREEN {
        public static int Width = 0;
        public static int Height = 0;
    }
}
