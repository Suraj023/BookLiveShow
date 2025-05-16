package com.liveshow.model;

import java.util.Map;
import java.util.TreeMap;

public class Show {
    private final String name;
    private final String genre;
    private final Map<String, Slot> slots = new TreeMap<>(); // Sorted by time

    public Show(String name, String genre) {
        this.name = name;
        this.genre = genre;
    }

    public void addSlot(String time, Slot slot) {
        // Assumes slot already corresponds to the given time
        slots.put(time, slot);
    }

    public Map<String, Slot> getSlots() {
        return slots;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return "Show{" +
                "name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", slots=" + slots.keySet() +
                '}';
    }
}
