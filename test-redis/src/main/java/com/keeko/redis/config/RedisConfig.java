package com.keeko.redis.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis bean 相关配置
 *
 * @author chensq
 */
@Configuration
@ConditionalOnClass(RedisOperations.class)
public class RedisConfig {

    /**
     * RedisTemplate配置
     *
     * @param factory 工厂类定义Redis模板序列化
     * @return 定义Redis模板序列化
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        return template;
    }


}




















