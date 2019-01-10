package com.github.cyk.annotation;


import java.lang.annotation.*;

/**
 * 自定义注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
@Documented
public @interface FlightLog {
    String logStr() default "";

}
