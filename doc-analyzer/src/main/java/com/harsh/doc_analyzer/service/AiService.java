package com.harsh.doc_analyzer.service;

import com.harsh.doc_analyzer.config.GeminiConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;
import org.springframework.core.ParameterizedTypeReference;

@Service
public class AiService {

    private final RestTemplate restTemplate;

    // Injecting custom configuration properties for Gemini API
    @Autowired
    private GeminiConfigProperties geminiConfig;

    public AiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Generates a summary for the given text using Google Gemini AI.
     * Caches the result to reduce API latency for identical requests.
     * @param text The extracted document text to be summarized.
     * @return Summarized text in 5 points.
     */
    @Cacheable(value = "summaries", key = "#text.hashCode()")
    public String getSummary(String text) {
        
        // Debugging logs to verify configuration values
        System.out.println("DEBUG: Fetching URL -> " + geminiConfig.getApiUrl());
        System.out.println("DEBUG: Fetching Key -> " + geminiConfig.getApiKey());

        // Safety Check: Ensure API URL is configured correctly
        if (geminiConfig.getApiUrl() == null || geminiConfig.getApiUrl().isEmpty()) {
            return "Error: 'gemini.api-url' not found in application.properties!";
        }

        String url = geminiConfig.getApiUrl() + "?key=" + geminiConfig.getApiKey();
        
        // Preparing the request payload for Gemini API
        Map<String, Object> textPart = Map.of("text", "Summarize this document in 5 points: " + text);
        Map<String, Object> parts = Map.of("parts", List.of(textPart));
        Map<String, Object> contents = Map.of("contents", List.of(parts));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(contents, headers);

        try {
            System.out.println("AiService: Calling Gemini API...");
            
            // Type-safe API call using ParameterizedTypeReference
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                url, 
                HttpMethod.POST, 
                entity, 
                new ParameterizedTypeReference<Map<String, Object>>() {}
            );
            
            if (response.getBody() == null) return "Error: API response is empty.";

            // Logic to parse the complex JSON structure of Gemini API response
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.getBody().get("candidates");
            Map<String, Object> firstCandidate = candidates.get(0);
            
            @SuppressWarnings("unchecked")
            Map<String, Object> content = (Map<String, Object>) firstCandidate.get("content");
            
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> resParts = (List<Map<String, Object>>) content.get("parts");
            Map<String, Object> firstPart = resParts.get(0);
            
            System.out.println("AiService: Summary generated successfully!");
            return firstPart.get("text").toString();

        } catch (Exception e) {
            System.err.println("AiService Error: " + e.getMessage());
            return "Gemini API call failed: " + e.getMessage();
        }
    }
}