package com.txt1stparkuor.Ecommerce.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class RedisCacheConfig {

        @Bean
        public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {

                RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(Duration.ofMinutes(30))
                                .disableCachingNullValues()
                                .serializeKeysWith(RedisSerializationContext.SerializationPair
                                                .fromSerializer(new StringRedisSerializer()))
                                .serializeValuesWith(RedisSerializationContext.SerializationPair
                                                .fromSerializer(GenericJacksonJsonRedisSerializer.builder().build()));

                Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

                // Cache "products" -> TTL 2 Hours
                cacheConfigurations.put("products", defaultConfig.entryTtl(Duration.ofHours(2)));

                // Cache "similar_products" -> TTL 12 Hours (Expensive ML recommendations)
                cacheConfigurations.put("similar_products", defaultConfig.entryTtl(Duration.ofHours(12)));

                // Cache "categories" -> TTL 24 Hours (Static data)
                cacheConfigurations.put("categories", defaultConfig.entryTtl(Duration.ofHours(24)));

                // Cache "product_catalog" -> TTL 1 Hour (Paginated/Filtered listings)
                cacheConfigurations.put("product_catalog", defaultConfig.entryTtl(Duration.ofHours(1)));

                return RedisCacheManager.builder(connectionFactory)
                                .cacheDefaults(defaultConfig)
                                .withInitialCacheConfigurations(cacheConfigurations)
                                .build();
        }
}