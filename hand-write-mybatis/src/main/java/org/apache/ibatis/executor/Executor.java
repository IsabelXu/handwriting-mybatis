package org.apache.ibatis.executor;

import org.apache.ibatis.configuration.Configuration;
import org.apache.ibatis.configuration.MappedStatement;

import java.sql.SQLException;
import java.util.List;

/**
 * SQl语句执行器 Executor执行器接口
 */
public interface Executor {

    /**
     * SQL语句执行器
     * @param configuration
     * @param mappedStatement
     * @param parms
     * @param <E>
     * @return
     */
    <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... parms) throws SQLException, Exception;
}
