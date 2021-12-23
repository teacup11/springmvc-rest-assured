package com.appsdeveloperblog.app.ws.restassuredtest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.Alphanumeric.class) // tests run in a fixed order
class UsersWebServiceEndpointTest {
    // this class is because we cannot mock with integration tests

    private final String CONTEXT_PATH = "/mobile-app-ws";
    private final String EMAIL_ADDRESS = "ziva.groza@mail.com";
    private final String JSON = "application/json";
    private static String authorizationHeader;
    private static String userId;

    @BeforeEach
    void setUp() throws Exception {
        // set up the base uri and the port number of my app
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    final void A_testUserLogin() {

        Map<String, String> loginDetails = new HashMap<>();
        loginDetails.put("email", EMAIL_ADDRESS);
        loginDetails.put("password", "123");

        // http request
        Response response = given().contentType(JSON)
                .accept(JSON)
                .body(loginDetails)
                .when().post(CONTEXT_PATH + "/users/login")
                .then().statusCode(200)
                .extract().response();

        authorizationHeader = response.header("Authorization"); // if this header is there and not null, user login was successful
        userId = response.header("UserID");

        //assert that values not null
        assertNotNull(authorizationHeader);
        assertNotNull(userId);

    }

    @Test
    final void B_testGetUserDetails() {

        Response response = given()
                .header("Authorization", authorizationHeader)
                .accept(JSON)
                .when().get(CONTEXT_PATH + "/users/" + userId)
                .then().statusCode(200)
                .contentType(JSON)
                .extract().response();

        // assert that response contains user id and user email address
        String userPublicId = response.jsonPath().getString("userId");
        String userEmail = response.jsonPath().getString("email");
        // assert that these values are not null
        assertNotNull(userPublicId);
        assertNotNull(userEmail);
    }
}

