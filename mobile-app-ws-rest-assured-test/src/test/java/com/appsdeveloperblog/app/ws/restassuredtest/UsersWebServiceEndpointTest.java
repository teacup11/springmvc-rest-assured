package com.appsdeveloperblog.app.ws.restassuredtest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UsersWebServiceEndpointTest {
    // this class is because we cannot mock with integration tests

    private final String CONTEXT_PATH = "/mobile-app-ws";
    private final String EMAIL_ADDRESS = "ziva.groza@mail.com";

    @BeforeEach
    void setUp() throws Exception {
        // set up the base uri and the port number of my app
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    final void testUserLogin() {

        Map<String, String> loginDetails = new HashMap<>();
        loginDetails.put("email", EMAIL_ADDRESS);
        loginDetails.put("password", "123");

        // http request
        Response response = given().contentType("application/json")
                .accept("application/json")
                .body(loginDetails)
                .when().post(CONTEXT_PATH + "/users/login")
                .then().statusCode(200)
                .extract().response();

        String authorizationHeader = response.header("Authorization"); // if this header is there and not null, user login was successful
        String userId = response.header("UserID");

        //assert that values not null
        assertNotNull(authorizationHeader);
        assertNotNull(userId);

    }
}

