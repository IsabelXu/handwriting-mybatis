package org.apache.ibatis.io;

import java.io.InputStream;

public class Resources {

    /**
     * 根据配置文件的路径，将配置文件加载为字节流形式，存贮在内存中
     * @param path 配置文件的位置
     * @return 返回的字节流
     */
    public static InputStream getResourceAsStream(String path){
        /**
         * 加载类路径下的配置文件，以字节流的形式返回
         */
        //getClassLoader()类加载器
        InputStream inputStream = Resources.class.getClassLoader().getResourceAsStream(path);
        return inputStream;
    }
}
