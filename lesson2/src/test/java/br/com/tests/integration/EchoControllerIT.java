package br.com.tests.integration;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;

public class EchoControllerIT {

    @Test
    public final void whenCallEchoWithMessageThenReturnSameMessage() {
        get("/echo/hello")
                .then()
                .assertThat()
                    .statusCode(equalTo(HttpStatus.OK.value()))
                    .body("message", equalTo("hello"));
    }

}
