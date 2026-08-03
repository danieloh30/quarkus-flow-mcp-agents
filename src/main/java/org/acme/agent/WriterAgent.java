package org.acme.agent;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

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
