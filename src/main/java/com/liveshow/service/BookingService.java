package com.liveshow.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liveshow.model.Booking;
import com.liveshow.model.Show;
import com.liveshow.model.Slot;
import com.liveshow.model.WaitlistEntry;
import com.liveshow.util.TimeUtils;

@Service
public class BookingService {
    private final Map<String, Booking> bookings = new HashMap<>();
    private final Map<String, List<String>> userBookings = new HashMap<>();

    @Autowired private ShowService showService;
//    @Autowired private TrendingService trendingService;

    public String bookTicket(String user, String showName, String time, int count) {
        Show show = showService.getShow(showName);
        if (show == null || !show.getSlots().containsKey(time)) {
            return "Show/Slot not found";
        }

        // Validate time format
        if (!TimeUtils.isValidOneHourSlot(time)) {
            return "Invalid slot time format. Must be 1-hour range (e.g., 09:00-10:00)";
        }

        Slot slot = show.getSlots().get(time);

        if (!isSlotAvailableForUser(user, time)) {
            return "User already booked another show in the same slot.";
        }

        if (slot.canBook(count)) {
            slot.book(count);
            String bookingId = UUID.randomUUID().toString();
            Booking booking = new Booking(bookingId, user, showName, time, count);
            bookings.put(bookingId, booking);
            userBookings.computeIfAbsent(user, k -> new ArrayList<>()).add(bookingId);
//            trendingService.updateTrending(showName, count);
            return "Booked. Booking id: " + bookingId;
        } else {
            slot.getWaitlist().add(new WaitlistEntry(user, count));
            return "Slot full. Added to waitlist.";
        }
    }

    public String cancelBooking(String bookingId) {
        Booking booking = bookings.remove(bookingId);
        if (booking == null) {
            return "Booking not found.";
        }

        Show show = showService.getShow(booking.getShowName());
        Slot slot = show.getSlots().get(booking.getSlotTime());

        slot.cancel(booking.getCount());
        userBookings.getOrDefault(booking.getUser(), new ArrayList<>()).remove(bookingId);

        // Assign booking to first person in waitlist if any
        if (!slot.getWaitlist().isEmpty()) {
            WaitlistEntry entry = slot.getWaitlist().peek();
            if (slot.canBook(entry.getCount())) {
                slot.book(entry.getCount());
                slot.getWaitlist().poll(); // remove the user from waitlist
                String newId = UUID.randomUUID().toString();
                Booking newBooking = new Booking(newId, entry.getUser(), show.getName(), slot.getTime(), entry.getCount());
                bookings.put(newId, newBooking);
                userBookings.computeIfAbsent(entry.getUser(), k -> new ArrayList<>()).add(newId);
//                trendingService.updateTrending(show.getName(), entry.getCount());
            }
        }

        return "Booking Canceled";
    }

    private boolean isSlotAvailableForUser(String user, String slotTime) {
        List<String> bookingIds = userBookings.getOrDefault(user, new ArrayList<>());
        for (String id : bookingIds) {
            Booking b = bookings.get(id);
            if (b != null && b.getSlotTime().equals(slotTime)) {
                return false;
            }
        }
        return true;
    }
}
