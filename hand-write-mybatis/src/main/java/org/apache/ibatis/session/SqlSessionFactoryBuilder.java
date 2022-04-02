package org.apache.ibatis.session;

import org.apache.ibatis.configuration.Configuration;
import org.apache.ibatis.configuration.XmlConfigBuilder;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * 生成一个SqlSessionFactory对象
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(InputStream inputStream) throws PropertyVetoException, DocumentException {
        //获取configuration对象
        Configuration configuration=new Configuration();
        XmlConfigBuilder xmlConfigBuilder=new XmlConfigBuilder(configuration);
        xmlConfigBuilder.parseMyBatisXConfig(inputStream);
        //创建SqlSessionFactory
        DefaultSqlSessionFactory sqlSessionFactory=new DefaultSqlSessionFactory(configuration);
        return sqlSessionFactory;
    }
}
