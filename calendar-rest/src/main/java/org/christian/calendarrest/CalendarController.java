package org.christian.calendarrest;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@CrossOrigin
public class CalendarController {

    private FakeRepository fakeRepository = new FakeRepository();
    private CalendarRepository calendarRepository;

    public CalendarController(CalendarRepository calendarRepository) {
        this.calendarRepository = calendarRepository;
    }

    @GetMapping("/events")
    public Flux<CalendarEvent> getCalendarEvents() {
        return calendarRepository.getCalendarEvents();
    }

    @PostMapping("/events")
    public Mono<CalendarEvent> addEvent(@RequestBody CalendarEvent event) {
        System.out.println("postmapping addEvent");
        System.out.println(event.getTitle());
        return calendarRepository.addEvent(event);
    }

}
