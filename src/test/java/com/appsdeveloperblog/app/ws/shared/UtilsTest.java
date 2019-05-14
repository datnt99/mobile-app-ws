package com.appsdeveloperblog.app.ws.shared;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UtilsTest {

    @Autowired
    Utils utils;

    @BeforeEach
    void setUp() {
    }

    @Test
    void generateUserId() {
        String userId = utils.generateUserId(30);
        String userId2 = utils.generateUserId(30);

        assertNotNull(userId);
        assertNotNull(userId2);

        assertTrue(userId.length() == 30);
        assertTrue(!userId.equalsIgnoreCase(userId2));
    }

    @Test
    void hasTokenNotExpired() {
        String userId = utils.generateUserId(30);
        assertNotNull(userId);

        String token = utils.generateEmailVerificationToken(userId);
        assertNotNull(token);

        boolean hasTokenExpired = Utils.hasTokenExpired(token);
        assertFalse(hasTokenExpired);
    }

    @Test
    void hasTokenExpired(){
        String token =  "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJQRFZFNWJFYThVN3M1VFpmcjJjamNGaGhrV0FkdUciLCJleHAiOjE1NTAyMjUxOTh9." +
                "_kw9gbyHv7GmMeIfrwkMALVN9HxzyPmEX5E5Knnjq0pGvl1S_85MLAblI5LEaZY71ghb1hf2YXIHFQVIuuGiWg";
        assertNotNull(token);

        boolean hasTokenExpired = Utils.hasTokenExpired(token);
        assertTrue(hasTokenExpired);

    }
}