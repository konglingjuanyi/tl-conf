package com.tiaoling.cloud.dmps.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限限制
 * @author yuhonglie 2016-10-10 16:29:02
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermessionLimit {

    /**
     * 登陆拦截 (默认拦截)
     */
    boolean limit() default true;

}