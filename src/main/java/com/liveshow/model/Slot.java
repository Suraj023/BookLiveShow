package com.liveshow.model;

import java.util.LinkedList;
import java.util.Queue;

public class Slot {
    private final String time; // Format: "HH:mm-HH:mm"
    private final int capacity;
    private int booked;
    private final Queue<WaitlistEntry> waitlist;

    public Slot(String time, int capacity) {
        this.time = time;
        this.capacity = capacity;
        this.booked = 0;
        this.waitlist = new LinkedList<>();
    }

    public String getTime() {
        return time;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getBooked() {
        return booked;
    }

    public int getRemainingCapacity() {
        return capacity - booked;
    }

    public boolean canBook(int people) {
        return getRemainingCapacity() >= people;
    }

    public void book(int people) {
        if (!canBook(people)) {
            throw new IllegalArgumentException("Not enough capacity to book.");
        }
        this.booked += people;
    }

    public void cancel(int people) {
        this.booked -= people;
        if (this.booked < 0) {
            this.booked = 0;
        }
    }

    public void addToWaitlist(String user, int count) {
        waitlist.add(new WaitlistEntry(user, count));
    }

    public WaitlistEntry pollWaitlist() {
        return waitlist.poll();
    }

    public boolean hasWaitlist() {
        return !waitlist.isEmpty();
    }

    public Queue<WaitlistEntry> getWaitlist() {
        return new LinkedList<>(waitlist); // return copy to protect encapsulation
    }

    @Override
    public String toString() {
        return "Slot{" +
                "time='" + time + '\'' +
                ", capacity=" + capacity +
                ", booked=" + booked +
                ", waitlistSize=" + waitlist.size() +
                '}';
    }
}
