package com.coates.mybatis.annotation;

import java.lang.annotation.*;

/**
 * @ClassName ExtSelect
 * @Description TODO
 * @Author mc
 * @Date 2019/8/13 11:34
 * @Version 1.0
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExtSelect {
    String value();
}
