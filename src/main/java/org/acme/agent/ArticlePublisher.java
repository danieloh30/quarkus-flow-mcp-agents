package org.acme.agent;

import dev.langchain4j.agentic.declarative.ExitCondition;
import dev.langchain4j.agentic.declarative.LoopAgent;
import dev.langchain4j.agentic.declarative.Output;
import dev.langchain4j.service.V;

public interface ArticlePublisher {

    @LoopAgent(
            subAgents = { WriterAgent.class, CriticAgent.class },
            maxIterations = 3)
    String publishArticle(@V("topic") String topic);

    @ExitCondition(testExitAtLoopEnd = true,
            description = "Exit when the critic approves the draft")
    static boolean isApproved(@V("review") String review) {
        return review != null && review.toUpperCase().startsWith("APPROVED");
    }

    @Output
    static String extractArticle(String draft) {
        return draft;
    }
}
