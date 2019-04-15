package com.example.moneyapp;

import java.io.Serializable;
import java.util.UUID;

public class RecordBean implements Serializable {
    //    消费类别
    public enum RecordType{
        RECORD_TYPE_EXPENSE,RECORD_TYPE_INCOME
    }

    //消费金额

    private double amount;
    //消费类别
    private RecordType type;
    //消费类型
    private String category;
    //备注
    private String remark;
    //日期 2019-1-1
    private String date;
    //时间 unix时间戳
    private long timetamp;
    //每一笔都是唯一的

    private String uuid;
    //构造方法
    public RecordBean(){
        uuid = UUID.randomUUID().toString();//id
        timetamp = System.currentTimeMillis();//time
        date = DateUtil.getFormattedDate();
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getType() {
        if (this.type == RecordType.RECORD_TYPE_EXPENSE){
            return 1;
        }else {
            return 2;
        }
    }

    public void setType(int type) {
        if (type == 1){
            this.type = RecordType.RECORD_TYPE_EXPENSE;
        }else {
            this.type = RecordType.RECORD_TYPE_INCOME;
        }
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getTimetamp() {
        return timetamp;
    }

    public void setTimetamp(long timetamp) {
        this.timetamp = timetamp;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
