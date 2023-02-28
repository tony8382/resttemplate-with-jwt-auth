package com.lyyang.test.web.service;

import com.lyyang.test.web.model.User;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Slf4j
@Service
public class JwtConsumer {
    private RestTemplate restTemplate;
    private static final String AUTHENTICATION_URL = "/auth/authenticate";
    private static final String HELLO_URL = "/user";
    private String token;

    private final TokenValidator tokenValidator;

    public JwtConsumer(RestTemplateBuilder builder, TokenValidator tokenValidator) {
        this.restTemplate = builder
                .rootUri("http://localhost:8080")
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(120))
                .additionalInterceptors(((request, body, execution) -> {
                    if (!AUTHENTICATION_URL.equals(request.getURI().getPath())) {
                        HttpHeaders headers = request.getHeaders();
                        if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                            headers.setBearerAuth(getAutoToken());
                        }
                    }
                    return execution.execute(request, body);
                })).build();
        this.tokenValidator = tokenValidator;
    }

    String getAutoToken() {

        try {
            if (StringUtils.isBlank(token) || !tokenValidator.isTokenValid(token)) {
                User user = new User();
                user.setUsername("user");
                user.setPassword("123456");
                this.token = restTemplate.postForObject(AUTHENTICATION_URL, user, String.class);
            }
        } catch (Exception e) {
            log.warn("decode error:{}", e.getMessage());
        }

        return this.token;

    }

    public void hello() {
        String response = restTemplate.getForObject(HELLO_URL, String.class);
        log.info("RR:{}", response);

    }

}
