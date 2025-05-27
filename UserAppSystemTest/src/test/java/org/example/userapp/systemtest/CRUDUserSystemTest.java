package org.example.userapp.systemtest;

import org.apache.http.HttpStatus;
import org.example.userapp.systemtest.util.UserAppSystemTestConfig;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.example.userapp.systemtest.util.JsonBuilder.o;
import static org.example.userapp.systemtest.util.JsonBuilder.v;

class CRUDUserSystemTest {


    private static Stream<Arguments> createUserBadRequestArgs() {
        return Stream.of(
                Arguments.of(null, null, null, null),
                Arguments.of("fn", "ln", null, null),
                Arguments.of("fn", null, "em", null),
                Arguments.of("fn", null, null, LocalDate.of(1990, 1, 1)),
                Arguments.of("fn", "ln", "emabc.ch", LocalDate.of(1990, 1, 1))
        );
    }

    @ParameterizedTest
    @MethodSource("createUserBadRequestArgs")
    void createUser400BadRequest(String firstName, String lastName, String email, LocalDate birthdate) {
        // given
        UserAppSystemTestConfig.InitSystemTestConfig();
        given()
                .contentType("application/json")
                .body(
                        o(null,
                                v("firstName", firstName),
                                v("lastName", lastName),
                                v("email", email),
                                v("birthdate", birthdate)
                        )
                )
                // when
                .when()
                .post(UserAppSystemTestConfig.USERS_URI)
                // then
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .contentType("application/problem+json")
                .body("type", Matchers.equalTo("about:blank"))
                .body("title", Matchers.equalTo("Bad Request"))
                .body("status", Matchers.equalTo(400))
                .body("detail", Matchers.equalTo("Invalid request content."))
                .body("instance", Matchers.equalTo("/users"));
    }

    @Test
    void createUserSuccess() {
        // given
        UserAppSystemTestConfig.InitSystemTestConfig();
        given()
                .contentType("application/json")
                .body(
                        o(
                                null,
                                v("firstName", "fn"),
                                v("lastName", "ln"),
                                v("email", "em@abc.ch"),
                                v("birthdate", LocalDate.of(1990, 1, 1)))
                )
                // when
                .when()
                .post(UserAppSystemTestConfig.USERS_URI)
                // then
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType("application/json")
                .body("uuid", Matchers.notNullValue())
                .body("uuid", Matchers.hasLength(36)) // UUID length
                .body("email", Matchers.equalTo("em@abc.ch"))
                .body("firstName", Matchers.equalTo("fn"))
                .body("lastName", Matchers.equalTo("ln"))
                .body("birthdate", Matchers.equalTo(LocalDate.of(1990, 1, 1).format(DateTimeFormatter.ISO_DATE)));
    }
}
