package com.xhc.test;

import org.apache.ibatis.configuration.Configuration;
import org.apache.ibatis.configuration.XmlConfigBuilder;
import org.apache.ibatis.io.Resources;
import org.dom4j.DocumentException;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.io.InputStream;

public class TestXmlConfigBuilder {

    @Test
    public void test1(){
        try {
            Configuration configuration=new Configuration();
            XmlConfigBuilder xmlConfigBuilder=new XmlConfigBuilder(configuration);
            InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
            xmlConfigBuilder.parseMyBatisXConfig(inputStream);
            System.out.println("mybatis-config.xml解析完毕");
            System.out.println(configuration);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        } finally {
        }
    }
}
