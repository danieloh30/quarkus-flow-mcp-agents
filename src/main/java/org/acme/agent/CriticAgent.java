package org.acme.agent;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

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
