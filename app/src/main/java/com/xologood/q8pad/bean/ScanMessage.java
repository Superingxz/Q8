package com.xologood.q8pad.bean;

import android.os.Bundle;

/**
 * Created by Administrator on 17-2-21.
 */

public class ScanMessage {
    private String ewm_num;
    private String ewm_type;
    private String pict_width;
    private String pict_height;
    private Bundle pict_bundle;

    public String getEwm_num() {
        return ewm_num;
    }

    public void setEwm_num(String ewm_num) {
        this.ewm_num = ewm_num;
    }

    public String getEwm_type() {
        return ewm_type;
    }

    public void setEwm_type(String ewm_type) {
        this.ewm_type = ewm_type;
    }

    public String getPict_width() {
        return pict_width;
    }

    public void setPict_width(String pict_width) {
        this.pict_width = pict_width;
    }

    public String getPict_height() {
        return pict_height;
    }

    public void setPict_height(String pict_height) {
        this.pict_height = pict_height;
    }

    public Bundle getPict_bundle() {
        return pict_bundle;
    }

    public void setPict_bundle(Bundle pict_bundle) {
        this.pict_bundle = pict_bundle;
    }
}
