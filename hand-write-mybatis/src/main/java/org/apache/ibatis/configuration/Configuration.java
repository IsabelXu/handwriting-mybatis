package org.apache.ibatis.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Configuration类是MyBatis框架的SQL配置封装类，后期使用Dom4J进行解析
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Configuration {


    /**
     * 数据源(mybatis-config.xml里的datasource
     */
    private DataSource dataSource;


    /**
     * 封装的UserMaper.xml文件中的SQL语句，因为maper.xml文件中不止一条SQL
     */
    //namespace的名字+id作为key(String)
    Map<String,MappedStatement> mappedStatementMap=new ConcurrentHashMap<String,MappedStatement>();

}
