package org.apache.ibatis.executor;

import org.apache.ibatis.configuration.BoundSql;
import org.apache.ibatis.configuration.Configuration;
import org.apache.ibatis.configuration.MappedStatement;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.*;

/**
 * SQl语句执行器（对Executor接口的实现类）
 */
public class SimpleExecutor implements Executor {

    /**
     * 执行SQL核心方法(最终执行JDBC的方法)
     *
     * @param configuration
     * @param mappedStatement
     * @param parms
     * @param <E>
     * @return
     */
    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... parms) throws Exception{
        //1.获取数据库连接
        Connection connection = configuration.getDataSource().getConnection();

        //2.获取要执行的SQL语句
        // select * from user where id=#{id} 解析为 select * from user where id=?
        // select * from user where id=#{id} and username=#{username} 解析为 select * from user where id=? and username=?
        // 转换SQL语句：把#{} 转换为 ?
        String sql = mappedStatement.getSql(); //配置文件中的原始的SQL语句 select * from user where id=#{id} and username=#{username}
        //SQl语句转换
        BoundSql boundSql = this.getBoundSql(sql);
        System.out.println("要执行的SQL语句是:"+boundSql.getSqlText());
        //获取PreparedStatement
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());
        //设置参数:
        String parameterType = mappedStatement.getParameterType(); // com.xhc.pojo.User
        Class<?> parameterTypeClass = this.getClassType(parameterType);
        //获取SQL语句参数集合
        List<String> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            String content = parameterMappingList.get(i);
            if(!"java.lang.Integer".equals(parameterType)){
                //传入参数如果是对象，暴力反射
                //暴力反射
                Field declaredField = parameterTypeClass.getDeclaredField(content);
                declaredField.setAccessible(true);
                //取出参数，获得data
                Object data = declaredField.get(parms[0]);
                System.out.println("参数:"+content+"SQL语句中参数的值是:"+data);
                preparedStatement.setObject(i + 1, data);
            }else{
                //传进来的不是对象
                preparedStatement.setObject(i + 1, parms[0]);
            }
        }
        //执行SQL
        String id = mappedStatement.getId();
        ResultSet resultSet = null;

        //如果分开写DefaultSqlSession里就不用if else了
        if ("insert".equals(mappedStatement.getSqlType())||"update".equals(mappedStatement.getSqlType())||"delete".equals(mappedStatement.getSqlType())){
            //执行-----增删改
            Integer result = preparedStatement.executeUpdate();
            List<Integer> resultList = new ArrayList<Integer>();
            resultList.add(result);
            return (List<E>) resultList;
        } else {
            //执行---------查询
            resultSet = preparedStatement.executeQuery();
        }

        //获取返回值类型
        String returnType = mappedStatement.getResultType(); // com.xhc.pojo.User
        Class<?> returnTypeClass = this.getClassType(returnType);
        List<Object> objects = new ArrayList<Object>();
        //查询的结果集封装
        while (resultSet.next()) {
            //调用无参构造方法生成对象
            Object o = returnTypeClass.newInstance();
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1; i <=metaData.getColumnCount(); i++) {
                //字段名字
                String columnName = metaData.getColumnName(i);
                //获取值
                Object value = resultSet.getObject(columnName);
                //属性封装

                //使用内省根据数据库表和实体类的属性和字段对应关系数据封装
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, returnTypeClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(o, value);

                //反射给返回对象赋值
                //Field declaredField = returnTypeClass.getDeclaredField(columnName);
                //declaredField.setAccessible(true);
                //declaredField.set(o,value);
            }
            objects.add(o);
        }
        return (List<E>) objects;
    }

    /**
     * 根据类的全名称反射获取字节码Class
     *
     * @param parameterType
     * @return
     * @throws ClassNotFoundException
     */
    public Class<?> getClassType(String parameterType) throws ClassNotFoundException {
        if (parameterType != null) {
            Class<?> clazz = Class.forName(parameterType);
            return clazz;
        }
        return null;
    }


    Map<Integer, Integer> map = new TreeMap<Integer, Integer>();
    int findPosition = 0;
    //SQL语句的参数
    List<String> parameterMappings = new ArrayList<String>();

    /**
     * 完成对#{}的解析工作:
     * 1.将#{}使用？进行代替
     * 2.解析出#{}里面的值进行存储
     *
     * @param sql 原生sql
     * @return 解析后的sql  select * from user where id=#{id} and username=#{username}
     */
    private BoundSql getBoundSql(String sql) {
        this.parserSql(sql);
        Set<Map.Entry<Integer, Integer>> entries = map.entrySet();
        for (Map.Entry<Integer, Integer> entry : entries) {
            Integer key = entry.getKey() + 2;
            Integer value = entry.getValue();
            parameterMappings.add(sql.substring(key, value));
        }
        for (String s : parameterMappings) {
            sql = sql.replace("#{" + s + "}", "?");
        }
        BoundSql boundSql = new BoundSql(sql, parameterMappings);
        return boundSql;
    }

    /**
     * 递归算法  select * from user where id=#{id} and username=#{username}
     * 一个sql语句中可能有两个占位符
     * @param sql
     */
    private void parserSql(String sql) {
        int openIndex = sql.indexOf("#{", findPosition);
        if (openIndex != -1) {
            int endIndex = sql.indexOf("}", findPosition + 1);
            if (endIndex != -1) {
                map.put(openIndex, endIndex);
                findPosition = endIndex + 1;
                parserSql(sql);
            } else {
                System.out.println("SQL语句中参数错误..");
            }
        }
    }
}
