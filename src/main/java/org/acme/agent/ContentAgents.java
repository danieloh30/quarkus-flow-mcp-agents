package org.acme.agent;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.declarative.ExitCondition;
import dev.langchain4j.agentic.declarative.LoopAgent;
import dev.langchain4j.agentic.declarative.Output;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public class ContentAgents {

    public interface WriterAgent {

        @Agent(outputKey = "draft",
                description = "Drafts or revises a technical article based on the topic")
        @SystemMessage("""
                You are an expert Java and Quarkus developer.
                Write concise, technically accurate blog drafts.
                Never generate raw shell commands or suggest unsafe practices.
                If the reviewer has given you feedback in a previous turn, revise the draft to address it.
                """)
        @UserMessage("Write a short technical blog post about: {topic}")
        String writeDraft(@V("topic") String topic);
    }

    public interface CriticAgent {

        @Agent(outputKey = "review",
                description = "Reviews the draft for technical accuracy and clarity")
        @SystemMessage("""
                You are a strict editor checking for technical accuracy and clarity.
                If the draft is acceptable, your response MUST start with "APPROVED:" followed by a brief note.
                If the draft needs improvement, provide constructive feedback.
                """)
        @UserMessage("""
                Review this draft:

                {draft}
                """)
        String reviewDraft(@V("draft") String draft);
    }

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
}
