package ru.ulstu.schetinincoursework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ulstu.schetinincoursework.dao.EventDao;
import ru.ulstu.schetinincoursework.models.Event;

import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/events")
public class EventsController {
    private final EventDao eventDao;
    private final ResourceBundleMessageSource messageSource;

    @Autowired
    public EventsController(EventDao eventDao, ResourceBundleMessageSource messageSource) {
        this.eventDao = eventDao;
        this.messageSource = messageSource;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("event", new Event());
        return "events/create";
    }

    @PostMapping()
    public String create(@ModelAttribute("event") @Valid Event event, BindingResult bindingResult, Locale loc) {
        validateEvent(event, bindingResult, loc);

        if (bindingResult.hasErrors())
            return "events/create";

        eventDao.create(event);
        return "redirect:/";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("event", eventDao.findById(id));
        return "events/edit";
    }

    @PatchMapping("/{id}")
    public String edit(@PathVariable("id") int id, @ModelAttribute("event") @Valid Event event, BindingResult bindingResult, Locale loc) {
        validateEvent(event, bindingResult, loc);

        if (bindingResult.hasErrors())
            return "events/edit";

        eventDao.update(id, event);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        eventDao.delete(id);
        return "redirect:/";
    }

    private void validateEvent (Event event, BindingResult bindingResult, Locale loc) {
        if (event.getStartTime() != null && event.getEndTime() != null && event.getStartTime().isAfter(event.getEndTime()))
            bindingResult.rejectValue("endTime", "error.event", messageSource.getMessage("event.validation.startmoreendtime", new Object[]{}, loc));

        if (event.getDate() != null && event.getStartTime() != null && event.getEndTime() != null && eventDao.isTimeIntersect(event)) {
            bindingResult.rejectValue("startTime", "error.event", messageSource.getMessage("event.validation.intersect", new Object[]{}, loc));
            bindingResult.rejectValue("endTime", "error.event", messageSource.getMessage("event.validation.intersect", new Object[]{}, loc));
        }
    }
}
