package com.coates.mybatis.orm.mybatis.aop;

import java.lang.reflect.Field;

import com.coates.mybatis.annotation.ExtInsert;
import com.coates.mybatis.annotation.ExtParam;
import com.coates.mybatis.annotation.ExtSelect;
import com.coates.mybatis.orm.jdb.JDBCUtils;
import com.coates.mybatis.utils.SQLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName MyInvocationHandlerMbatis
 * @Description TODO
 * @Author mc
 * @Date 2019/8/13 10:36
 * @Version 1.0
 **/

public class MyInvocationHandlerMbatis implements InvocationHandler {
    private static Logger logger = LoggerFactory.getLogger(MyInvocationHandlerMbatis.class);
    private Object object;

    public MyInvocationHandlerMbatis(Object object) {
        this.object = object;
    }

    //proxy 代理对象 method 拦截方法 args方法上的参数
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //使用白话文翻译
        //1、判断方法上是否存在ExtInsert 注解
        ExtInsert extInsert = method.getDeclaredAnnotation(ExtInsert.class);
        if (extInsert != null) {
            return extInsertSql(extInsert, proxy, method, args);
        }
        // 2.查询的思路
        // 1. 判断方法上是否存 在注解
        ExtSelect extSelect = method.getDeclaredAnnotation(ExtSelect.class);
        if (extSelect != null) {
            String selectSql = extSelect.value();
            ConcurrentHashMap<Object, Object> paramsMap = paramsMap( method, args);
            // 4. 参数替换？传递方式
            List<String> sqlSelectParameter = SQLUtils.sqlSelectParameter(selectSql);
            // 5.传递参数
            List<Object> sqlParams = new ArrayList<>();
            for (String parameterName : sqlSelectParameter) {
                Object parameterValue = paramsMap.get(parameterName);
                sqlParams.add(parameterValue);
            }
            // 6.将sql语句替换成?
            String newSelectSql = SQLUtils.parameQuestion(selectSql, sqlSelectParameter);

            logger.info("newSelectSql:[{}],sqlParams:[{}]", newSelectSql, sqlParams.toString());

            ResultSet result = JDBCUtils.query(newSelectSql, sqlParams);

            if (!result.next()) {
                return null;
            }
            result.previous();
            Class<?> returnType = method.getReturnType();
            Object object = returnType.newInstance();
            while (result.next()) {
                Field[] declaredFields = returnType.getDeclaredFields();
                for (Field field : declaredFields) {
                    String fieldName = field.getName();
                    Object fieldValue = result.getObject(fieldName);
                    field.set(fieldName, fieldValue);
                }
            }
            return object;
        }


        return null;
    }

    /**
     *  拦截注解信息
     * @param extInsert
     * @param proxy
     * @param method
     * @param args
     * @return
     */
    public Object extInsertSql(ExtInsert extInsert, Object proxy, Method method, Object[] args) {
        //2、获取sql语句，获取注释insert语句
        String insertSql = extInsert.value();
        logger.info("insertSql:[{}]", insertSql);
        // 3. 获取方法的参数和SQL参数进行匹配
        // 定一个一个Map集合 KEY为@ExtParamValue,Value 结果为参数值
        ConcurrentHashMap<Object, Object> paramsMap = paramsMap( method, args);
        // 存放sql执行的参数---参数绑定过程
        String[] sqlInsertParameter = SQLUtils.sqlInsertParameter(insertSql);
        List<Object> sqlParser = sqlParameter(sqlInsertParameter, paramsMap);
        // 4. 根据参数替换参数变为?
        String newSQL = SQLUtils.parameQuestion(insertSql, sqlInsertParameter);
        logger.info("newSQL:[{}],sqlParams:[{}]", newSQL, sqlParser.toString());
        // 5. 调用jdbc底层代码执行语句
        return JDBCUtils.insert(newSQL, false, sqlParser);
    }

    /**
     * 获取方法数据 组装成list集合
     * @param sqlInsertParameter
     * @param paramsMap
     * @return
     */
    private List<Object> sqlParameter(String[] sqlInsertParameter, ConcurrentHashMap<Object, Object> paramsMap) {
        List<Object> sqlParameter = new ArrayList<>();
        for (String paramName : sqlInsertParameter) {
            Object paramValue = paramsMap.get(paramName.trim());
            sqlParameter.add(paramValue);
        }
        return sqlParameter;
    }

    /**
     * 获取方法数据 封装成map集合
     * @param method
     * @param args
     * @return
     */
    private ConcurrentHashMap<Object, Object> paramsMap( Method method, Object[] args) {
        //获取方法上面的参数
        ConcurrentHashMap<Object, Object> paramsMap = new ConcurrentHashMap<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            ExtParam extParam = parameter.getDeclaredAnnotation(ExtParam.class);
            if (extParam != null) {
                //获取参数名
                String paramName = extParam.value();
                Object paramValue = args[i];
                paramsMap.put(paramName, paramValue);
            }
        }
        return paramsMap;
    }
}
