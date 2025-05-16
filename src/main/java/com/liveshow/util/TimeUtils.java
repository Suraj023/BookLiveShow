package com.liveshow.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeUtils {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * Validates that a given time range is exactly 1 hour long
     * Format: "HH:mm-HH:mm"
     */
    public static boolean isValidOneHourSlot(String timeRange) {
        try {
            String[] times = timeRange.split("-");
            if (times.length != 2) return false;

            LocalTime start = LocalTime.parse(times[0], TIME_FORMATTER);
            LocalTime end = LocalTime.parse(times[1], TIME_FORMATTER);

            return start.plusHours(1).equals(end);
        } catch (DateTimeParseException e) { 
            return false;
        }
    }

    /**
     * Parses start time from time range (e.g., "09:00-10:00" → "09:00")
     */
    public static String getStartTime(String timeRange) {
        return timeRange.split("-")[0];
    }

    /**
     * Parses end time from time range (e.g., "09:00-10:00" → "10:00")
     */
    public static String getEndTime(String timeRange) {
        return timeRange.split("-")[1];
    }

    /**
     * Compares two time ranges by start time.
     * Returns -1 if range1 is earlier, 1 if later, 0 if equal.
     */
    public static int compareByStartTime(String range1, String range2) {
        LocalTime t1 = LocalTime.parse(getStartTime(range1), TIME_FORMATTER);
        LocalTime t2 = LocalTime.parse(getStartTime(range2), TIME_FORMATTER);
        return t1.compareTo(t2);
    }
}
