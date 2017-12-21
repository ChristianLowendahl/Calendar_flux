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

    @GetMapping("/events")
    public Flux<CalendarEvent> getCalendarEvents() {
        return fakeRepository.getCalendarEvents();
    }

    @PostMapping("/events")
    public Mono<CalendarEvent> addEvent(@RequestBody CalendarEvent event) {
        System.out.println("postmapping addEvent");
        System.out.println(event.getTitle());
        return fakeRepository.addEvent(event);
    }

}
