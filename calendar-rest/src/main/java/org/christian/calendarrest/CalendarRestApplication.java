package org.christian.calendarrest;

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

@SpringBootApplication
public class CalendarRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalendarRestApplication.class, args);
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