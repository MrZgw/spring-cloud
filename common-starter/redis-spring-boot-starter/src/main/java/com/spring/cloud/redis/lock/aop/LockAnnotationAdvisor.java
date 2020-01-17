package com.spring.cloud.redis.lock.aop;

import com.spring.cloud.redis.lock.annotation.RedisLock;
import lombok.NonNull;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import java.util.Objects;

/**
 * @ClassName LockAnnotationAdvisor
 * @Description lockAOP实现
 * @Author zhanguowei
 * @Date 2019/5/16 18:11
 **/
public class LockAnnotationAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {

    private Advice advice;
    private Pointcut pointcut;

    public LockAnnotationAdvisor(@NonNull LockInterceptor lockInterceptor) {
        if (Objects.isNull(lockInterceptor)) {
            throw new NullPointerException("lockInterceptor is null");
        }
        this.advice = lockInterceptor;
        this.pointcut = this.buildPointcut();
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (this.advice instanceof BeanFactoryAware) {
            ((BeanFactoryAware) this.advice).setBeanFactory(beanFactory);
        }
    }

    private Pointcut buildPointcut() {
        return AnnotationMatchingPointcut.forMethodAnnotation(RedisLock.class);
    }
}
