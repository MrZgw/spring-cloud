package com.spring.cloud.redis.autoconfigure;

import com.spring.cloud.redis.lock.aop.LockAnnotationAdvisor;
import com.spring.cloud.redis.lock.aop.LockInterceptor;
import com.spring.cloud.redis.lock.api.ILockExecutor;
import com.spring.cloud.redis.lock.api.LockTemplate;
import com.spring.cloud.redis.lock.api.impl.LockExecutorImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @ClassName RedisLockAutoConfiguration
 * @Description redis分布式锁自动配置
 * @Author zgw
 * @Date 2020/1/16 17:47
 **/
@Configuration
@ConditionalOnClass(RedisTemplate.class)
public class RedisLockAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ILockExecutor lockExecutor(RedisTemplate redisTemplate) {
        LockExecutorImpl lockExecutor = new LockExecutorImpl();
        lockExecutor.setRedisTemplate(redisTemplate);
        return lockExecutor;
    }

    @Bean
    @ConditionalOnMissingBean
    public LockTemplate lockTemplate(ILockExecutor lockExecutor) {
        LockTemplate lockTemplate = new LockTemplate();
        lockTemplate.setLockExecutor(lockExecutor);
        return lockTemplate;
    }

    @Bean
    @ConditionalOnMissingBean
    public LockInterceptor lockInterceptor(LockTemplate lockTemplate) {
        LockInterceptor lockInterceptor = new LockInterceptor();
        lockInterceptor.setLockTemplate(lockTemplate);
        return lockInterceptor;
    }

    @Bean
    @ConditionalOnMissingBean
    public LockAnnotationAdvisor lockAnnotationAdvisor(LockInterceptor lockInterceptor) {
        return new LockAnnotationAdvisor(lockInterceptor);
    }
}
