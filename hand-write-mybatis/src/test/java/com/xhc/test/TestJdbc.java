package com.xhc.test;

import com.xhc.pojo.User;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestJdbc {

    /**
     * 回顾JDBC(Mybatis封装的Jdbc)
     */
    @Test
    public void testSearch() {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        List<User> users=new ArrayList<User>();
        try {
            //加载驱动(注册JDBC驱动)
            Class.forName("com.mysql.jdbc.Driver");
            //获取负责数据库连接的连接对象
            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/hand-write-mybatis?useSSL=false&amp;serverTimezone=UTC&amp;characterEncoding=UTF-8","root","1234");
            //获取preparedStatement
            preparedStatement = connection.prepareStatement("select * from user");
            //执行SQL返回ResultSet（查询的结果集）
            resultSet = preparedStatement.executeQuery();
            //遍历变量数据
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                User user=new User(id,username); //带参构造封装
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {//用完之后原始的jdbc数据库关一下
            try {
                if(resultSet!=null){
                    resultSet.close();
                }
                if(preparedStatement!=null){
                    preparedStatement.close();
                }
                if(connection!=null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
            }
        }
        System.out.println("查询到的数据是:"+users);
    }

    /**
     * 回顾新增
     */

    @Test

    public void testInsert(){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        List<User> users=new ArrayList<User>();
        try {
            //加载驱动(注册JDBC驱动)
            Class.forName("com.mysql.jdbc.Driver");
            //获取连接对象
            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/hand-write-mybatis?useSSL=false&amp;serverTimezone=UTC&amp;characterEncoding=UTF-8","root","1234");
            //获取preparedStatement
            //null主键自增
            preparedStatement = connection.prepareStatement("insert into user values (null,?)");
            preparedStatement.setObject(1,"小王");
            //执行SQL
            //count数据库中受影响的行数
            int count = preparedStatement.executeUpdate();
            System.out.println(count>0?"新增成功":"新增失败");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(resultSet!=null){
                    resultSet.close();
                }
                if(preparedStatement!=null){
                    preparedStatement.close();
                }
                if(connection!=null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
            }
        }
    }
}
