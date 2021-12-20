package ru.ulstu.schetinincoursework.viewmodels.calendar;

import java.time.LocalDate;

public class CalendarDayOfWeek {
    private final String name;
    private final String date;
    private final Integer eventId;
    private final String eventName;

    public CalendarDayOfWeek(String name, LocalDate date, Integer eventId, String eventName) {
        this.name = name;
        this.date = date.toString();
        this.eventId = eventId;
        this.eventName = eventName;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public boolean isEventExist() {
        return eventId != null;
    }

    public int getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }
}