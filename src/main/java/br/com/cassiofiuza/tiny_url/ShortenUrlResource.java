package br.com.cassiofiuza.tiny_url;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;

@Path("/shorten-url")
@RequestScoped
public class ShortenUrlResource {

    @Context
    UriInfo uriInfo;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> shortenUrl(ShortenUrlRequest url) {
        var baseUrl = this.uriInfo.getBaseUri().toString();

        var id = NanoIdGenerator.generate();
        var expireAt = LocalDateTime.now().plus(2, ChronoUnit.DAYS);
        var shortenedUrl = baseUrl + id;

        TinyUrl tinyUrl = new TinyUrl();
        tinyUrl.id = id;
        tinyUrl.url = url.url();
        tinyUrl.expirteAt = expireAt;

        return Panache.<TinyUrl>withTransaction(tinyUrl::persist)
                .onItem()
                .transform(mapper -> Response.status(201)
                        .entity(new ShortenUrlResponse(shortenedUrl, expireAt))
                        .build());
    }

}

record ShortenUrlRequest(String url) {
}

record ShortenUrlResponse(
        String url,
        @JsonSerialize(using = LocalDateTimeSerializer.class) 
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        LocalDateTime expirteAt) {
}
