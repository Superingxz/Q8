package com.xologood.q8pad.bean;

/**
 * Created by wei on 2017/3/7.
 */

public class GridBean {
    int imageId ;
    String title;
    boolean isViserble;//是否显示改该模块

    public GridBean(int imageId, String title) {
        this.imageId = imageId;
        this.title = title;
    }

    public GridBean(int imageId, String title, boolean isViserble) {
        this.imageId = imageId;
        this.title = title;
        this.isViserble = isViserble;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isViserble() {
        return isViserble;
    }

    public void setViserble(boolean isViserble) {
        this.isViserble = isViserble;
    }


    @Override
    public String toString() {
        return "GridBean{" +
                "imageId=" + imageId +
                ", title='" + title + '\'' +
                ", viserble=" + isViserble +
                '}';
    }
}
