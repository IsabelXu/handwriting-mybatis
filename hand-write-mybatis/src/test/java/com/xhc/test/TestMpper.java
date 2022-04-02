package com.xhc.test;

import org.apache.ibatis.configuration.Configuration;
import org.apache.ibatis.configuration.XmlMapperBuilder;
import org.apache.ibatis.io.Resources;
import org.dom4j.DocumentException;
import org.junit.Test;

import java.io.InputStream;

public class TestMpper {

    @Test
    public void test1(){
        try {
            //MyBatis配置类
            Configuration configuration=new Configuration();
            InputStream inputstream = Resources.getResourceAsStream("mappers/UserMapper.xml");
            System.out.println(inputstream);
            XmlMapperBuilder xmlMapperBuilder=new XmlMapperBuilder(configuration);
            xmlMapperBuilder.parse(inputstream);
            System.out.println("UserMapper.xml解析完成");
            System.out.println(configuration);
        } catch (
    DocumentException e) {
            e.printStackTrace();
        } finally {
        }
    }
}
