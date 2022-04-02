package org.apache.ibatis.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * MyBatis-config.xml配置文件  SQL语句映射
 * MappedStatement类作用是封装UserMapper.xml文件解析之后的SQL语句信息，
 * 在底层框架可以使用Dom4J进行解析！
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MappedStatement {

    /**
     * id标识  每条SQL的唯一标识
     */
    private String id;

    /**
     * SQL语句返回值
     */
    private String resultType;

    /**
     * 参数类型
     */
    private String parameterType;


    /**
     * SQl语句
     */
    private String sql;


    /**
     * SQL语句的类型
     */
    private String sqlType;

}
