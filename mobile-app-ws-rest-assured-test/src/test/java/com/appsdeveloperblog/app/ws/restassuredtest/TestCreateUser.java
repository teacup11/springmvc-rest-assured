package com.appsdeveloperblog.app.ws.restassuredtest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;


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
        // test: create user api call

        //body
        List<Map<String, Object>> userAddresses = new ArrayList<>();

        Map<String, Object> shippingAddress = new HashMap<>();
        shippingAddress.put("city","Ljubljana");
        shippingAddress.put("country", "Slovenia");
        shippingAddress.put("streetName", "123 Ulica");
        shippingAddress.put("postalCode", "1000");
        shippingAddress.put("type", "shipping");

        Map<String, Object> billingAddress = new HashMap<>();
        billingAddress.put("city", "Celje");
        billingAddress.put("country", "Slovenia");
        billingAddress.put("streetName", "1234 Ulica");
        billingAddress.put("postalCode", "3000");
        billingAddress.put("type", "billing");

        userAddresses.add(shippingAddress);
        userAddresses.add(billingAddress);

        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("firstName", "Živa");
        userDetails.put("lastName", "Groza");
        userDetails.put("email", "ziva.groza@mail2.com");
        userDetails.put("password", "123");
        userDetails.put("addresses", userAddresses);

        // prepare http request
        Response response = given()
                .contentType("application/json")
                .accept("application/json")
                .body(userDetails)
                .when().post(CONTEXT_PATH + "/users")
                .then().statusCode(200)
                .contentType("application/json")
                .extract()
                .response();

        // return string value for json key user id and validate user id not null
        String userId = response.jsonPath().getString("userId");
        assertNotNull(userId);
        assertTrue(userId.length() == 30);

        //validate JSON response
        String bodyString = response.body().asString(); // get json body as string
        try {
            JSONObject responseBodyJson = new JSONObject(bodyString); // convert it to JSON
            JSONArray addresses = responseBodyJson.getJSONArray("addresses"); //get JSON array addresses
            //validate
            assertNotNull(addresses);
            assertTrue(addresses.length() == 2);

            //get specific element
            String addressId = addresses.getJSONObject(0).getString("addressId");
            //validate
            assertNotNull(addressId);
            assertTrue(addressId.length() == 30);
        } catch (JSONException e) {
            fail(e.getMessage());
        }

    }

}
