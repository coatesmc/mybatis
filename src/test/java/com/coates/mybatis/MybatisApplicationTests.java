package com.coates.mybatis;

import com.coates.mybatis.mapper.UserMapper;
import com.coates.mybatis.sql.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;


public class MybatisApplicationTests {
    private static final Logger logger = LoggerFactory.getLogger(MybatisApplicationTests.class);



    public static void main(String[] args) {
       UserMapper userMapper = SqlSession.getMapper(UserMapper.class);
        int insertUserResult = userMapper.insertUser(2, "coates", "123456");
        logger.info("insertUserResult:[{}]", insertUserResult);
    }


}
