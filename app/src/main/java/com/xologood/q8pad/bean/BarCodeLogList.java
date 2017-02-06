package com.xologood.q8pad.bean;

import java.util.List;

/**
 * Created by Administrator on 17-1-10.
 */

public class BarCodeLogList {

    /**
     * BarCodeLogList : [{"Id":0,"BarCode":"27618582669","IsOk":false,"Remark":"失败，此条码不存在","CreationDate":"0001-01-01 08:00:00","CreationBy":0,"SysKey":null,"keyBase":null,"recorderBase":0,"versionBase":null,"projectNameBase":null,"sysKeyBase":null,"pageIndex":0,"pageSize":0}]
     * InvId : 0
     * Msg : 完成
     */

    private int InvId;
    private String Msg;
    private List<BarCodeLog> BarCodeLogList;

    public int getInvId() {
        return InvId;
    }

    public void setInvId(int invId) {
        InvId = invId;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public List<BarCodeLog> getBarCodeLogList() {
        return BarCodeLogList;
    }

    public void setBarCodeLogList(List<BarCodeLog> barCodeLogList) {
        BarCodeLogList = barCodeLogList;
    }
}
