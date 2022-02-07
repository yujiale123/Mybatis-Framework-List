package com.mybatis.sqlSession;

import java.util.List;

/**
 * @author yujiale
 */
public interface SqlSession {

    /**
     * 查询所有
     * @param statementid
     * @param params
     * @param <E>
     * @return
     * @throws Exception
     */
    public <E> List<E> selectList(String statementid,Object... params) throws Exception;

    /**
     * 根据条件查询单个
     * @param statementid
     * @param params
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T selectOne(String statementid,Object... params) throws Exception;


    /**
     * 为Dao接口生成代理实现类
     * @param mapperClass
     * @param <T>
     * @return
     */
    public <T> T getMapper(Class<?> mapperClass);


}
