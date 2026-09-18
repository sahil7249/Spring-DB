package com.DB.SpringDB.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@EnableCaching
@Configuration 
public class RedisConfig {
    @Bean 
    public RedisCacheManager redisCachManager (RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration defaultConfig = 
            RedisCacheConfiguration.defaultCacheConfig()
                                   .entryTtl(Duration.ofMinutes(10))
                                   .serializeKeysWith(RedisSerializationContext.SerializationPair
                                        .fromSerializer(new StringRedisSerializer()))
                                    .serializeValuesWith(RedisSerializationContext.SerializationPair
                                        .fromSerializer(RedisSerializer.json()));   

        Map<String,RedisCacheConfiguration> cacheConfiguration = new HashMap<>();
        cacheConfiguration.put("products",defaultConfig.entryTtl(Duration.ofHours(1)));
        cacheConfiguration.put("users",defaultConfig.entryTtl(Duration.ofMinutes(10)));
        cacheConfiguration.put("orders",defaultConfig.entryTtl(Duration.ofMinutes(2)));
        
        return RedisCacheManager.builder(redisConnectionFactory)
                                .cacheDefaults(defaultConfig)
                                .withInitialCacheConfigurations(cacheConfiguration)
                                .build();
    }
}
