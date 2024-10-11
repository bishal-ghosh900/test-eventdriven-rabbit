package com.example.message.function;

import com.example.message.model.Anime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class NotifyFunction {

    private final Logger log = LoggerFactory.getLogger(NotifyFunction.class);

    @Bean
    public Function<Anime, Anime> anime() {
        return anime -> {
            log.info("sending the message with the anime name: " + anime.animeName());
            return anime;
        };
    }

    @Bean
    public Function<Anime, String> genre() {
        return anime -> {
            log.info("sending the message with the anime genre: " + anime.genre());
            return anime.genre();
        };
    }

}
