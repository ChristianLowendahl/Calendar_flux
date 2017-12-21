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

    private List<CalendarEvent> makeCalendarEvents() {
        List<CalendarEvent> events = new ArrayList<>();
        events.add(CalendarEvent.builder()
                .withTitle("Heldagshändelse")
                .withStart("2017-09-01")
                .build()
        );
        events.add(CalendarEvent.builder()
                .withTitle("Flerdagshändelse")
                .withStart("2017-09-07")
                .withEnd("2017-09-10")
                .build()
        );
        events.add(CalendarEvent.builder()
                .withTitle("Utvecklarkonferens")
                .withStart("2017-09-11")
                .withEnd("2017-09-11")
                .build()
        );
        events.add(CalendarEvent.builder()
                .withTitle("Lunch")
                .withStart("2017-09-12T12:00:00")
                .withEnd("2017-09-12T13:00:00")
                .build()
        );
        events.add(CalendarEvent.builder()
                .withTitle("Hämta barnen på förskolan")
                .withStart("2017-09-12T16:30:00")
                .withEnd("2017-09-12T17:30:00")
                .build()
        );
        events.add(CalendarEvent.builder()
                .withTitle("Sök på Google")
                .withStart("2017-09-15")
                .withUrl("www.google.com")
                .build()
        );
        return events;
    }

    public Flux<CalendarEvent> getCalendarEvents() {
        return Flux.fromIterable(calendarEvents);
    }

    public Mono<CalendarEvent> addEvent(CalendarEvent event) {
        calendarEvents.add(event);
        return Mono.just(event);
    }


    /*
     {
      title: 'All Day Event',
      start: '2017-09-01',
    },
    {
      title: 'Long Event',
      start: '2017-09-07',
      end: '2017-09-10'
    },
    {
      id: 999,
      title: 'Repeating Event',
      start: '2017-09-09T16:00:00'
    },
    {
      id: 999,
      title: 'Repeating Event',
      start: '2017-09-16T16:00:00'
    },
    {
      title: 'Conference',
      start: '2017-09-11',
      end: '2017-09-13'
    },
    {
      title: 'Meeting',
      start: '2017-09-12T10:30:00',
      end: '2017-09-12T12:30:00'
    },
    {
      title: 'Lunch',
      start: '2017-09-12T12:00:00'
    },
    {
      title: 'Meeting',
      start: '2017-09-12T14:30:00'
    },
    {
      title: 'Happy Hour',
      start: '2017-09-12T17:30:00'
    },
    {
      title: 'Dinner',
      start: '2017-09-12T20:00:00'
    },
    {
      title: 'Birthday Party',
      start: '2017-09-13T07:00:00'
    },
    {
      title: 'Click for Google',
      url: 'http://google.com/',
      start: '2017-09-28'
    }];
     */

}
