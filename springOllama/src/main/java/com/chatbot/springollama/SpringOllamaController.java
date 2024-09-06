package com.chatbot.springollama;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;
import org.springframework.ai.chat.prompt.Prompt;
@RestController
public class SpringOllamaController{
    private final OllamaChatModel chatModel;
    @Autowired
    public SpringOllamaController(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/ai/generate")
    public Map<String, String> generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        String response;

        // Check for the specific question and provide a predefined answer
        if (message.equalsIgnoreCase("What's your name?") || message.equalsIgnoreCase("What is your name?")) {
            response = "My name is ChatCat.";
        } else {
            // Call the chat model for other messages
            response = chatModel.call(message);
        }

        return Map.of("generation", response);
    }


    @GetMapping("/ai/generateStream")
    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return chatModel.stream(prompt);
    }





}
