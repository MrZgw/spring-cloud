package com.spring.cloud.redis.lock.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisLock {

    String[] keys() default {""};

    long expire() default 30000L;

    long timeout() default 2000L;
}
