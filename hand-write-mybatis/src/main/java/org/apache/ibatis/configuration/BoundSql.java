package org.apache.ibatis.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

//对SQL语句的封装
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoundSql {

    //要执行的SQL语句(转换完毕之后的SQL语句)
    private String sqlText;

    //执行SQL语句参数的集合
    private List<String> parameterMappingList = new ArrayList<String>();
}
