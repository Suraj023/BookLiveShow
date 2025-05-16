package com.liveshow.model;


public class Booking {
    private String bookingId;
    private String user;
    private String showName;
    private String slotTime; // Format: "HH:mm-HH:mm"
    private int count; // Number of persons

    public Booking(String bookingId, String user, String showName, String slotTime, int count) {
        this.bookingId = bookingId;
        this.user = user;
        this.showName = showName;
        this.slotTime = slotTime;
        this.count = count;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getUser() {
        return user;
    }

    public String getShowName() {
        return showName;
    }

    public String getSlotTime() {
        return slotTime;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId='" + bookingId + '\'' +
                ", user='" + user + '\'' +
                ", showName='" + showName + '\'' +
                ", slotTime='" + slotTime + '\'' +
                ", count=" + count +
                '}';
    }
}

