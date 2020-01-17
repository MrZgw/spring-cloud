package com.spring.cloud.redis.lock.api;

/**
 * @ClassName ILockExecutor
 * @Description 分布式锁操作接口
 * @Author zgw
 * @Date 2020/1/16 15:45
 **/
public interface ILockExecutor {

    /**
     * 获取锁
     *
     * @param var1 key
     * @param var2 value
     * @param var3 过期时间
     * @return boolean true:成功,false:失败
     */
    boolean acquire(String var1, String var2, long var3);

    /**
     * 释放锁
     *
     * @param lockInfo 锁对象
     * @return boolean true:成功,false:失败
     */
    boolean release(LockInfo lockInfo);
}
