package com.xhc.dao;

import com.xhc.pojo.User;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TestUserDao { //代理类实际上是目标类的子类
    @Test
    public void testUserDao() {
        Class c = UserDao.class;
        MyInvocationHandler h = new MyInvocationHandler();
        //生成一个代理类对象
        UserDao userDao = (UserDao) Proxy.newProxyInstance(c.getClassLoader(), new Class[]{c}, h);
        //可以通过JDK动态代理给一个接口实现一个实现类
        int count = userDao.add(new User(111, "小A"));
        System.out.println(count > 0 ? "新增成功" : "新增失败");
    }
}

/**
 * 方法拦截器
 */
class MyInvocationHandler implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("add")) {
            //调用目标方法的add
            System.out.println("执行ADD的参数：" + args);
            Object o = args[0];
            System.out.println(o);
            return 1;
        } else {
            return null;
        }
    }

}
