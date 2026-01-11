package com.harsh.doc_analyzer.service;

import com.harsh.doc_analyzer.config.GeminiConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;
import org.springframework.core.ParameterizedTypeReference;

@Service
public class AiService {

    private final RestTemplate restTemplate;

    @Autowired
    private GeminiConfigProperties geminiConfig;

    public AiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getSummary(String text) {
        String url = geminiConfig.getApiUrl() + "?key=" + geminiConfig.getApiKey();
        
        Map<String, Object> textPart = Map.of("text", "Summarize this document in 5 bullet points: " + text);
        Map<String, Object> parts = Map.of("parts", List.of(textPart));
        Map<String, Object> contents = Map.of("contents", List.of(parts));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(contents, headers);

        try {
            System.out.println("AiService: Initiating Gemini API call...");
            
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                url, 
                HttpMethod.POST, 
                entity, 
                new ParameterizedTypeReference<Map<String, Object>>() {}
            );
            
            // --- UPDATED SAFETY CHECK START ---
            if (response.getBody() == null || !response.getBody().containsKey("candidates")) {
                System.err.println("AiService Error: No candidates found in response.");
                return "The Gemini API did not return any valid candidates. Perhaps the API Key or URL is incorrect.";
            }
            // --- UPDATED SAFETY CHECK END ---

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.getBody().get("candidates");
            
            if (candidates == null || candidates.isEmpty()) {
                return "Error: Gemini API candidates list is empty.";
            }

            @SuppressWarnings("unchecked")
            Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
            
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> resParts = (List<Map<String, Object>>) content.get("parts");
            
            return resParts.get(0).get("text").toString();

        } catch (Exception e) {
            System.err.println("AiService Exception: " + e.getMessage());
            return "Gemini API call failed: " + e.getMessage();
        }
    }
}