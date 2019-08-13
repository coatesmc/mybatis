package com.coates.mybatis.entity;

/**
 * @ClassName User
 * @Description TODO
 * @Author mc
 * @Date 2019/8/13 15:43
 * @Version 1.0
 **/
public class User {
    private int id;
    private String username;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
