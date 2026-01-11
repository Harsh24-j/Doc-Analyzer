package com.harsh.doc_analyzer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class to map Gemini API properties.
 * Prefix 'gemini.api' matches 'gemini.api.url' and 'gemini.api.key'.
 */
@Configuration
@ConfigurationProperties(prefix = "gemini.api") // Prefix match hona chahiye
public class GeminiConfigProperties {
    private String url; 
    private String key; 

    public String getApiUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getApiKey() { return key; }
    public void setKey(String key) { this.key = key; }
}