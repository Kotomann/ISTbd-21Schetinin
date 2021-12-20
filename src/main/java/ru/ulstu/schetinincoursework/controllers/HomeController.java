package ru.ulstu.schetinincoursework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ulstu.schetinincoursework.dao.EventDao;
import ru.ulstu.schetinincoursework.services.CalendarService;

import java.time.LocalDate;
import java.util.Locale;

@Controller
public class HomeController {

    private final EventDao eventDao;
    private final CalendarService calendarService;

    @Autowired
    public HomeController(EventDao eventDao, CalendarService calendarService) {
        this.eventDao = eventDao;
        this.calendarService = calendarService;
    }

    @GetMapping("/")
    public String index(@RequestParam(name = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, Model model, Locale loc) {
        date = date != null ? date : LocalDate.now();

        var calendar = calendarService.getCalendar(date, loc);
        var week = calendar[0].getDaysOfWeek();

        model.addAttribute("week", week);
        model.addAttribute("calendar", calendar);
        model.addAttribute("events", eventDao.list());

        model.addAttribute("nextweekdate", date.plusDays(7).toString());
        model.addAttribute("prevweekdate", date.minusDays(7).toString());
        return "home/index";
    }
}