package com.coates.mybatis.sql;

import com.coates.mybatis.orm.mybatis.aop.MyInvocationHandlerMbatis;

import java.lang.reflect.Proxy;

/**
 * @ClassName SqlSession
 * @Description  加载mapper 接口
 * @Author mc
 * @Date 2019/8/13 10:38
 * @Version 1.0
 **/
public class SqlSession {

    public  static <T> T getMapper(Class classz){
        return (T) Proxy.newProxyInstance(classz.getClassLoader(),new Class [] {classz} ,new MyInvocationHandlerMbatis(classz));
    }
}
