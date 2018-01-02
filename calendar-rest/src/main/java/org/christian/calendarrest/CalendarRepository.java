package org.christian.calendarrest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CalendarRepository {

    Flux<CalendarEvent> getCalendarEvents();
    Mono<CalendarEvent> addEvent(CalendarEvent event);

}
