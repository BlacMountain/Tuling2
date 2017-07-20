package com.example.a67342.tuling;

/**
 * Created by 67342 on 2017/7/16.
 */

public class ListData {
    private String content;
    private String mTime;
    public static final int SEND = 1;
    public static final int RECEIVER = 2;
    private int secound;
    private int flag;
    //构造函数
    public ListData(String content,int flag,String time,int secound) {
        setContent(content);
        setFlag(flag);
        setmTime(time);
        setSend(secound);
    }
    //方法
    public void setSend(int secound){this.secound = secound;}
    public int getSend() {return secound;}
    public void setmTime(String time) {this.mTime = time;}
    public String getmTime() {return mTime;}
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public int getFlag() {
        return flag;
    }
    public void setFlag(int flag) {
        this.flag = flag;
    }

}
