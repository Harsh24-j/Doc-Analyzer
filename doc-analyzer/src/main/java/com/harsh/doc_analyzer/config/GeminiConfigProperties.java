package com.harsh.doc_analyzer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class to map Gemini API properties from application.properties.
 * Uses Type-safe configuration properties for better maintainability.
 */
@Configuration
@ConfigurationProperties(prefix = "gemini")
public class GeminiConfigProperties {
    
    // Maps to 'gemini.api-url' in application.properties
    private String apiUrl; 
    
    // Maps to 'gemini.api-key' in application.properties
    private String apiKey; 

    /**
     * Manual Getters and Setters are required for Spring Boot 
     * to perform successful property mapping.
     */
    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}