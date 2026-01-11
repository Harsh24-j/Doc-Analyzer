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
        // Base URL + API Key
        String url = geminiConfig.getApiUrl() + "?key=" + geminiConfig.getApiKey();
        
        // --- CORRECT PAYLOAD (No 'model' field here) ---
        Map<String, Object> textPart = Map.of("text", "Summarize this document in 5 bullet points: " + text);
        Map<String, Object> parts = Map.of("parts", List.of(textPart));
        Map<String, Object> contents = Map.of("contents", List.of(parts));
        // -----------------------------------------------

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(contents, headers);

        try {
            System.out.println("AiService: Calling Gemini API...");
            
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                url, 
                HttpMethod.POST, 
                entity, 
                new ParameterizedTypeReference<Map<String, Object>>() {}
            );
            
            if (response.getBody() == null) return "Error: API response is empty.";

            // Parsing logic
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.getBody().get("candidates");
            Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
            List<Map<String, Object>> resParts = (List<Map<String, Object>>) content.get("parts");
            
            return resParts.get(0).get("text").toString();

        } catch (Exception e) {
            System.err.println("AiService Error: " + e.getMessage());
            return "Gemini API call failed: " + e.getMessage();
        }
    }
}