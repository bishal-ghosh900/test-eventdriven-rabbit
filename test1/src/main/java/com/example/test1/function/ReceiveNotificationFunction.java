package com.example.test1.function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class ReceiveNotificationFunction {

    private final Logger log = LoggerFactory.getLogger(ReceiveNotificationFunction.class);

    @Bean
    public Consumer<String> receivedGenre() {
        return genre -> log.info("received genre from the message microservice: " + genre);
    }

}
