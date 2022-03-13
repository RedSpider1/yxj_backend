package com.redspider.pss.aop;


import com.redspider.pss.aop.annotation.NoRepeatSubmit;
import com.redspider.pss.exception.PssValidationException;
import com.redspider.pss.utils.RequestUtils;
import com.redspider.pss.security.UserUtil;
import com.redspider.pss.response.ResponseCode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class RepeatSubmitAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepeatSubmitAspect.class);

    @Autowired
    private RedissonClient redisClient;

    @Pointcut("@annotation(noRepeatSubmit)")
    public void pointCut(NoRepeatSubmit noRepeatSubmit) {
    }

    @Around("pointCut(noRepeatSubmit)")
    public Object around(ProceedingJoinPoint pjp, NoRepeatSubmit noRepeatSubmit) throws Throwable {
        int lockSeconds = noRepeatSubmit.lockTime();
        HttpServletRequest request = RequestUtils.getRequest();
        Assert.notNull(request, "request can not null");
        Long userId = UserUtil.getUserId();
        String path = request.getServletPath();
        String key = getKey(userId, path);
        String clientId = getClientId() + "_" + key;
        LOGGER.info("lock info:{}, {}", clientId, lockSeconds);
        RLock lock = redisClient.getLock(key);
        boolean isSuccess;
        try {
            if (lockSeconds == 0) {
                isSuccess = lock.tryLock();
            } else {
                isSuccess = lock.tryLock(lockSeconds, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            LOGGER.error("tryLock error！");
            throw new PssValidationException(ResponseCode.ILLEGAL_PARAMETER);
        }
        if (isSuccess) {
            LOGGER.info("tryLock success, key = [{}], clientId = [{}]", key, clientId);
            // 获取锁成功
            Object result = null;
            try {
                // 执行进程
                result = pjp.proceed();
            } catch (Exception e) {
                LOGGER.error("releaseLock success with exception,clientId = [{}]", clientId);
            } finally {
                // 解锁
                lock.unlock();
                LOGGER.info("fake releaseLock success, clientId = [{}]", clientId);
            }
            return result;
        } else {
            // 获取锁失败，认为是重复提交的请求
            LOGGER.info("tryLock fail, key = [{}]", key);
            throw new PssValidationException(ResponseCode.ILLEGAL_PARAMETER);
        }
    }

    private String getKey(Long userId, String path) {
        return userId + path;
    }

    private String getClientId() {
        return UUID.randomUUID().toString();
    }

}
