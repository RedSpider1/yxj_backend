package com.redspider.pss.aop.annotation;

import com.redspider.pss.constant.enums.UserRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * the annotation of permission verify(u can call it authorization validation also).
 *
 * @author 千般婉转皆戏言丶
 * @date 2021/9/3 0:09
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleValidation {

    UserRole role() default UserRole.TOURIST;

}
