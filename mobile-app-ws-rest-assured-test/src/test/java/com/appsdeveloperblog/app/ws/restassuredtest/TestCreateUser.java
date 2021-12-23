package com.appsdeveloperblog.app.ws.restassuredtest;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class TestCreateUser {

    private final String CONTEXT_PATH = "/mobile-app-ws";

    @BeforeEach
    void setUp() throws Exception {
        // this method is called first
        // use it to set up the base uri and the port number of my app, application context path
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    final void testCreateUser() {
        // test create user api call
    }

}
