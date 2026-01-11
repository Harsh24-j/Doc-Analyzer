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

    @Autowired
    private GeminiConfigProperties geminiConfig;

    public AiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable(value = "summaries", key = "#text.hashCode()")
    public String getSummary(String text) {
        
        // URL with Stable v1 Version
        String url = geminiConfig.getApiUrl() + "?key=" + geminiConfig.getApiKey();
        
        // Simplified Payload: Only 'contents' are needed since model is in the URL
        Map<String, Object> textPart = Map.of("text", "Summarize this document in 5 bullet points: " + text);
        Map<String, Object> parts = Map.of("parts", List.of(textPart));
        Map<String, Object> payload = Map.of("contents", List.of(parts));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

        try {
            System.out.println("AiService: Calling Gemini API at: " + geminiConfig.getApiUrl());
            
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                url, 
                HttpMethod.POST, 
                entity, 
                new ParameterizedTypeReference<Map<String, Object>>() {}
            );
            
            if (response.getBody() == null) return "Error: API response is empty.";

            // Parsing the nested JSON response
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