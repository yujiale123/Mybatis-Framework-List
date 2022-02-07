package com.mybatis.sqlSession;


import com.mybatis.pojo.Configuration;
import com.mybatis.pojo.MappedStatement;

import java.util.List;

/**
 * @author yujiale
 */
public interface Executor {

    /**
     * 查询操作
     * @param configuration
     * @param mappedStatement
     * @param params
     * @param <E>
     * @return
     * @throws Exception
     */
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception;

}
