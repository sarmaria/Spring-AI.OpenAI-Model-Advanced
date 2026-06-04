package com.ai.openai.advanced.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatClient chatClient;

    private final String promptTemplate = """
            A customer named {customerName} sent the following message:
            "{customerMessage}"
            Write a polite and helpful email response addressing the issue.
            Maintain a professional tone and provide reassurance.
            
            Respond as if you're writing the email body only. 
            Don't include subject, signature.
            """;

    public ChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/ai/chat")
    public String chat(@RequestParam("msg") String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
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
