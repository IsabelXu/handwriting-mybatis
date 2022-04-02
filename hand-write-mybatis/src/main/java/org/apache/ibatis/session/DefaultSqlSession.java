package org.apache.ibatis.session;

import org.apache.ibatis.configuration.Configuration;
import org.apache.ibatis.configuration.MappedStatement;
import org.apache.ibatis.executor.SimpleExecutor;

import java.lang.reflect.*;
import java.util.List;
import java.util.Map;

//对SqlSession的实现
public class DefaultSqlSession implements SqlSession {

    //封装的是配置信息
    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... parms) throws Exception {
        //调用SimpleExecutorQuery方法完成查询
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = this.configuration.getMappedStatementMap().get(statementId);
        List<Object> list = simpleExecutor.query(configuration, mappedStatement, parms);
        return (List<E>) list;
    }

    @Override
    public <E> E selectOne(String statementId, Object... parms) throws Exception {
        List<Object> objects = this.selectList(statementId, parms);
        if(objects.size()==1){
            return (E) objects.get(0);
        }else if(objects.size()>1){
            throw new RuntimeException("查询的结果不唯一！");
        }else{
            throw new RuntimeException("查询的结果为空！");
        }
    }

    @Override
    public <E> E insert(String statementId, Object... parms) throws Exception {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = this.configuration.getMappedStatementMap().get(statementId);
        List<Object> list = simpleExecutor.query(configuration,mappedStatement,parms);
        if(list.size()>0){
            return (E) list.get(0);
        }else{
            return (E) "0";
        }
    }

    @Override
    public <E> E update(String statementId, Object... parms) throws Exception {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = this.configuration.getMappedStatementMap().get(statementId);
        List<Object> list = simpleExecutor.query(configuration,mappedStatement,parms);
        if(list.size()>0){
            return (E) list.get(0);
        }else{
            return (E) "0";
        }
    }

    @Override
    public <E> E delete(String statementId, Object... parms) throws Exception {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = this.configuration.getMappedStatementMap().get(statementId);
        List<Object> list = simpleExecutor.query(configuration,mappedStatement,parms);
        if(list.size()>0){
            return (E) list.get(0);
        }else{
            return (E) "0";
        }
    }

    /**
     * 使用JDK动态代理给UserMapper接口生成一个代理类对象
     * 把usermapper的接口放进去返回usermapper结果的对象
     * @param mapperClass 字节码
     * @param <T>
     * @return
     * @throws Exception
     */
    @Override
    public <T> T getMapper(Class<?> mapperClass) throws Exception {
        //使用JDK动态代理技术为Mapper接口层生成代理类对象，其实就是实现类 并返回！！！ 如果直接new一个接口，是匿名内部类写法！
        Object instance = Proxy.newProxyInstance(mapperClass.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //接口中的方法名字
                String methodName = method.getName();
                //接口的全类名
                String className = method.getDeclaringClass().getName();
                //拼接SQL的唯一标识
                String statmentId = className + "." + methodName;
                //获取方法被调用之后的返回值类型
                Type genericReturnType = method.getGenericReturnType();

                Map<String, MappedStatement> mappedStatementMap = configuration.getMappedStatementMap();
                MappedStatement mappedStatement = mappedStatementMap.get(statmentId);

                if ("insert".equals(mappedStatement.getSqlType())) {
                    return insert(statmentId, args);
                } else if ("delete".equals(mappedStatement.getSqlType())) {
                    return delete(statmentId, args);
                } else if ("update".equals(mappedStatement.getSqlType())) {
                    return update(statmentId, args);
                }
                //判断是否进行了泛型类型的参数化(返回结果是否是泛型，如果是的话查询集合)
                if (genericReturnType instanceof ParameterizedType) {
                    List<Object> objects = selectList(statmentId, args);
                    return objects;
                } else {
                    //查询的结果不是泛型（一个对象）
                    return selectOne(statmentId, args);
                }
            }
        });
        return (T) instance;
    }
}


