package com.ai.openai.advanced.controller;

import com.ai.openai.advanced.model.Countries;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class StructuredOutputController {

    private final ChatClient chatClient;

    public StructuredOutputController(ChatClient.Builder chatClientBuilder) {
        chatClient = chatClientBuilder.defaultAdvisors(new SimpleLoggerAdvisor()).build();
    }

    @GetMapping("/ai/chat-bean")
    public ResponseEntity<Countries> chatBean(@RequestParam("msg") String message) {
       Countries countries =  chatClient.prompt().user(message).call().entity(Countries.class);
       return ResponseEntity.ok(countries);
    }

    @GetMapping("/ai/chat-list")
    public ResponseEntity<List<String>> chatList(@RequestParam("msg") String message) {
        List<String> cities =  chatClient.prompt().user(message).call().entity(new ListOutputConverter());
        return ResponseEntity.ok(cities);
    }

    @GetMapping("/ai/chat-map")
    public ResponseEntity<Map<String, Object>> chatMap(@RequestParam("msg") String message) {
        Map<String, Object> cities =  chatClient.prompt().user(message).call().entity(new MapOutputConverter());
        return ResponseEntity.ok(cities);
    }

    @GetMapping("/ai/chat-custom-list")
    public ResponseEntity<List<Countries>> chatCustomList(@RequestParam("msg") String message) {
        List<Countries> countries =  chatClient.prompt().user(message).call().entity(new ParameterizedTypeReference<List<Countries>>() {
        });
        return ResponseEntity.ok(countries);
    }
}
