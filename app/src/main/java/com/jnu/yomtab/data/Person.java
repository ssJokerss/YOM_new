package com.jnu.yomtab.data;

import java.io.Serializable;

public class Person implements Serializable {//继承接口
    private String title;
    private String money;
    private String date;
    private String reason;
    public Person(String title,String money,String date,String reason) {
        this.setTitle(title);
       // this.setCoverResourceId(coverResourceId);
        this.setMoney(money);
        this.setDate(date);
        this.setReason(reason);
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
