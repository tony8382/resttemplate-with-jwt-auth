package com.lyyang.test.web.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtConsumerTest {

    @Autowired
    private JwtConsumer jwtConsumer;

    @Test
    void hello() {
        jwtConsumer.hello();
        jwtConsumer.hello();
        jwtConsumer.hello();
        jwtConsumer.hello();
    }

    @Test
    void getAutoToken() {
        jwtConsumer.getAutoToken();
    }
}
