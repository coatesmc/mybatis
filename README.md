**手写的MyBatis（注解版）**

    实现思路：
        1.自定义注解
        2.利用动态代理操作目标对象
        3.利用反射绑定参数
        4.执行SQL语句
    
    需要改进地方:
        将SQL语句的参数替换变为?时 需要注意 sql 支持大小写查询方式,做分隔时需注意前后的空格
        
    注意:  升级后的mysql驱动类 Driver位置由 `com.mysql.jdbc.Driver` 变为 `com.mysql.cj.jdbc.Driver `