package com.spring.cloud.redis.template;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName RedisRepository
 * @Description redis基本操作封装
 * @Author zgw
 * @Date 2020/1/16 10:04
 **/
@Slf4j
public class RedisRepository {

    /**
     * key序列化实现类
     */
    private static final RedisSerializer keySerializer = new StringRedisSerializer();
    /**
     * value序列化实现类
     */
    private static final RedisSerializer valueSerializer = new GenericJackson2JsonRedisSerializer();

    private RedisTemplate<String, Object> redisTemplate;

    public RedisRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.redisTemplate.setKeySerializer(keySerializer);
        this.redisTemplate.setValueSerializer(valueSerializer);
        this.redisTemplate.setHashKeySerializer(keySerializer);
        this.redisTemplate.setHashValueSerializer(valueSerializer);
    }

    /**
     * 清楚db
     *
     * @param redisClusterNode redis集群节点
     */
    public void flushDb(RedisClusterNode redisClusterNode) {
        redisTemplate.opsForCluster().flushDb(redisClusterNode);
    }

    /**
     * 判断某个主键是否存在
     *
     * @param key the key
     * @return the boolean
     */
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 设置key过期时间(毫秒)
     *
     * @param key  key值
     * @param time 过期时间
     */
    public void setExpire(String key, long time) {
        redisTemplate.expire(key, time, TimeUnit.MILLISECONDS);
    }

    /**
     * 获取key过期时间(毫秒)
     *
     * @param key key值
     * @return long
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.MILLISECONDS);
    }

    /**
     * string操作set
     *
     * @param key   key值
     * @param value value值
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * string操作set
     *
     * @param key   key值
     * @param value value值
     * @param time  过期时间(毫秒)
     */
    public void set(String key, Object value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.MILLISECONDS);
    }

    /**
     * string操作get
     *
     * @param key key值
     * @return value值
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * list操作leftPush
     *
     * @param key    key值
     * @param values value值
     * @param time   过期时间
     */
    public void leftPushAll(String key, List<Object> values, long time) {
        if (time == 0) {
            redisTemplate.opsForList().leftPushAll(key, values);
        } else {
            redisTemplate.opsForList().leftPushAll(key, values, time, TimeUnit.MILLISECONDS);
        }

    }

    /**
     * list操作leftPush
     *
     * @param key   key值
     * @param value value值
     */
    public void leftPush(String key, Object value) {
        redisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * list操作leftPop
     *
     * @param key key值
     */
    public Object leftPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * list操作rightPush
     *
     * @param key    key值
     * @param values value值
     * @param time   过期时间
     */
    public void rightPushAll(String key, List<Object> values, long time) {
        if (time == 0) {
            redisTemplate.opsForList().rightPushAll(key, values);
        } else {
            redisTemplate.opsForList().rightPushAll(key, values, time, TimeUnit.MILLISECONDS);
        }

    }

    /**
     * list操作rightPush
     *
     * @param key   key值
     * @param value value值
     */
    public void rightPush(String key, Object value) {
        redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * list操作rightPop
     *
     * @param key key值
     */
    public Object rightPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * hash操作
     *
     * @param key       key值
     * @param hashKey   value值的key
     * @param hashValue value值
     */
    public void putHashValue(String key, String hashKey, Object hashValue) {
        redisTemplate.opsForHash().put(key, hashKey, hashValue);
    }

    /**
     * hash操作
     *
     * @param key key值
     * @param map hash
     */
    public void putHashAll(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * hash操作,获取hash单个value值
     *
     * @param key     key值
     * @param hashKey value的key值
     * @return value
     */
    public Object getHashValue(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * hash操作,根据key值删除
     *
     * @param key      the key
     * @param hashKeys the hash keys
     */
    public void delHashValues(String key, Object... hashKeys) {
        log.debug("[redisTemplate redis]  delHashValues()  key={}", key);
        redisTemplate.opsForHash().delete(key, hashKeys);
    }

    /**
     * 批量删除key
     *
     * @param keys key数组
     */
    public void delKeyPipeline(String... keys) {
        if (keys == null || keys.length <= 0) {
            return;
        }
        redisTemplate.executePipelined((RedisCallback<Long>) redisConnection -> {
            for (String key : keys) {
                redisConnection.del(key.getBytes(Charset.defaultCharset()));
            }
            return null;
        });
    }

}
