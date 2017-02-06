package com.xologood.q8pad.bean;

/**
 * Created by Administrator on 2017/1/3.
 */

public class BaseResponse<T> {
    private T data;
    private String timeSpan;
    private String q8ApiStatus;
    private String memo;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getTimeSpan() {
        return timeSpan;
    }

    public void setTimeSpan(String timeSpan) {
        this.timeSpan = timeSpan;
    }

    public String getQ8ApiStatus() {
        return q8ApiStatus;
    }

    public void setQ8ApiStatus(String q8ApiStatus) {
        this.q8ApiStatus = q8ApiStatus;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
