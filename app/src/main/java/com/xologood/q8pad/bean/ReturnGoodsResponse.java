package com.xologood.q8pad.bean;

import java.util.List;

/**
 * Created by Administrator on 17-1-19.
 */

public class ReturnGoodsResponse {

    /**
     * IsExistsInvoicing : true
     * BarCodeLogList : [{"Id":0,"BarCode":"5091572974","IsOk":false,"Remark":"失败,条码不存在","CreationDate":"2016-12-12 18:08:24","CreationBy":0,"SysKey":null,"keyBase":null,"recorderBase":0,"versionBase":null,"projectNameBase":null,"sysKeyBase":null,"pageIndex":0,"pageSize":0}]
     * BarCodeOut : []
     * GetAllSum : 0
     * GetSuccessSum : 0
     * GetFailSum : 1
     * CodeDetailSum : 0
     * ResultMemo : null
     */

    private boolean IsExistsInvoicing;
    private int GetAllSum;
    private int GetSuccessSum;
    private int GetFailSum;
    private int CodeDetailSum;
    private Object ResultMemo;
    private List<BarCodeLog> BarCodeLogList;

    public boolean isExistsInvoicing() {
        return IsExistsInvoicing;
    }

    public void setExistsInvoicing(boolean existsInvoicing) {
        IsExistsInvoicing = existsInvoicing;
    }

    public int getGetAllSum() {
        return GetAllSum;
    }

    public void setGetAllSum(int getAllSum) {
        GetAllSum = getAllSum;
    }

    public int getGetSuccessSum() {
        return GetSuccessSum;
    }

    public void setGetSuccessSum(int getSuccessSum) {
        GetSuccessSum = getSuccessSum;
    }

    public int getGetFailSum() {
        return GetFailSum;
    }

    public void setGetFailSum(int getFailSum) {
        GetFailSum = getFailSum;
    }

    public int getCodeDetailSum() {
        return CodeDetailSum;
    }

    public void setCodeDetailSum(int codeDetailSum) {
        CodeDetailSum = codeDetailSum;
    }

    public Object getResultMemo() {
        return ResultMemo;
    }

    public void setResultMemo(Object resultMemo) {
        ResultMemo = resultMemo;
    }

    public List<BarCodeLog> getBarCodeLogList() {
        return BarCodeLogList;
    }

    public void setBarCodeLogList(List<BarCodeLog> barCodeLogList) {
        BarCodeLogList = barCodeLogList;
    }
}
