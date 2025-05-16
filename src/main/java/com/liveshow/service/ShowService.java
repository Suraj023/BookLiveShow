package com.liveshow.service;

import com.liveshow.model.Show;
import com.liveshow.model.Slot;
import com.liveshow.util.TimeUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShowService {

    // In-memory store: show name -> Show object
    private final Map<String, Show> showMap = new HashMap<>();

    // Genre-wise index for fast retrieval
    private final Map<String, List<String>> genreIndex = new HashMap<>();

    /**
     * Register a new show with a given genre.
     */
    public String registerShow(String name, String genre) {
        if (showMap.containsKey(name)) {
            return name + " is already registered.";
        }

        Show show = new Show(name, genre);
        showMap.put(name, show);
        genreIndex.computeIfAbsent(genre.toLowerCase(), g -> new ArrayList<>()).add(name);
        return name + " show is registered !!";
    }

    /**
     * Onboard show with one or more slots.
     * Each slot must be exactly 1 hour (e.g., 09:00-10:00).
     * Overlapping slots for the same show are not allowed.
     */
    public String onboardSlots(String showName, Map<String, ?> slotsWithCapacity) {
        Show show = showMap.get(showName);
        if (show == null) {
            return "Show not found.";
        }
        System.out.println("slotsWithCapacity:  "+slotsWithCapacity);
        boolean atLeastOneSlotAdded = false;

        for (Map.Entry<String, ?> entry : slotsWithCapacity.entrySet()) {
            String time = entry.getKey();
            Object value = entry.getValue();
            System.out.println("time:  "+time);
            System.out.println("value: "+value);
            // Skip entries like 'name' which are not valid 1-hour slot times
            if (!TimeUtils.isValidOneHourSlot(time)) {
                System.out.println("Skipping invalid slot key: " + time);
                continue;
            }

            int capacity;
            try {
                if (value instanceof Integer) {
                    capacity = (Integer) value;
                } else if (value instanceof String) { 
                    capacity = Integer.parseInt((String) value);
                } else {
                    System.out.println("Skipping invalid capacity type for slot " + time);
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Skipping slot with invalid capacity: " + time + " -> " + value);
                continue;
            }

            if (show.getSlots().containsKey(time)) {
                System.out.println("Slot already exists, skipping: " + time);
                continue;
            }

            Slot slot = new Slot(time, capacity);
            show.addSlot(time, slot); // ✅ Assumes this updates the internal map correctly
//            System.out.println("Slot added: " + time + " with capacity " + capacity);
            atLeastOneSlotAdded = true;
        }

        return atLeastOneSlotAdded ? "Done!" : "No valid slots were added.";
    }




    /**
     * Get all shows with their available slots based on genre.
     * Sorted by start time by default.
     */
    public List<String> getShowsByGenre(String genre) {
        List<String> result = new ArrayList<>();
        List<String> shows = genreIndex.getOrDefault(genre.toLowerCase(), new ArrayList<>());
        System.out.println(shows);
        for (String showName : shows) {
            Show show = showMap.get(showName);
            if (show == null) continue;
            System.out.println(show);
            for (Map.Entry<String, Slot> entry : show.getSlots().entrySet()) {
                Slot slot = entry.getValue();
                if (slot.getRemainingCapacity() > 0) {
                    result.add(show.getName() + ": (" + slot.getTime() + ") " + slot.getRemainingCapacity());
                }
            }
        }

        return result;
    }

    public Show getShow(String name) {
        return showMap.get(name);
    }
}
