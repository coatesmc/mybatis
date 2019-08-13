package com.coates.mybatis.mapper;

import com.coates.mybatis.annotation.ExtInsert;
import com.coates.mybatis.annotation.ExtParam;

/**
 * @ClassName mapper
 * @Description TODO
 * @Author mc
 * @Date 2019/8/13 10:25
 * @Version 1.0
 **/
public interface UserMapper {
    @ExtInsert("INSERT INTO user (id,username,password) VALUES (#{id},#{username},#{password});")
    public int insertUser(@ExtParam("id") Integer id, @ExtParam("username") String username, @ExtParam("password") String password);

}
