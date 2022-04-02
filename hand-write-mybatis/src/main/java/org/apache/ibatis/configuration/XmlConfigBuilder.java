package org.apache.ibatis.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.ibatis.io.Resources;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * 解析MyBatis的核心配置文件 mybatis-config.xml
 */
public class XmlConfigBuilder {

    private Configuration  configuration;

    public XmlConfigBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * 解析MyBatis-config配置文件
     * @return
     */
    public Configuration parseMyBatisXConfig(InputStream inputStream) throws DocumentException, PropertyVetoException {
        SAXReader saxReader=new SAXReader();
        Document document = saxReader.read(inputStream);
        //根节点————configuration 节点
        Element rootElement = document.getRootElement();
        List<Element> propertyList = rootElement.selectNodes("//property");
        //utils里面的工具类
        Properties properties=new Properties();
        for (Element element : propertyList) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.setProperty(name,value);
        }
        //初始化一个数据库连接池（封装datasource，用的c3p0）
        ComboPooledDataSource comboPooledDataSource=new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties.getProperty("driverClass"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl")); //setJdbcUrl连接字符串
        comboPooledDataSource.setUser(properties.getProperty("username"));
        comboPooledDataSource.setPassword(properties.getProperty("password"));
        //设置数据库数据源
        configuration.setDataSource(comboPooledDataSource);

        //MyBatis的核心配置文件中，映射XxxxMapper.xml文件
        List<Element> mapperList = rootElement.selectNodes("//mapper"); //可能项目中有多个mapper文件,所以是mapperList
        for (Element element : mapperList) {
            //得到了mappers/UserMapper.xml这个信息
            String resource = element.attributeValue("resource");
            InputStream resourceAsStream = Resources.getResourceAsStream(resource);
            XmlMapperBuilder xmlMapperBuilder=new XmlMapperBuilder(configuration);
            xmlMapperBuilder.parse(resourceAsStream);
        }
        return configuration;
    }

}
