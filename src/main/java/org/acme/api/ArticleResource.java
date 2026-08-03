package org.acme.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.agent.ContentAgents.ArticlePublisher;

import java.util.Map;

@Path("/api/articles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArticleResource {

    @Inject
    ArticlePublisher articlePublisher;

    @POST
    @Path("/generate")
    public Map<String, Object> generateArticle(ArticleRequest request) {
        String result = articlePublisher.publishArticle(request.topic());
        return Map.of("article", result);
    }

    public record ArticleRequest(String topic) {}
}
