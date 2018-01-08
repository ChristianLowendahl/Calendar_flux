package org.christian.calendarrest;

import org.springframework.data.redis.connection.ReactiveNumberCommands;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class RedisCalendarRepository implements CalendarRepository {

    private ReactiveRedisTemplate<String, String> template;
    private ReactiveNumberCommands numberCommands;
    public static final String EVENT_KEY = "event";
    private byte[] EVENT_KEY_BYTES;

    public RedisCalendarRepository(ReactiveRedisTemplate<String, String> template, ReactiveNumberCommands numberCommands) {
        this.template = template;
        this.numberCommands = numberCommands;
        try {
            this.EVENT_KEY_BYTES = EVENT_KEY.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            this.EVENT_KEY_BYTES = new byte[0];
        }
    }

    @Override
    public Flux<CalendarEvent> getCalendarEvents() {
        return template
                .opsForList()
                .range("events",0, -1)
                .flatMap(eventId -> template.opsForValue(makeSerializationContext())
                    .get(EVENT_KEY + ":" + eventId)
                );
    }

    @Override
    public Mono<CalendarEvent> addEvent(CalendarEvent event) {
        RedisSerializationContext<String, CalendarEvent> serializationContext = makeSerializationContext();
        return numberCommands
                    .incr(ByteBuffer.wrap(EVENT_KEY_BYTES))
                    .log()
                    .map(id -> CalendarEvent.builder()
                            .withId(id.toString())
                            .withEnd(event.getEnd())
                            .withStart(event.getStart())
                            .withTitle(event.getTitle())
                            .withUrl(event.getUrl())
                            .build()
                    )
                    .flatMap(ce -> template
                            .opsForValue(serializationContext)
                            .set(EVENT_KEY + ":" + ce.getId(), ce)
                            .map(b -> ce)
                    )
                    .flatMap(ce -> template
                            .opsForList()
                            .leftPush("events", ce.getId())
                            .map(l -> ce));


        //calendarEvent -> template.opsForValue(serializationContext).set(EVENT_KEY + ":" + withId.getId(), withId);

               // .subscribe();
        //
        // return Mono.just(event);
    }

    private RedisSerializationContext<String, CalendarEvent> makeSerializationContext() {
        Jackson2JsonRedisSerializer jacksonSerializer = new Jackson2JsonRedisSerializer(CalendarEvent.class);
        return (RedisSerializationContext<String, CalendarEvent>) RedisSerializationContext
                .newSerializationContext(new StringRedisSerializer())
                .value(jacksonSerializer)
                .build();
    }

}