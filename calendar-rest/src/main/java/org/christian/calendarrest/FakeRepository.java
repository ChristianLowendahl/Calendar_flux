package org.christian.calendarrest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class FakeRepository {

    private List<CalendarEvent> calendarEvents;

    public FakeRepository() {
        this.calendarEvents = makeCalendarEvents();
    }

    public Flux<CalendarEvent> getCalendarEvents() {
        return Flux.fromIterable(calendarEvents);
    }

    public Mono<CalendarEvent> addEvent(CalendarEvent event) {
        calendarEvents.add(event);
        return Mono.just(event);
    }

    private List<CalendarEvent> makeCalendarEvents() {
        List<CalendarEvent> events = new ArrayList<>();
        events.add(CalendarEvent.builder()
                .withTitle("Heldagshändelse")
                .withStart("2017-09-01")
                .withId("1001")
                .build()
        );
        events.add(CalendarEvent.builder()
                .withTitle("Flerdagshändelse")
                .withStart("2017-09-07")
                .withEnd("2017-09-10")
                .withId("1002")
                .build()
        );
        events.add(CalendarEvent.builder()
                .withTitle("Utvecklarkonferens")
                .withStart("2017-09-11")
                .withEnd("2017-09-11")
                .withId("1003")
                .build()
        );
        events.add(CalendarEvent.builder()
                .withTitle("Lunch")
                .withStart("2017-09-12T12:00:00")
                .withEnd("2017-09-12T13:00:00")
                .withId("1004")
                .build()
        );
        events.add(CalendarEvent.builder()
                .withTitle("Hämta barnen på förskolan")
                .withStart("2017-09-12T16:30:00")
                .withEnd("2017-09-12T17:30:00")
                .withId("1005")
                .build()
        );
        events.add(CalendarEvent.builder()
                .withTitle("Sök på Google")
                .withStart("2017-09-15")
                .withUrl("www.google.com")
                .withId("1006")
                .build()
        );
        events.add(CalendarEvent.builder()
                .withTitle("Återkommande händelse")
                .withStart("2017-09-09T16:00:00")
                .withId("1007")
                .build()
        );
        events.add(CalendarEvent.builder()
                .withTitle("Återkommande händelse")
                .withStart("2017-09-16T16:00:00")
                .withId("1007")
                .build()
        );
        return events;
    }

}
