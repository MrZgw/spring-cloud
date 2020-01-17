package com.spring.cloud.redis.lock.api;

import com.spring.cloud.redis.utils.LockUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;


/**
 * @ClassName LockTemplate
 * @Description redis分布式锁操作模板
 * @Author zgw
 * @Date 2020/1/16 16:56
 **/
@Slf4j
public class LockTemplate {

    private static final String PROCESS_ID = LockUtils.getLocalIP() + LockUtils.getJvmPid();

    private ILockExecutor lockExecutor;

    /**
     * 获取锁
     *
     * @param key     key
     * @param expire  过期时间
     * @param timeout 获取锁超时时间
     * @return lockInfo 锁对象
     */
    public LockInfo lock(String key, long expire, long timeout) throws InterruptedException {
        Assert.isTrue(timeout > 0L, "tryTimeout must more than 0");
        long start = System.currentTimeMillis();
        int acquireCount = 0;
        String value = PROCESS_ID + Thread.currentThread().getId();

        while (System.currentTimeMillis() - start < timeout) {
            boolean result = this.lockExecutor.acquire(key, value, expire);
            ++acquireCount;
            if (result) {
                return new LockInfo(key, value, expire, timeout, acquireCount);
            }

            Thread.sleep(50L);
        }

        log.info("lock failed, try {} times", acquireCount);
        return null;
    }

    /**
     * 释放锁
     *
     * @param lockInfo 锁对象
     * @return boolean
     */
    public boolean unLock(LockInfo lockInfo) {
        return this.lockExecutor.release(lockInfo);
    }

    public void setLockExecutor(ILockExecutor lockExecutor) {
        this.lockExecutor = lockExecutor;
    }
}
