package org.acme.api;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import org.acme.agent.ContentAgents.ArticlePublisher;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@QuarkusTest
class ArticleResourceTest {

    @InjectMock
    ArticlePublisher articlePublisher;

    @Test
    void generateArticle() {
        when(articlePublisher.publishArticle("Quarkus Flow"))
                .thenReturn("A great article about Quarkus Flow.");

        given()
                .contentType("application/json")
                .body("{\"topic\": \"Quarkus Flow\"}")
                .when()
                .post("/api/articles/generate")
                .then()
                .statusCode(200)
                .body("article", is("A great article about Quarkus Flow."));
    }

    @Test
    void generateArticleWithEmptyTopic() {
        when(articlePublisher.publishArticle(""))
                .thenReturn("A generic technical article.");

        given()
                .contentType("application/json")
                .body("{\"topic\": \"\"}")
                .when()
                .post("/api/articles/generate")
                .then()
                .statusCode(200)
                .body("article", is("A generic technical article."));
    }
}
