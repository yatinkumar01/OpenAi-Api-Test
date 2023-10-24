package com.example.openaitest.controller;

import com.example.openaitest.DTO.Message;
import com.example.openaitest.DTO.Request;
import com.example.openaitest.DTO.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/openAi")
public class MyController {

    // Inject the OpenAI model name from configuration
    @Value("${openai.model}")
    private String model;

    // Inject the OpenAI API URL from configuration
    @Value(("${openai.api.url}"))
    private String apiURL;

    private RestTemplate template;

    @Autowired
    public void setTemplate(RestTemplate template) {
        this.template = template;
    }

    // Handle a POST request to "/openAi/chat"
    @PostMapping("/chat")
    public String chat(@RequestBody Request request) {
        List<Message> messages = request.getMessages();

        // Create a Request object with the OpenAI model and the user's message
        Request getRequest = new Request(model, messages.get(0).getContent());

        // Make a POST request to the OpenAI API
        Response response = template.postForObject(apiURL, getRequest, Response.class);

        // Return the response from the OpenAI API
        return response.getChoices().get(0).getMessage().getContent();
    }
}
