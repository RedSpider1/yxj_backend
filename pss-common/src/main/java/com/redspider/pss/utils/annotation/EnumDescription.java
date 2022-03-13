package com.redspider.pss.utils.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName EnumDescription
 * @Description
 * @Author hf
 * @Version V1.0
 **/
@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumDescription {
   String value();
}
