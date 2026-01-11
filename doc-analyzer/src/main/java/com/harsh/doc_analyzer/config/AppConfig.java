package com.harsh.doc_analyzer.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * Main configuration class for the application.
 * Enables caching and defines global beans for the Spring Context.
 */
@Configuration
@EnableCaching
public class AppConfig {

    /**
     * Configures a RestTemplate bean for making HTTP requests to external APIs (e.g., Gemini).
     * @return A new instance of RestTemplate.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * Configures the Redis Cache Manager with specific TTL and serialization rules.
     * @param connectionFactory Connection factory provided by the Redis starter.
     * @return A configured CacheManager.
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        
        // Caching configuration: Set Time-To-Live (TTL) to 10 minutes
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10)) // Data will expire after 10 minutes
                .disableCachingNullValues(); // Avoid caching null responses to save memory

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .build();
    }
}