package br.com.cassiofiuza.tiny_url;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

import java.net.URL;

import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.Test;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class RetrieveUrlResourceTest extends AbstractBaseTinyUrlTest {

    @TestHTTPResource("/")
    URL url;

    @Test
    public void shoudRecoveryOriginalUrl() {
        var id = NanoIdGenerator.generate();

        var tinyUrl = new TinyUrl();
        tinyUrl.id = id;
        tinyUrl.url = ORIGINAL_URL;
        tinyUrl.expirteAt = _2daysFromNow;

        Panache.withTransaction(tinyUrl::persist)
                .await()
                .indefinitely();

        requestOriginalUrl(id);
    }

    private void requestOriginalUrl(String id) {
        var requestUrl = url + id;
        given()
                .when()
                .get(requestUrl)
                .then()
                .contentType(ContentType.HTML)
                .statusCode(Status.OK.getStatusCode())
                .body(containsString("Google"));
    }
}
