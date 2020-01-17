//package com.spring.cloud.redis.lock.aop;
//
//import com.spring.cloud.redis.lock.annotation.RedisLock;
//import com.spring.cloud.redis.lock.api.LockInfo;
//import com.spring.cloud.redis.lock.api.LockTemplate;
//import com.spring.cloud.redis.utils.LockKeyGenerator;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
///**
// * @ClassName LockAspect
// * @Description 锁切面
// * @Author zgw
// * @Date 2020/1/16 17:15
// **/
//@Slf4j
//@Aspect
//@Component
//public class RedisLockAspect {
//
//    @Autowired
//    private LockTemplate lockTemplate;
//
//    private LockKeyGenerator keyGenerator = new LockKeyGenerator();
//
//    @Pointcut("@annotation(com.spring.cloud.redis.lock.annotation.RedisLock)")
//    public void RedisLockAspect() {
//    }
//
//    @Around(value = "RedisLockAspect()&&@annotation(redisLock)")
//    public Object interceptMethod(ProceedingJoinPoint joinPoint, RedisLock redisLock) throws Throwable {
//
//        Object var5;
//        LockInfo lockInfo = null;
//        //获取锁
//        try {
//            String keyName = keyGenerator.getKeyName(joinPoint, redisLock);
//            lockInfo = lockTemplate.lock(keyName, redisLock.expire(), redisLock.timeout());
//            if (lockInfo == null) {
//                var5 = null;
//                return var5;
//            }
//            var5 = joinPoint.proceed();
//        } finally {
//            //释放锁
//            lockTemplate.unLock(lockInfo);
//        }
//
//        return var5;
//    }
//}
