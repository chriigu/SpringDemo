package org.example.userapp.systemtest;

import io.restassured.response.ValidatableResponse;
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
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.not;

@TestMethodOrder(MethodOrderer.MethodName.class)
class SearchUserSystemTest {

    private static final String UUID_START = "0195fcfc-c694-7810-9e27-917bb0c3a02";

    @Test
    void allParametersEmptyEqualsNoResultsReturned() {
        // given
        // when
        // then
    }

    private static Stream<Arguments> userSearchArgs() {
        return Stream.of(
                Arguments.of("1fi", null, null, null, 0, 6, "0195fcfc-c694-7810-9e27-917bb0c3a02b", 1, 1, 1),
                Arguments.of("rstN1", null, null, null, 0, 6, "0195fcfc-c694-7810-9e27-917bb0c3a02b", 1, 1, 1),
                Arguments.of("fir", null, null, null, 0, 6, null, 6, 1, 6),
                Arguments.of(null, "1la", null, null, 0, 6, "0195fcfc-c694-7810-9e27-917bb0c3a02b", 1, 1, 1),
                Arguments.of(null, "astN1", null, null, 0, 6, "0195fcfc-c694-7810-9e27-917bb0c3a02b", 1, 1, 1),
                Arguments.of(null, "astN", null, null, 0, 6, null, 6, 1, 6),
                Arguments.of(null, null, "1ab", null, 0, 6, "0195fcfc-c694-7810-9e27-917bb0c3a02b", 1, 1, 1),
                Arguments.of(null, null, "1@ab", null, 0, 6, "0195fcfc-c694-7810-9e27-917bb0c3a02b", 1, 1, 1),
                Arguments.of(null, null, "abc", null, 0, 6, null, 6, 1, 6),
                Arguments.of(null, null, null, "1990-02-01", 0, 6, "0195fcfc-c694-7810-9e27-917bb0c3a02f", 1, 1, 1)
        );
    }

    @ParameterizedTest
    @MethodSource("userSearchArgs")
    void shouldSearchUsers(String firstName, String lastName, String email, LocalDate birthdate,
                           int page, int limit,
                           String expectedUuid, int expectedTotalResults, int expectedTotalPages, int expectedResultListSize) {
        // given
        UserAppSystemTestConfig.InitSystemTestConfig();
        given()
                .contentType("application/json")
                .queryParam("firstName", firstName)
                .queryParam("lastName", lastName)
                .queryParam("email", email)
                .queryParam("birthdate", birthdate != null ? birthdate.format(DateTimeFormatter.ISO_DATE) : null)
                .queryParam("order", "FIRST_NAME")
                .queryParam("orderDirection", "ASC")
                .queryParam("page", page)
                .queryParam("limit", limit)
                // when
                .when()
                .get(UserAppSystemTestConfig.USERS_URI)
                // then
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType("application/json")
                .body("resultList", Matchers.notNullValue())
                .body("resultList.size()", Matchers.equalTo(expectedResultListSize))
                .body("resultList.uuid", everyItem(Matchers.notNullValue()))
                .body("resultList.uuid", everyItem(Matchers.hasLength(36)))
                .body("resultList.uuid", everyItem(expectedUuid != null ? Matchers.equalTo(expectedUuid) : Matchers.notNullValue()))
                .body("resultList.firstName", everyItem(firstName != null ? Matchers.containsString(firstName) : not(Matchers.blankOrNullString())))
                .body("resultList.lastName", everyItem(lastName != null ? Matchers.containsString(lastName) : not(Matchers.blankOrNullString())))
                .body("resultList.email", everyItem(email != null ? Matchers.containsString(email) : not(Matchers.blankOrNullString())))
                .body("resultList.birthdate", everyItem(birthdate != null ? Matchers.containsString(birthdate.format(DateTimeFormatter.ISO_DATE)) : not(Matchers.blankOrNullString())))
                .body("totalResults", Matchers.equalTo(expectedTotalResults))
                .body("totalPages", Matchers.equalTo(expectedTotalPages))
                .body("pageNumber", Matchers.equalTo(page))
                .body("pageSize", Matchers.equalTo(limit));
    }

    private static Stream<Arguments> userSearchPagingAndSortingArgs() {
        return Stream.of(
                Arguments.of(0, 6, 6, 1, 6, "a", "b", "c", "d", "e", "f", "FIRST_NAME", "ASC"),
                Arguments.of(0, 2, 6, 3, 2, "a", "b", null,null,null,null,"FIRST_NAME", "ASC"),
                Arguments.of(1, 2, 6, 3, 2, "c", "d", null,null,null,null,"FIRST_NAME", "ASC"),
                Arguments.of(2, 2, 6, 3, 2, "e", "f", null,null,null,null,"FIRST_NAME", "ASC"),
                Arguments.of(0, 6, 6, 1, 6, "f", "e", "d", "c", "b", "a", "FIRST_NAME", "DESC"),
                Arguments.of(0, 2, 6, 3, 2, "f", "e", null,null,null,null,"FIRST_NAME", "DESC"),
                Arguments.of(1, 2, 6, 3, 2, "d", "c", null,null,null,null,"FIRST_NAME", "DESC"),
                Arguments.of(2, 2, 6, 3, 2, "b", "a", null,null,null,null,"FIRST_NAME", "DESC")
        );
    }

    @ParameterizedTest
    @MethodSource("userSearchPagingAndSortingArgs")
    void pagingAndSortingShouldWork(int page,
                                    int limit,
                                    int expectedTotalResults,
                                    int expectedTotalPages,
                                    int expectedResultListSize,
                                    String firstUUIDSuff,
                                    String secondUUIDSuff,
                                    String thirdUUIDSuff,
                                    String fourthUUIDSuff,
                                    String fifthUUIDSuff,
                                    String sixthUUIDSuff,
                                    String order,
                                    String orderDirection

    ) {
        // given
        String firstUUID = firstUUIDSuff != null ? UUID_START + firstUUIDSuff : null;
        String secondUUID = secondUUIDSuff != null ? UUID_START + secondUUIDSuff : null;
        String thirdUUID = thirdUUIDSuff != null ? UUID_START + thirdUUIDSuff : null;
        String fourthUUID = fourthUUIDSuff != null ? UUID_START + fourthUUIDSuff : null;
        String fifthUUID = fifthUUIDSuff != null ? UUID_START + fifthUUIDSuff : null;
        String sixthUUID = sixthUUIDSuff != null ? UUID_START + sixthUUIDSuff : null;


        UserAppSystemTestConfig.InitSystemTestConfig();
        ValidatableResponse response = given()
                .contentType("application/json")
                .queryParam("firstName", "fir")
                .queryParam("page", page)
                .queryParam("limit", limit)
                .queryParam("order", order)
                .queryParam("orderDirection", orderDirection)
                // when
                .when()
                .get(UserAppSystemTestConfig.USERS_URI)
                // then
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType("application/json")
                .body("resultList", Matchers.notNullValue())
                .body("resultList.size()", Matchers.equalTo(expectedResultListSize))
                .body("totalResults", Matchers.equalTo(expectedTotalResults))
                .body("totalPages", Matchers.equalTo(expectedTotalPages))
                .body("pageNumber", Matchers.equalTo(page))
                .body("pageSize", Matchers.equalTo(limit));

        if(firstUUID != null) {
            assertUuid(firstUUID, response, 0);
        }
        if(secondUUID != null) {
            assertUuid(secondUUID, response, 1);
        }
        if(thirdUUID != null) {
            assertUuid(thirdUUID, response, 2);
        }
        if(fourthUUID != null) {
            assertUuid(fourthUUID, response, 3);
        }
        if(fifthUUID != null) {
            assertUuid(fifthUUID, response, 4);
        }
        if(sixthUUID != null) {
            assertUuid(sixthUUID, response, 5);
        }

    }

    private void assertUuid(String expectedUuid, ValidatableResponse response, int entryNumber)
    {
        response.body("resultList[" + entryNumber + "].uuid", Matchers.notNullValue())
                .body("resultList[" + entryNumber + "].uuid", Matchers.hasLength(36))
                .body("resultList[" + entryNumber + "].uuid", Matchers.equalTo(expectedUuid));
    }
}
