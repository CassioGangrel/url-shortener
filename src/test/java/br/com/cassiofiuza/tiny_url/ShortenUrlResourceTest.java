package br.com.cassiofiuza.tiny_url;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.enterprise.context.control.ActivateRequestContext;
import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@ActivateRequestContext
public class ShortenUrlResourceTest extends AbstractBaseTinyUrlTest {

    private static final String ORIGINAL_URL = "https://www.google.com";
    private LocalDateTime _2daysFromNow;

    @TestHTTPResource("/shorten-url")
    URL url;

    @BeforeEach
    void beforeEach() {
        _2daysFromNow = LocalDateTime.now().plus(2, ChronoUnit.DAYS);
    }

    @Test
    public void shouldReturnCorrectShortedUrl() throws MalformedURLException {
        var response = shortenUrl(ORIGINAL_URL);

        var expectedHost = url.getHost();
        var actualHost = new URL(response.url()).getHost();

        assertEquals(expectedHost, actualHost);

        assertTrue(response.expirteAt().isAfter(_2daysFromNow));
    }

    private ShortenUrlResponse shortenUrl(String originalUrl) {
        var response = given()
                .when()
                .body(new ShortenUrlRequest(originalUrl))
                .contentType(ContentType.JSON)
                .post(url)
                .then()
                .statusCode(Status.CREATED.getStatusCode())
                .body("url", is(not(originalUrl)))
                .extract()
                .as(ShortenUrlResponse.class);
        return response;
    }

}
