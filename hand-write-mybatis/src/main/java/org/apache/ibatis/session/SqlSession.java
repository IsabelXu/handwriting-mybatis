package org.apache.ibatis.session;

import java.util.List;

public interface SqlSession {

    /**
     * 查询所有数据
     * @param statementId sql语句的唯一ID（通过statementId去找sql语句）
     * @param parms 查询sql需要的参数，可变参数
     * @param <E> 泛型
     * @return  返回结果集
     * @throws Exception
     */
    <E> List<E> selectList(String statementId,Object... parms) throws Exception; //...可变参数

    /**
     * 根据条件查询单个对象
     * @param statementId  sql语句的唯一ID
     * @param parms  查询sql需要的参数，可变参数
     * @param <E>
     * @return  返回对象
     * @throws Exception
     */
    <E> E selectOne(String statementId,Object... parms) throws Exception;

    /**
     * 新增
     * @param statementId sql语句的唯一ID
     * @param parms  新增的参数
     * @param <E>
     * @return
     * @throws Exception
     */
    <E> E insert(String statementId,Object... parms) throws Exception;

    /**
     * 更新
     * @param statementId sql语句的唯一ID
     * @param parms  更新的参数
     * @param <E>
     * @return
     * @throws Exception
     */
    <E> E update(String statementId,Object... parms) throws Exception;

    /**
     * 删除
     * @param statementId sql语句的唯一ID
     * @param parms  删除的参数
     * @param <E>
     * @return
     * @throws Exception
     */
    <E> E delete(String statementId,Object... parms) throws Exception;

    /**
     * 为Mapper层的接口JDK动态代理生成实现类
     * @param mapperClass  字节码
     * @param <T>  反向
     * @return 接口的代理类对象
     * @throws Exception
     */
    <T> T getMapper(Class<?> mapperClass) throws Exception;
}
