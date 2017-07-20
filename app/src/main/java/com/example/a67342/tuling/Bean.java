package com.example.a67342.tuling;

/**
 * Created by 67342 on 2017/7/17.
 */

public class Bean {
    public int code;
    public String text;


    public int  getCode() {
        return code;
    }

    public void setCode(int  code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    @Override
    public String toString() {
        return "App{" +
                "code='" + code + '\'' +
                ", text='" + text + '}';
    }

}
