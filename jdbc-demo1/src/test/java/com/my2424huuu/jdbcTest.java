package com.my2424huuu;

import org.junit.jupiter.api.Test;

import java.sql.*;

public class jdbcTest {
    /**
     * 编写JDBC程序，操作数据
     */
    @Test
    public void testUpdate() throws Exception{
        //1、注册驱动
        try {
            Class.forName( "com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        //2，获取连接
        String url ="jdbc:mysql://localhost:3306/web01";
        String username =  "root";
        String password = "1234";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            //3、获取SQL告句执行对象
            Statement statement = null;
            statement = connection.createStatement();
            //4，执行SQL
            int i = statement.executeUpdate( "update user set age 25 where id = 1");
            System.out.println("SQL执行完毕，影响的记录数为:"+i);
            //5，释放资源
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 编写JDBC程序, 查询数据
     */
    @Test
    public void testSelect() throws Exception {
        // 获取连接
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/web01", "root", "1234");
        // 创建预编译的PreparedStatement对象
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM user WHERE username = ? AND password = ?");
        // 设置参数
        pstmt.setString(1, "daqiao"); // 第一个问号对应的参数
        pstmt.setString(2, "123456"); // 第二个问号对应的参数
        // 执行查询
        ResultSet rs = pstmt.executeQuery();
        // 处理结果集
        while (rs.next()) {
            int id = rs.getInt("id");
            String uName = rs.getString("username");
            String pwd = rs.getString("password");
            String name = rs.getString("name");
            int age = rs.getInt("age");

            System.out.println("ID: " + id + ", Username: " + uName + ", Password: " + pwd + ", Name: " + name + ", Age: " + age);
        }
        // 关闭资源
        rs.close();
        pstmt.close();
        conn.close();
    }

}
