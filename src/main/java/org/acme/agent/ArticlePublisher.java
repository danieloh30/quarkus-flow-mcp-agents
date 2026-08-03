package org.acme.agent;

import dev.langchain4j.agentic.declarative.ExitCondition;
import dev.langchain4j.agentic.declarative.LoopAgent;
import dev.langchain4j.agentic.declarative.Output;
public interface ArticlePublisher {

    @LoopAgent(
            subAgents = { WriterAgent.class, CriticAgent.class },
            maxIterations = 3)
    String publishArticle(String topic);

    @ExitCondition(testExitAtLoopEnd = true,
            description = "Exit when the critic approves the draft")
    static boolean isApproved(String review) {
        return review != null && review.toUpperCase().startsWith("APPROVED");
    }

    @Output
    static String extractArticle(String draft) {
        return draft;
    }
}
