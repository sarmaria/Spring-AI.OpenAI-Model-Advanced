package com.ai.openai.advanced.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PromptStuffingController {

    private final ChatClient chatClient;

    @Value("classpath:promptTemplates/systemPromptTemplate.st")
    private Resource promptTemplate;

    public PromptStuffingController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }
    @GetMapping("/ai/hr")
    public String hrResponseGenerator(@RequestParam("customerMessage") String customerMessage) {
        return chatClient.prompt()
                .system(promptTemplate)
                .user(customerMessage)
                .call()
                .content();
    }
}
