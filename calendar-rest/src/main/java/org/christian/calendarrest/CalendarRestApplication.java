package org.christian.calendarrest;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.ReactiveNumberCommands;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class CalendarRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalendarRestApplication.class, args);
	}

	@Bean
    ApplicationRunner populateDb(CalendarRepository calendarRepository) {
	    return args -> {
            Flux.just(
                    CalendarEvent.builder()
                        .withTitle("Heldagshändelse")
                        .withStart("2017-09-01")
                        .withId("1001")
                        .build(),
                    CalendarEvent.builder()
                        .withTitle("Flerdagshändelse")
                        .withStart("2017-09-07")
                        .withEnd("2017-09-10")
                        .withId("1002")
                        .build(),
                    CalendarEvent.builder()
                        .withTitle("Utvecklarkonferens")
                        .withStart("2017-09-11")
                        .withEnd("2017-09-11")
                        .withId("1003")
                        .build()
                    )
                    .flatMap(calendarRepository::addEvent)
                    .subscribe(calendarEvent -> System.out.println(calendarEvent.getTitle()));
	        //calendarRepository.addEvent()
        };
    }

	@Bean
    public ReactiveRedisConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory("localhost", 6379);
    }

    @Bean
    ReactiveRedisTemplate<String, String> reactiveRedisTemplate(ReactiveRedisConnectionFactory connectionFactory) {
        Jackson2JsonRedisSerializer jacksonSerializer = new Jackson2JsonRedisSerializer(CalendarEvent.class);
        /* RedisSerializationContext <String, CalendarEvent> serializationContext = RedisSerializationContext
                .newSerializationContext(new StringRedisSerializer())
                .value(jacksonSerializer)
                .build();
                */
        return new ReactiveRedisTemplate<String, String>(connectionFactory, RedisSerializationContext.string());
    }

    @Bean
    ReactiveNumberCommands reactiveNumberCommands(ReactiveRedisConnectionFactory connectionFactory) {
	    return connectionFactory.getReactiveConnection().numberCommands();
    }

    @Bean
    CalendarRepository calendarRepository(ReactiveRedisTemplate<String, String> template, ReactiveNumberCommands reactiveNumberCommands) {
	    return new RedisCalendarRepository(template, reactiveNumberCommands);
    }

}