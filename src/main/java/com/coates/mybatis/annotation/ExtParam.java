package com.coates.mybatis.annotation;

import java.lang.annotation.*;

/**
 * @ClassName ExtParam
 * @Description TODO
 * @Author mc
 * @Date 2019/8/13 10:27
 * @Version 1.0
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface ExtParam {
    String value();
}
