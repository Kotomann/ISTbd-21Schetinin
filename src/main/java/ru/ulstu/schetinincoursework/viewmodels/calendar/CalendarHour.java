package ru.ulstu.schetinincoursework.viewmodels.calendar;

public class CalendarHour {
    private int hour;
    private CalendarDayOfWeek[] daysOfWeek;

    public CalendarHour(int hour, CalendarDayOfWeek[] daysOfWeek) {
        this.hour = hour;
        this.daysOfWeek = daysOfWeek;
    }

    public String getHour() {
        var hourStr = Integer.toString(hour);
        if (hour < 10)
            hourStr = "0" + hourStr;

        return hourStr;
    }

    public CalendarDayOfWeek[] getDaysOfWeek() {
        return daysOfWeek;
    }
}
