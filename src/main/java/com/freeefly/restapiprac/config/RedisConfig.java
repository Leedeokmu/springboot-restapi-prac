package com.freeefly.restapiprac.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
@EnableCaching
public class RedisConfig {

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .entryTtl(Duration.ofSeconds(CacheKey.DEFAULT_EXPIRE_SEC))
                .computePrefixWith(CacheKeyPrefix.simple())
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()));

        // 각 캐시 키에 대한 초기 캐시 시간 설정
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put(CacheKey.USER, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(CacheKey.USER_EXPIRE_SEC)));
        cacheConfigurations.put(CacheKey.BOARD, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(CacheKey.BOARD_EXPIRE_SEC)));
        cacheConfigurations.put(CacheKey.POST, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(CacheKey.POST_EXPIRE_SEC)));
        cacheConfigurations.put(CacheKey.POSTS, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(CacheKey.POST_EXPIRE_SEC)));

        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory).cacheDefaults(configuration)
                .withInitialCacheConfigurations(cacheConfigurations).build();

    }

}
