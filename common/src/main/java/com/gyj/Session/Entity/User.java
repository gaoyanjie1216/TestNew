package com.gyj.Session.Entity;

/**
 * Created by Gao on 2017/12/27.
 */
public class User {

    private String sessionId;

    private String ip;

    private String firstTime;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(String firstTime) {
        this.firstTime = firstTime;
    }
}
