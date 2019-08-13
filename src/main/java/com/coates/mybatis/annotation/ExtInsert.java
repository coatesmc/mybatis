package com.coates.mybatis.annotation;

import java.lang.annotation.*;

/**
 * @ClassName ExtInsert
 * @Description TODO
 * @Author mc
 * @Date 2019/8/13 10:24
 * @Version 1.0
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExtInsert {
    String value();
}
