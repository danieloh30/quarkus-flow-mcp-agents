package org.acme.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.acme.agent.ArticlePublisher;

import java.util.Map;

@Path("/api/articles")
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
