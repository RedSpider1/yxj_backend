package com.redspider.pss.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Administrator
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface  NoRepeatSubmit {

    /**
     * 设置请求锁定时间
     * 默认0为redisson的30秒，自动续约锁
     *
     * @return
     */
    int lockTime() default 0;

}
