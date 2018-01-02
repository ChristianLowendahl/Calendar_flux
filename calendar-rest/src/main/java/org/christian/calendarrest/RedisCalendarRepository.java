package org.christian.calendarrest;

import org.springframework.data.redis.core.ReactiveRedisTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class RedisCalendarRepository implements CalendarRepository {

    private ReactiveRedisTemplate<String, CalendarEvent> template;
    public static final String KEY_NAME = "eventKey";
    private Long id;

    public RedisCalendarRepository(ReactiveRedisTemplate template) {
        this.template = template;
    }

    @Override
    public Flux<CalendarEvent> getCalendarEvents() {
        return null;
    }

    @Override
    public Mono<CalendarEvent> addEvent(CalendarEvent event) {
        template.opsForValue().set(KEY_NAME + ":" + getID(), event);
        return Mono.just(event);
    }

    private Long getID() {
        return 1l;
    }

}