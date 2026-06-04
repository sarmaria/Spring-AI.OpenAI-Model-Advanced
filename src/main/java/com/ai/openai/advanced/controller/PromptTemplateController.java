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
public class PromptTemplateController {

    private final ChatClient chatClient;

    @Value("classpath:promptTemplates/userPromptTemplate.st")
    private Resource promptTemplate;

    public PromptTemplateController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }
    @GetMapping("/ai/email")
    public String customerResponseGenerator(@RequestParam("customerName") String customerName,
                                            @RequestParam("customerMessage") String customerMessage) {
        return chatClient.prompt()
                .system("""
                        You are an customer support assistant who helps in drafting
                                     the mail for support response to improve the productivity.
                        """)
                .user(promptUserSpec ->
                        promptUserSpec.text(promptTemplate)
                                .param("customerName", customerName)
                                .param("customerMessage", customerMessage))
                .call()
                .content();
    }
}
