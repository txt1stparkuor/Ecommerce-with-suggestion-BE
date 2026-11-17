package com.txt1stparkuor.Ecommerce.config;

import com.txt1stparkuor.Ecommerce.constant.CacheName;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import tools.jackson.databind.jsontype.PolymorphicTypeValidator;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class RedisCacheConfig {

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {

        PolymorphicTypeValidator typeValidator = BasicPolymorphicTypeValidator.builder()
                .allowIfSubType("com.txt1stparkuor.Ecommerce.domain.dto.")
                .allowIfSubType("java.util.")
                .allowIfSubType("java.math.")
                .build();

        GenericJacksonJsonRedisSerializer serializer = GenericJacksonJsonRedisSerializer.builder()
                .enableDefaultTyping(typeValidator)
                .enableSpringCacheNullValueSupport()
                .build();

        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30))
                .disableCachingNullValues()
                .serializeKeysWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(serializer));

        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put(CacheName.PRODUCT, defaultConfig.entryTtl(Duration.ofHours(2)));
        cacheConfigurations.put(CacheName.SIMILAR_PRODUCTS, defaultConfig.entryTtl(Duration.ofHours(12)));
        cacheConfigurations.put(CacheName.CATEGORIES, defaultConfig.entryTtl(Duration.ofHours(24)));
        cacheConfigurations.put(CacheName.PRODUCT_LIST, defaultConfig.entryTtl(Duration.ofHours(1)));
        cacheConfigurations.put(CacheName.SALES_SUMMARY, defaultConfig.entryTtl(Duration.ofMinutes(5)));
        cacheConfigurations.put(CacheName.SALES_TREND, defaultConfig.entryTtl(Duration.ofMinutes(15)));
        cacheConfigurations.put(CacheName.TOP_PRODUCTS, defaultConfig.entryTtl(Duration.ofMinutes(15)));
        cacheConfigurations.put(CacheName.LOW_STOCK, defaultConfig.entryTtl(Duration.ofMinutes(5)));

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(cacheConfigurations)
                .build();
    }
}