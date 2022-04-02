package com.xhc.test;

import com.xhc.mapper.UserMapper;
import com.xhc.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class TestMyBatis {

    UserMapper userMapper=null;

    /**
     * 在单元测试之前执行
     */
    @Before
    public void init(){
        try {
            //从XML中构建SqlSessionFactory
            String resource = "mybatis-config.xml";
            //加载MyBatis的配置文件，返回字节流
            InputStream inputStream = Resources.getResourceAsStream(resource);
            System.out.println(inputStream);
            //解析
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            System.out.println(sqlSession);
            //框架底层使用JDK动态代理给接口生成实现类对象
            userMapper = sqlSession.getMapper(UserMapper.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询所有数据
     */
    @Test
    public void testList(){
        System.out.println("测试！");
        List<User> users = userMapper.list();
        for (User user : users) {
            System.out.println(user);
        }
    }

    /**
     * 根据ID查询
     */
    @Test
    public void testfindById(){
        User u=new User();
        u.setId(2);
        User user = userMapper.findById(u);
        System.out.println(user);
    }

    /**
     * 测试新增
     */
    @Test
    public void testInsert(){
        User user=new User();
        user.setUsername("小B");
        int insert = userMapper.add(user);
        System.out.println(insert>0?"新增成功":"新增失败");
    }


    /**
     * 测试更新
     */
    @Test
    public void testUpdate(){
        User user=new User();
        user.setId(2);
        user.setUsername("niudd");
        Integer update = userMapper.updateUser(user);
        System.out.println(update>0?"更新成功":"更新失败");
    }

    /**
     * 测试删除（通过USER）
     */
    @Test
    public void testDelete(){
        User u=new User();
        u.setId(1);
        Integer delete = userMapper.delete(u);
        System.out.println(delete>0?"删除成功":"删除失败");
    }

    /**
     * 测试删除（通过ID）
     */
    @Test
    public void testDeleteById(){
        Integer delete = userMapper.deleteUserById(54);
        System.out.println(delete>0?"删除成功":"删除失败");
    }
}
