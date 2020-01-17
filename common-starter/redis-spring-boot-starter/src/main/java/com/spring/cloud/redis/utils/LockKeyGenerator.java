package com.spring.cloud.redis.utils;

import com.spring.cloud.redis.lock.annotation.RedisLock;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName LockKeyGenerator
 * @Description key生成器
 * @Author zhanguowei
 * @Date 2019/5/16 18:30
 **/
public class LockKeyGenerator {

    private static final ParameterNameDiscoverer NAME_DISCOVERER = new DefaultParameterNameDiscoverer();
    private static final ExpressionParser PARSER = new SpelExpressionParser();
    private static final String LOCK_PREFIX = "lock:";//锁前缀

    public LockKeyGenerator() {
    }

    public String getKeyName(MethodInvocation invocation, RedisLock redisLock) {
        StringBuilder sb = new StringBuilder();
        Method method = invocation.getMethod();
        //类名+方法名
        sb.append(method.getDeclaringClass().getName()).append(".").append(method.getName());
        if (redisLock.keys().length > 1 || !"".equals(redisLock.keys()[0])) {
            sb.append(this.getSpelDefinitionKey(redisLock.keys(), method, invocation.getArguments()));
        }

        return sb.toString();
    }

    public String getKeyName(ProceedingJoinPoint joinPoint, RedisLock redisLock) {
        StringBuilder sb = new StringBuilder(LOCK_PREFIX);
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        //类名+方法名
        sb.append(method.getDeclaringClass().getName()).append(".").append(method.getName());
        if (redisLock.keys().length > 1 || !"".equals(redisLock.keys()[0])) {
            sb.append(this.getSpelDefinitionKey(redisLock.keys(), method, joinPoint.getArgs()));
        }

        return sb.toString();
    }

    private String getSpelDefinitionKey(String[] definitionKeys, Method method, Object[] parameterValues) {
        EvaluationContext context = new MethodBasedEvaluationContext((Object) null, method, parameterValues, NAME_DISCOVERER);
        List<String> definitionKeyList = new ArrayList(definitionKeys.length);
        String[] var6 = definitionKeys;
        int var7 = definitionKeys.length;

        for (int var8 = 0; var8 < var7; ++var8) {
            String definitionKey = var6[var8];
            if (definitionKey != null && !definitionKey.isEmpty()) {
                String key = PARSER.parseExpression(definitionKey).getValue(context).toString();
                definitionKeyList.add(key);
            }
        }

        return StringUtils.collectionToDelimitedString(definitionKeyList, ".", "", "");
    }
}
