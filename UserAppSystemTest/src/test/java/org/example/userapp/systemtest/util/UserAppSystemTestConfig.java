package org.example.userapp.systemtest.util;

import io.restassured.RestAssured;

public class UserAppSystemTestConfig {

    public static final String BASE_URI = "http://localhost";
    public static final int PORT = 8081;
    public static final String USERS_URI = BASE_URI + "/users";


    public static void InitSystemTestConfig() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = PORT;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.basePath = "/users";
    }
}
