package com.liveshow.model;


public class WaitlistEntry {
    private String user;
    private int count; // number of persons the user wants to book for

    public WaitlistEntry(String user, int count) {
        this.user = user;
        this.count = count;
    }

    public String getUser() {
        return user;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "WaitlistEntry{" +
                "user='" + user + '\'' +
                ", count=" + count +
                '}';
    }
}
