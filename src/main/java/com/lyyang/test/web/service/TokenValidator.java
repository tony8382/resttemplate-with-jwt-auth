package com.lyyang.test.web.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenValidator {

    public boolean isTokenValid(String token) throws Exception {
        DecodedJWT jwt = JWT.decode(token);

        if (jwt.getExpiresAt().before(DateUtils.addMinutes(new Date(), 1))) {
            return false;
        }
        return true;
    }
}