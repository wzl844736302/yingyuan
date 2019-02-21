package com.bw.movie.bean;

public class Result<T> {
    private String message;
    private String status;
    private T result;
    private int count;
    private Object[] args;

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    /**
     * appId : wxb3852e6a6b7d9516
     * nonceStr : WQ72A7mNfUaA05Gw
     * partnerId : 1510865081
     * prepayId : wx07084938610450dcaad697243945045542
     * sign : 94F2FD993AB97C002E6CE898B04B578A
     * timeStamp : 1533602976
     * packageValue : Sign=WXPay
     */

    private String appId;
    private String nonceStr;
    private String partnerId;
    private String prepayId;
    private String sign;
    private String timeStamp;
    private String packageValue;
    /**
     * orderId : 20180807084055347
     */

    private String orderId;


    /**
     * 微信
     * @return
     */

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
