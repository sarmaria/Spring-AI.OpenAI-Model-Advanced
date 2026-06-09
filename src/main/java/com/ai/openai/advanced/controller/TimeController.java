package com.ai.openai.advanced.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
@RequestMapping("/api")
public class TimeController {

    private final ChatClient chatClient;

    public TimeController(@Qualifier("timeChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/ai/time/chat")
    public ResponseEntity<String> timeChat(@RequestHeader("username") String username,
                                           @RequestParam("msg") String message) {
        String response = chatClient.prompt()
                .advisors(advisorSpec ->
                        advisorSpec.param(CONVERSATION_ID, username))
                .user(message)
                .call()
                .content();
        return ResponseEntity.ok(response);
    }
}
