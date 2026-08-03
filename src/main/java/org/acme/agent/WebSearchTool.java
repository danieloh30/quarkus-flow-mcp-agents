package org.acme.agent;

import java.util.List;
import java.util.Map;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.agent.tool.ToolExecutionRequest;
import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.stdio.StdioMcpTransport;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WebSearchTool {

    private final McpClient mcpClient;

    WebSearchTool(@ConfigProperty(name = "brave.api.key", defaultValue = "${BRAVE_API_KEY:}") String braveApiKey) {
        mcpClient = new DefaultMcpClient.Builder()
                .transport(new StdioMcpTransport.Builder()
                        .command(List.of("npx", "-y", "@brave/brave-search-mcp-server"))
                        .environment(Map.of("BRAVE_API_KEY", braveApiKey))
                        .logEvents(true)
                        .build())
                .build();
    }

    @Tool("Search the web for up-to-date information about a given query using Brave Search")
    public String webSearch(String query) {
        var request = ToolExecutionRequest.builder()
                .name("brave_web_search")
                .arguments("{\"query\": \"" + query + "\"}")
                .build();
        return mcpClient.executeTool(request).resultText();
    }

    @PreDestroy
    void close() {
        try {
            mcpClient.close();
        } catch (Exception ignored) {
        }
    }
}
