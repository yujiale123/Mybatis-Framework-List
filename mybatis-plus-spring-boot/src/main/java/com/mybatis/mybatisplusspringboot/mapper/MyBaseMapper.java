package com.mybatis.mybatisplusspringboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mybatis.mybatisplusspringboot.pojo.User;

import java.util.List;


/**
 * @program: Mybatis-Framework-List
 * @author: yjl
 * @created: 2021/12/07
 * <p>
 * 通用mapper接口，以后创建其他mapper接口时，不再继承BaseMapper，
 * 而是继承MyBaseMapper
 */
public interface MyBaseMapper<T> extends BaseMapper<T> {

    /**
     * 查询所有用户
     *
     * @return
     */
    public List<User> findAll();


}
