package com.spring.cloud.redis.autoconfigure;

import com.spring.cloud.redis.template.RedisRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @ClassName RedisAutoConfiguration
 * @Description redis配置类
 * @Author zgw
 * @Date 2020/1/15 18:11
 **/
@Configuration
@ConditionalOnClass(RedisRepository.class)
@EnableConfigurationProperties(RedisProperties.class)
public class RedisAutoConfiguration {

    /**
     * RedisRepository自动化配置
     *
     * @param redisTemplate redis操作模板
     * @return RedisRepository
     */
    @Bean
    @ConditionalOnMissingBean
    public RedisRepository redisRepository(RedisTemplate redisTemplate) {
        return new RedisRepository(redisTemplate);
    }
}
