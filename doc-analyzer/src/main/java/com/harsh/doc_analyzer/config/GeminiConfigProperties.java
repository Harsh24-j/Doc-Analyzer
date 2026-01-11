package com.harsh.doc_analyzer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class to map Gemini API properties.
 * Prefix 'gemini.api' matches 'gemini.api.url' and 'gemini.api.key'.
 */
@Configuration
@ConfigurationProperties(prefix = "gemini.api")
public class GeminiConfigProperties {
    
    // Maps to 'gemini.api.url' in properties or 'GEMINI_API_URL' in Railway
    private String url; 
    
    // Maps to 'gemini.api.key' in properties or 'GEMINI_API_KEY' in Railway
    private String key; 

    // Getters
    public String getApiUrl() {
        return url;
    }

    public String getApiKey() {
        return key;
    }

    // Setters (Mandatory for Spring to inject values)
    public void setUrl(String url) {
        this.url = url;
    }

    public void setKey(String key) {
        this.key = key;
    }
}