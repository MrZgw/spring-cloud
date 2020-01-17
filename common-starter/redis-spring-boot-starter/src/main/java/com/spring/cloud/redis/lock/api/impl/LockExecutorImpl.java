package com.spring.cloud.redis.lock.api.impl;

import com.spring.cloud.redis.lock.api.ILockExecutor;
import com.spring.cloud.redis.lock.api.LockInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collections;

/**
 * @ClassName LockExecutorImpl
 * @Description 分布式锁实现
 * @Author zgw
 * @Date 2020/1/16 15:52
 **/
@Slf4j
public class LockExecutorImpl implements ILockExecutor {

    /**
     * 获取锁lua脚本
     */
    private static final RedisScript<String> LOCK_SCRIPT = new DefaultRedisScript("return redis.call('set',KEYS[1],ARGV[1],'NX','PX',ARGV[2])", String.class);

    /**
     * 释放锁的lua脚本
     */
    private static final RedisScript<String> UNLOCK_SCRIPT = new DefaultRedisScript("if redis.call('get',KEYS[1]) == ARGV[1] then return tostring(redis.call('del',KEYS[1])==1) else return 'false' end", String.class);

    private RedisTemplate redisTemplate;

    private static final String LOCK_SUCCESS = "OK";

    @Override
    public boolean acquire(String var1, String var2, long var3) {
        Object result = redisTemplate.execute(LOCK_SCRIPT, redisTemplate.getStringSerializer(), redisTemplate.getStringSerializer(), Collections.singletonList(var1), new Object[]{var2, String.valueOf(var3)});
        return LOCK_SUCCESS.equals(result);
    }

    @Override
    public boolean release(LockInfo lockInfo) {
        Object result = redisTemplate.execute(UNLOCK_SCRIPT, this.redisTemplate.getStringSerializer(), this.redisTemplate.getStringSerializer(), Collections.singletonList(lockInfo.getLockKey()), new Object[]{lockInfo.getLockValue()});
        return Boolean.valueOf(result.toString());
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
