package com.mvvmhabit.entity;

public class SubmitOrderEntity {
    private String error;
    private String orderIds;
    private String now_time;
    private String out_time;
    private String payTotal;

    public String getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(String orderIds) {
        this.orderIds = orderIds;
    }

    public String getNow_time() {
        return now_time;
    }

    public void setNow_time(String now_time) {
        this.now_time = now_time;
    }

    public String getOut_time() {
        return out_time;
    }

    public void setOut_time(String out_time) {
        this.out_time = out_time;
    }

    public String getPayTotal() {
        return payTotal;
    }

    public void setPayTotal(String payTotal) {
        this.payTotal = payTotal;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
