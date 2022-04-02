package org.apache.ibatis.session;

/**
 * SqlSessionFactory
 */
public interface SqlSessionFactory {

    //获取SqlSession
    SqlSession openSession();
}
