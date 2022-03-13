package com.redspider.pss.utils;

import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * @program: pss
 * @description: redis
 * @author: txy
 * @create: 2021-06-18 21:26
 **/
@Component
public class RedisUtils {
    /**
     * 防重复提交锁
     */
    public static final String REPEAT_LOCK = "repeatLock:";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public boolean tryLock(String lockKey, String clientId, long seconds) {
        ValueOperations<String, Object> stringObjectValueOperations = redisTemplate.opsForValue();
        Boolean boo = stringObjectValueOperations.setIfAbsent(REPEAT_LOCK + lockKey, clientId, seconds, TimeUnit.SECONDS);
        return boo != null && boo;
    }

    /**
     * 与 tryLock 相对应，用作释放锁
     *
     * @param lockKey
     * @return
     */
    public void releaseLock(String lockKey) {
         redisTemplate.delete(REPEAT_LOCK + lockKey);
    }


}
