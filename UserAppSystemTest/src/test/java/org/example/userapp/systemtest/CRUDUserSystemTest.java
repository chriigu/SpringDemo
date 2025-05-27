package org.example.userapp.systemtest;

import org.apache.http.HttpStatus;
import org.example.userapp.systemtest.util.UserAppSystemTestConfig;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.example.userapp.systemtest.util.JsonBuilder.o;
import static org.example.userapp.systemtest.util.JsonBuilder.v;

@TestMethodOrder(MethodOrderer.MethodName.class)
class CRUDUserSystemTest {

    private static String uuid;
    private final String email = "CRUDUserSystemTest@abc.ch";
    private final String email2 = "CRUDUserSystemTest2@abc.ch";

    @Test
    void _00userShouldNotBeFound() {
        // given
        UserAppSystemTestConfig.InitSystemTestConfig();
        given()
                .contentType("application/json")
                .queryParam("email", email)
                .queryParam("order", "EMAIL")
                .queryParam("orderDirection", "ASC")
                .queryParam("page", 1)
                .queryParam("limit", 1)
                // when
                .when()
                .get(UserAppSystemTestConfig.USERS_URI)
                // then
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType("application/json")
                .body("resultList", Matchers.notNullValue())
                .body("resultList.size()", Matchers.equalTo(0))
                .body("totalResults", Matchers.equalTo(0))
                .body("totalPages", Matchers.equalTo(0))
                .body("pageNumber", Matchers.equalTo(0))
                .body("pageSize", Matchers.equalTo(1));
    }

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
    void _01createUser400BadRequest(String firstName, String lastName, String email, LocalDate birthdate) {
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
    void _02userShouldNotBeFound() {
        // given
        UserAppSystemTestConfig.InitSystemTestConfig();
        given()
                .contentType("application/json")
                .queryParam("email", email)
                .queryParam("order", "EMAIL")
                .queryParam("orderDirection", "ASC")
                .queryParam("page", 1)
                .queryParam("limit", 1)
                // when
                .when()
                .get(UserAppSystemTestConfig.USERS_URI)
                // then
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType("application/json")
                .body("resultList", Matchers.notNullValue())
                .body("resultList.size()", Matchers.equalTo(0))
                .body("totalResults", Matchers.equalTo(0))
                .body("totalPages", Matchers.equalTo(0))
                .body("pageNumber", Matchers.equalTo(0))
                .body("pageSize", Matchers.equalTo(1));
    }

    @Test
    void _03createUserSuccess() {
        // given
        UserAppSystemTestConfig.InitSystemTestConfig();
        uuid = given()
                .contentType("application/json")
                .body(
                        o(
                                null,
                                v("firstName", "fn"),
                                v("lastName", "ln"),
                                v("email", email),
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
                .body("email", Matchers.equalTo(email))
                .body("firstName", Matchers.equalTo("fn"))
                .body("lastName", Matchers.equalTo("ln"))
                .body("birthdate", Matchers.equalTo(LocalDate.of(1990, 1, 1).format(DateTimeFormatter.ISO_DATE)))
                .extract().body().jsonPath().getString("uuid");

        // See if created user really exists
        given()
                .contentType("application/json")
                .when()
                .get(UserAppSystemTestConfig.USERS_URI + "/{uuid}", uuid)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType("application/json")
                .body("uuid", Matchers.notNullValue())
                .body("uuid", Matchers.hasLength(36))
                .body("uuid", Matchers.equalTo(uuid))
                .body("email", Matchers.equalTo(email))
                .body("firstName", Matchers.equalTo("fn"))
                .body("lastName", Matchers.equalTo("ln"))
                .body("birthdate", Matchers.equalTo(LocalDate.of(1990, 1, 1).format(DateTimeFormatter.ISO_DATE)));
    }

    @Test
    void _04userShouldBeFound() {
        // With usersearch
        // given
        UserAppSystemTestConfig.InitSystemTestConfig();
        given()
                .contentType("application/json")
                .queryParam("email", email)
                .queryParam("order", "EMAIL")
                .queryParam("orderDirection", "ASC")
                .queryParam("page", 1)
                .queryParam("limit", 1)
                // when
                .when()
                .get(UserAppSystemTestConfig.USERS_URI)
                // then
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType("application/json")
                .body("resultList", Matchers.notNullValue())
                .body("resultList.size()", Matchers.equalTo(1))
                .body("resultList[0].uuid", Matchers.notNullValue())
                .body("resultList[0].uuid", Matchers.hasLength(36))
                .body("resultList[0].uuid", Matchers.equalTo(uuid))
                .body("resultList[0].firstName", Matchers.equalTo("fn"))
                .body("resultList[0].lastName", Matchers.equalTo("ln"))
                .body("resultList[0].email", Matchers.equalTo(email))
                .body("resultList[0].birthdate", Matchers.equalTo(LocalDate.of(1990,1,1).format(DateTimeFormatter.ISO_DATE)))
                .body("totalResults", Matchers.equalTo(1))
                .body("totalPages", Matchers.equalTo(1))
                .body("pageNumber", Matchers.equalTo(0))
                .body("pageSize", Matchers.equalTo(1));

        // With getUser
        // given
        UserAppSystemTestConfig.InitSystemTestConfig();
        given()
                .contentType("application/json")
                // when
                .when()
                .get(UserAppSystemTestConfig.USERS_URI + "/{uuid}", uuid)
                // then
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType("application/json")
                .body("uuid", Matchers.notNullValue())
                .body("uuid", Matchers.hasLength(36))
                .body("uuid", Matchers.equalTo(uuid))
                .body("firstName", Matchers.equalTo("fn"))
                .body("lastName", Matchers.equalTo("ln"))
                .body("email", Matchers.equalTo(email))
                .body("birthdate", Matchers.equalTo(LocalDate.of(1990,1,1).format(DateTimeFormatter.ISO_DATE)));
    }

    private static Stream<Arguments> updateUserBadRequestArgs() {
        return Stream.of(
                Arguments.of(null, null, null),
                Arguments.of("fn2", "ln2", null),
                Arguments.of("fn2", null, "em2"),
                Arguments.of("fn2", null, null),
                Arguments.of("fn2", "ln2", "em2abc.ch")
        );
    }

    @ParameterizedTest
    @MethodSource("updateUserBadRequestArgs")
    void _05updateUser400BadRequest(String firstName, String lastName, String email) {
        // given
        UserAppSystemTestConfig.InitSystemTestConfig();
        given()
                .contentType("application/json")
                .body(
                        o(null,
                                v("firstName", firstName),
                                v("lastName", lastName),
                                v("email", email)
                        )
                )
                // when
                .when()
                .put(UserAppSystemTestConfig.USERS_URI + "/{uuid}", uuid)
                // then
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .contentType("application/problem+json")
                .body("type", Matchers.equalTo("about:blank"))
                .body("title", Matchers.equalTo("Bad Request"))
                .body("status", Matchers.equalTo(400))
                .body("detail", Matchers.equalTo("Invalid request content."))
                .body("instance", Matchers.startsWith("/users/"));
    }

    @Test
    void _06oldUserShouldBeFound() {
        // With usersearch
        // given
        UserAppSystemTestConfig.InitSystemTestConfig();
        given()
                .contentType("application/json")
                .queryParam("email", email)
                .queryParam("order", "EMAIL")
                .queryParam("orderDirection", "ASC")
                .queryParam("page", 1)
                .queryParam("limit", 1)
                // when
                .when()
                .get(UserAppSystemTestConfig.USERS_URI)
                // then
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType("application/json")
                .body("resultList", Matchers.notNullValue())
                .body("resultList.size()", Matchers.equalTo(1))
                .body("resultList[0].uuid", Matchers.notNullValue())
                .body("resultList[0].uuid", Matchers.hasLength(36))
                .body("resultList[0].uuid", Matchers.equalTo(uuid))
                .body("resultList[0].firstName", Matchers.equalTo("fn"))
                .body("resultList[0].lastName", Matchers.equalTo("ln"))
                .body("resultList[0].email", Matchers.equalTo(email))
                .body("resultList[0].birthdate", Matchers.equalTo(LocalDate.of(1990,1,1).format(DateTimeFormatter.ISO_DATE)))
                .body("totalResults", Matchers.equalTo(1))
                .body("totalPages", Matchers.equalTo(1))
                .body("pageNumber", Matchers.equalTo(0))
                .body("pageSize", Matchers.equalTo(1));

        // With getUser
        // given
        UserAppSystemTestConfig.InitSystemTestConfig();
        given()
                .contentType("application/json")
                // when
                .when()
                .get(UserAppSystemTestConfig.USERS_URI + "/{uuid}", uuid)
                // then
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType("application/json")
                .body("uuid", Matchers.notNullValue())
                .body("uuid", Matchers.hasLength(36))
                .body("uuid", Matchers.equalTo(uuid))
                .body("firstName", Matchers.equalTo("fn"))
                .body("lastName", Matchers.equalTo("ln"))
                .body("email", Matchers.equalTo(email))
                .body("birthdate", Matchers.equalTo(LocalDate.of(1990,1,1).format(DateTimeFormatter.ISO_DATE)));
    }

    @Test
    void _07newUserShouldNotBeFound404() {
        // given
        UserAppSystemTestConfig.InitSystemTestConfig();
        given()
                .contentType("application/json")
                .queryParam("email", email2)
                .queryParam("order", "EMAIL")
                .queryParam("orderDirection", "ASC")
                .queryParam("page", 1)
                .queryParam("limit", 1)
                // when
                .when()
                .get(UserAppSystemTestConfig.USERS_URI)
                // then
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType("application/json")
                .body("resultList", Matchers.notNullValue())
                .body("resultList.size()", Matchers.equalTo(0))
                .body("totalResults", Matchers.equalTo(0))
                .body("totalPages", Matchers.equalTo(0))
                .body("pageNumber", Matchers.equalTo(0))
                .body("pageSize", Matchers.equalTo(1));
    }

    @Test
    void _08updateUserSuccess() {
        // given
        UserAppSystemTestConfig.InitSystemTestConfig();
        given()
                .contentType("application/json")
                .body(
                        o(
                                null,
                                v("firstName", "fn2"),
                                v("lastName", "ln2"),
                                v("email", email2)
                        )
                )
                // when
                .when()
                .put(UserAppSystemTestConfig.USERS_URI + "/{uuid}", uuid)
                // then
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType("application/json")
                .body("uuid", Matchers.notNullValue())
                .body("uuid", Matchers.hasLength(36)) // UUID length
                .body("uuid", Matchers.equalTo(uuid)) // UUID length
                .body("email", Matchers.equalTo(email2))
                .body("firstName", Matchers.equalTo("fn2"))
                .body("lastName", Matchers.equalTo("ln2"))
                .body("birthdate", Matchers.equalTo(LocalDate.of(1990, 1, 1).format(DateTimeFormatter.ISO_DATE)));

        // See if created user really exists
        given()
                .contentType("application/json")
                .when()
                .get(UserAppSystemTestConfig.USERS_URI + "/{uuid}", uuid)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType("application/json")
                .body("uuid", Matchers.notNullValue())
                .body("uuid", Matchers.hasLength(36))
                .body("uuid", Matchers.equalTo(uuid))
                .body("email", Matchers.equalTo(email2))
                .body("firstName", Matchers.equalTo("fn2"))
                .body("lastName", Matchers.equalTo("ln2"))
                .body("birthdate", Matchers.equalTo(LocalDate.of(1990, 1, 1).format(DateTimeFormatter.ISO_DATE)));
    }

    @Test
    void _09oldUserShouldNotBeFound() {
        // given
        UserAppSystemTestConfig.InitSystemTestConfig();
        given()
                .contentType("application/json")
                .queryParam("email", email)
                .queryParam("order", "EMAIL")
                .queryParam("orderDirection", "ASC")
                .queryParam("page", 1)
                .queryParam("limit", 1)
                // when
                .when()
                .get(UserAppSystemTestConfig.USERS_URI)
                // then
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType("application/json")
                .body("resultList", Matchers.notNullValue())
                .body("resultList.size()", Matchers.equalTo(0))
                .body("totalResults", Matchers.equalTo(0))
                .body("totalPages", Matchers.equalTo(0))
                .body("pageNumber", Matchers.equalTo(0))
                .body("pageSize", Matchers.equalTo(1));
    }

    @Test
    void _10newUserShouldBeFound() {
        // With usersearch
        // given
        UserAppSystemTestConfig.InitSystemTestConfig();
        given()
                .contentType("application/json")
                .queryParam("email", email2)
                .queryParam("order", "EMAIL")
                .queryParam("orderDirection", "ASC")
                .queryParam("page", 1)
                .queryParam("limit", 1)
                // when
                .when()
                .get(UserAppSystemTestConfig.USERS_URI)
                // then
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType("application/json")
                .body("resultList", Matchers.notNullValue())
                .body("resultList.size()", Matchers.equalTo(1))
                .body("resultList[0].uuid", Matchers.notNullValue())
                .body("resultList[0].uuid", Matchers.hasLength(36))
                .body("resultList[0].uuid", Matchers.equalTo(uuid))
                .body("resultList[0].firstName", Matchers.equalTo("fn2"))
                .body("resultList[0].lastName", Matchers.equalTo("ln2"))
                .body("resultList[0].email", Matchers.equalTo(email2))
                .body("resultList[0].birthdate", Matchers.equalTo(LocalDate.of(1990,1,1).format(DateTimeFormatter.ISO_DATE)))
                .body("totalResults", Matchers.equalTo(1))
                .body("totalPages", Matchers.equalTo(1))
                .body("pageNumber", Matchers.equalTo(0))
                .body("pageSize", Matchers.equalTo(1));

        // With getUser
        // given
        UserAppSystemTestConfig.InitSystemTestConfig();
        given()
                .contentType("application/json")
                // when
                .when()
                .get(UserAppSystemTestConfig.USERS_URI + "/{uuid}", uuid)
                // then
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType("application/json")
                .body("uuid", Matchers.notNullValue())
                .body("uuid", Matchers.hasLength(36))
                .body("uuid", Matchers.equalTo(uuid))
                .body("firstName", Matchers.equalTo("fn2"))
                .body("lastName", Matchers.equalTo("ln2"))
                .body("email", Matchers.equalTo(email2))
                .body("birthdate", Matchers.equalTo(LocalDate.of(1990,1,1).format(DateTimeFormatter.ISO_DATE)));
    }

    @Test
    void _11deleteUserBadRequest() {
        // given
        UserAppSystemTestConfig.InitSystemTestConfig();
        given()
                .contentType("application/json")
                // when
                .when()
                .delete(UserAppSystemTestConfig.USERS_URI + "/{uuid}", "123")
                // then
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("type", Matchers.equalTo("about:blank"))
                .body("title", Matchers.equalTo("Bad Request"))
                .body("status", Matchers.equalTo(400))
                .body("detail", Matchers.equalTo("Failed to convert 'uuid' with value: '123'"))
                .body("instance", Matchers.equalTo("/users/123"));
    }

    @Test
    void _12newUserShouldStillBeFound() {
        // With usersearch
        // given
        UserAppSystemTestConfig.InitSystemTestConfig();
        given()
                .contentType("application/json")
                .queryParam("email", email2)
                .queryParam("order", "EMAIL")
                .queryParam("orderDirection", "ASC")
                .queryParam("page", 1)
                .queryParam("limit", 1)
                // when
                .when()
                .get(UserAppSystemTestConfig.USERS_URI)
                // then
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType("application/json")
                .body("resultList", Matchers.notNullValue())
                .body("resultList.size()", Matchers.equalTo(1))
                .body("resultList[0].uuid", Matchers.notNullValue())
                .body("resultList[0].uuid", Matchers.hasLength(36))
                .body("resultList[0].uuid", Matchers.equalTo(uuid))
                .body("resultList[0].firstName", Matchers.equalTo("fn2"))
                .body("resultList[0].lastName", Matchers.equalTo("ln2"))
                .body("resultList[0].email", Matchers.equalTo(email2))
                .body("resultList[0].birthdate", Matchers.equalTo(LocalDate.of(1990,1,1).format(DateTimeFormatter.ISO_DATE)))
                .body("totalResults", Matchers.equalTo(1))
                .body("totalPages", Matchers.equalTo(1))
                .body("pageNumber", Matchers.equalTo(0))
                .body("pageSize", Matchers.equalTo(1));

        // With getUser
        // given
        UserAppSystemTestConfig.InitSystemTestConfig();
        given()
                .contentType("application/json")
                // when
                .when()
                .get(UserAppSystemTestConfig.USERS_URI + "/{uuid}", uuid)
                // then
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType("application/json")
                .body("uuid", Matchers.notNullValue())
                .body("uuid", Matchers.hasLength(36))
                .body("uuid", Matchers.equalTo(uuid))
                .body("firstName", Matchers.equalTo("fn2"))
                .body("lastName", Matchers.equalTo("ln2"))
                .body("email", Matchers.equalTo(email2))
                .body("birthdate", Matchers.equalTo(LocalDate.of(1990,1,1).format(DateTimeFormatter.ISO_DATE)));
    }

    @Test
    void _13deleteUserNotFound() {
        // given
        UserAppSystemTestConfig.InitSystemTestConfig();
        UUID randomUUID = UUID.randomUUID();
        given()
                .contentType("application/json")
                // when
                .when()
                .delete(UserAppSystemTestConfig.USERS_URI + "/{uuid}", randomUUID)
                // then
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .contentType("application/problem+json")
                .body("type", Matchers.equalTo("about:blank"))
                .body("title", Matchers.equalTo("Not found"))
                .body("status", Matchers.equalTo(404))
                .body("detail", Matchers.equalTo("User not found with uuid: ["+ randomUUID + "]"))
                .body("instance", Matchers.equalTo("/users/" + randomUUID));
    }

    @Test
    void _14newUserShouldStillBeFound() {
        // With usersearch
        // given
        UserAppSystemTestConfig.InitSystemTestConfig();
        given()
                .contentType("application/json")
                .queryParam("email", email2)
                .queryParam("order", "EMAIL")
                .queryParam("orderDirection", "ASC")
                .queryParam("page", 1)
                .queryParam("limit", 1)
                // when
                .when()
                .get(UserAppSystemTestConfig.USERS_URI)
                // then
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType("application/json")
                .body("resultList", Matchers.notNullValue())
                .body("resultList.size()", Matchers.equalTo(1))
                .body("resultList[0].uuid", Matchers.notNullValue())
                .body("resultList[0].uuid", Matchers.hasLength(36))
                .body("resultList[0].uuid", Matchers.equalTo(uuid))
                .body("resultList[0].firstName", Matchers.equalTo("fn2"))
                .body("resultList[0].lastName", Matchers.equalTo("ln2"))
                .body("resultList[0].email", Matchers.equalTo(email2))
                .body("resultList[0].birthdate", Matchers.equalTo(LocalDate.of(1990,1,1).format(DateTimeFormatter.ISO_DATE)))
                .body("totalResults", Matchers.equalTo(1))
                .body("totalPages", Matchers.equalTo(1))
                .body("pageNumber", Matchers.equalTo(0))
                .body("pageSize", Matchers.equalTo(1));

        // With getUser
        // given
        UserAppSystemTestConfig.InitSystemTestConfig();
        given()
                .contentType("application/json")
                // when
                .when()
                .get(UserAppSystemTestConfig.USERS_URI + "/{uuid}", uuid)
                // then
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType("application/json")
                .body("uuid", Matchers.notNullValue())
                .body("uuid", Matchers.hasLength(36))
                .body("uuid", Matchers.equalTo(uuid))
                .body("firstName", Matchers.equalTo("fn2"))
                .body("lastName", Matchers.equalTo("ln2"))
                .body("email", Matchers.equalTo(email2))
                .body("birthdate", Matchers.equalTo(LocalDate.of(1990,1,1).format(DateTimeFormatter.ISO_DATE)));
    }

    @Test
    void _15deleteSuccess() {
        // given
        UserAppSystemTestConfig.InitSystemTestConfig();
        given()
                .contentType("application/json")
                // when
                .when()
                .delete(UserAppSystemTestConfig.USERS_URI + "/{uuid}", uuid)
                // then
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    void _16userShouldNotBeFound() {
        // With usersearch

        // given
        UserAppSystemTestConfig.InitSystemTestConfig();
        given()
                .contentType("application/json")
                .queryParam("email", email)
                .queryParam("order", "EMAIL")
                .queryParam("orderDirection", "ASC")
                .queryParam("page", 1)
                .queryParam("limit", 1)
                // when
                .when()
                .get(UserAppSystemTestConfig.USERS_URI)
                // then
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType("application/json")
                .body("resultList", Matchers.notNullValue())
                .body("resultList.size()", Matchers.equalTo(0))
                .body("totalResults", Matchers.equalTo(0))
                .body("totalPages", Matchers.equalTo(0))
                .body("pageNumber", Matchers.equalTo(0))
                .body("pageSize", Matchers.equalTo(1));

        // With getUser
        // given
        UserAppSystemTestConfig.InitSystemTestConfig();
        given()
                .contentType("application/json")
                // when
                .when()
                .get(UserAppSystemTestConfig.USERS_URI + "/{uuid}", uuid)
                // then
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .contentType("application/problem+json")
                .body("type", Matchers.equalTo("about:blank"))
                .body("title", Matchers.equalTo("Not found"))
                .body("status", Matchers.equalTo(404))
                .body("detail", Matchers.equalTo("User not found with uuid: ["+ uuid + "]"))
                .body("instance", Matchers.equalTo("/users/" + uuid));
    }
}
