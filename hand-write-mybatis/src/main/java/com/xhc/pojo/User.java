package com.xhc.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor //无参构造
@AllArgsConstructor //有参构造
public class User { //User实例类

    private Integer id;
    private String username;
}
