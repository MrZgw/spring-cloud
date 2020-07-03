package com.spring.cloud.redis.lock.api;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName LockInfo
 * @Description 锁对象
 * @Author zgw
 * @Date 2019/5/16 17:22
 **/
@Data
@AllArgsConstructor
public class LockInfo {

    private String lockKey;

    private String lockValue;

    private Long acquireTimeOut;

    private Long expire;

    private int acquireCount;
}
