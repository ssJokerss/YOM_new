package com.jnu.yomtab;

import java.io.Serializable;

public class Person implements Serializable {//继承接口
    private String title;

    public Person(String title) {
        this.setTitle(title);
       // this.setCoverResourceId(coverResourceId);
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
