package com.liveshow.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.liveshow.service.BookingService;
import com.liveshow.service.ShowService;
//import com.liveshow.service.TrendingService;

@RestController
public class ShowController {
    @Autowired private ShowService showService;
    @Autowired private BookingService bookingService;
//    @Autowired private TrendingService trendingService;

    @PostMapping("/registerShow")
    public String registerShow(@RequestParam String name, @RequestParam String genre) {
        return showService.registerShow(name, genre);
    }

    @PostMapping("/onboardShowSlots")
    public String onboardSlots(@RequestParam String name, @RequestParam Map<String, Integer> slots) {
        return showService.onboardSlots(name, slots);
    }

    @GetMapping("/showAvailByGenre")
    public List<String> showAvailability(@RequestParam String genre) {
        return showService.getShowsByGenre(genre);
    }

    @PostMapping("/bookTicket")
    public String book(@RequestParam String user, @RequestParam String show, @RequestParam String time, @RequestParam int count) {
        return bookingService.bookTicket(user, show, time, count);
    }

    @PostMapping("/cancelBooking")
    public String cancel(@RequestParam String id) {
        return bookingService.cancelBooking(id);
    }

//    @GetMapping("/trendingShow")
//    public String trending() {
//        return trendingService.getTrendingShow();
//    }
}
