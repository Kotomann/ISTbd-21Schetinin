package ru.ulstu.schetinincoursework.dao;

import org.springframework.stereotype.Component;
import ru.ulstu.schetinincoursework.models.Event;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class EventDao {
    private static int EVENT_INDEX = 0;
    private CopyOnWriteArrayList<Event> events;

    {
        events = new CopyOnWriteArrayList<>();
        events.add(new Event(++EVENT_INDEX, "Совещание 1", "admin, user", LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(12, 0)));
    }

    public CopyOnWriteArrayList<Event> list() {

        return events;
    }

    public Event findById(int id) {
        return events.stream().filter(e -> e.getId() == id).findAny().orElse(null);
    }

    public void create(Event event) {
        event.setId(++EVENT_INDEX);
        events.add(event);
    }

    public void update(int id, Event event) {
        var eventFromDb = findById(id);
        eventFromDb.setName(event.getName());
        eventFromDb.setDate(event.getDate());
        eventFromDb.setStartTime(event.getStartTime());
        eventFromDb.setEndTime(event.getEndTime());
    }

    public void delete(int id) {
        events.removeIf(e -> e.getId() == id);
    }

    public boolean isTimeIntersect(Event event) {
        return list()
                .stream()
                .anyMatch(e -> e.getDate().isEqual(event.getDate())
                        && e.getId() != event.getId()
                        && ((e.getStartTime().compareTo(event.getStartTime()) <= 0 && e.getEndTime().compareTo(event.getStartTime()) >=0)
                        || (e.getStartTime().compareTo(event.getEndTime()) <= 0 && e.getEndTime().compareTo(event.getEndTime()) >= 0)
                        || (e.getEndTime().compareTo(event.getEndTime()) <= 0 && e.getStartTime().compareTo(event.getStartTime()) >= 0)));
    }
}
