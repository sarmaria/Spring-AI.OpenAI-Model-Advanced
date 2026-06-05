package com.ai.openai.advanced.config;

import com.ai.openai.advanced.advisor.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
        var chatOptionsBuilder = OpenAiChatOptions.builder().model("gpt-5-nano").maxCompletionTokens(600);
        return chatClientBuilder
                .defaultOptions(chatOptionsBuilder)
                .defaultAdvisors(new SimpleLoggerAdvisor(), new TokenUsageAuditAdvisor())
                .defaultSystem("""
                        You are helpful HR assistant who can answer queries on HR policies
                        like leave policy, benefits, employment contracts. 
                        If there are any other questions asked, 
                        politely respond that its out of your scope.
                        """)
                .defaultUser("How can you help me?").build();
    }
}
