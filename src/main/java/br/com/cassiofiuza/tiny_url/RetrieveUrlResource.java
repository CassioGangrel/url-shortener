package br.com.cassiofiuza.tiny_url;

import java.net.URI;
import java.net.URISyntaxException;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import io.smallrye.mutiny.Uni;

@ApplicationScoped
@Path("/")
public class RetrieveUrlResource {

    @GET
    @Path("{id}")
    public Uni<Response> recoveryOriginalUrl(@PathParam("id") String id) {
        return TinyUrl.<TinyUrl>findById(id)
                .onItem()
                .transform(tinyUrl -> {
                    if (tinyUrl == null) {
                        return Response.status(404).build();
                    }

                    URI uri;
                    try {
                        uri = new URI(tinyUrl.url);
                    } catch (URISyntaxException e) {
                        return Response.status(500).build();
                    }

                    return Response.status(301)
                            .location(uri)
                            .build();
                });

    }
}
