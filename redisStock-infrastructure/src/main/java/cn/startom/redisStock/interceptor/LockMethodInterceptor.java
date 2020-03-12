package cn.startom.redisStock.interceptor;


import cn.startom.redisStock.annotation.CacheLock;
import cn.startom.redisStock.common.CacheKeyGenerator;
import cn.startom.redisStock.common.RedissonConnector;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

@Aspect
@Configuration
public class LockMethodInterceptor {
    private final CacheKeyGenerator cacheKeyGenerator;
    private final RedissonConnector redissonConnector;

    @Autowired
    public LockMethodInterceptor(CacheKeyGenerator cacheKeyGenerator,RedissonConnector redissonConnector){
        this.cacheKeyGenerator=cacheKeyGenerator;
        this.redissonConnector=redissonConnector;
    }
    @Around("execution(public * *(..)) && @annotation(cn.startom.redisStock.annotation.CacheLock)")
    public Object interceptor(ProceedingJoinPoint pjp){
        MethodSignature signature=(MethodSignature)pjp.getSignature();
        Method method=signature.getMethod();
        CacheLock lock=method.getAnnotation(CacheLock.class);
        if(StringUtils.isEmpty(lock.prefix()))
        {
            throw new RuntimeException("lock key can't be null...");
        }
        final String lockKey=cacheKeyGenerator.getLockKey(pjp);
        try{

            //Redlock 分布式锁实现
            RedissonClient rc= redissonConnector.getClient();
            RLock rlock= rc.getLock(lockKey);
            Boolean success = rlock.tryLock(100, lock.expire(), lock.timeUnit());
            if(success){
                try {
                    return pjp.proceed();
                }finally {
                    rlock.unlock();
                }
            }else{
                throw new RuntimeException("获取锁失败！");
            }
        } catch(Throwable throwable){
            throw new RuntimeException("系统错误！");
        }
    }
}

