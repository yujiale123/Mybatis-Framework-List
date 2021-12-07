package com.mybatis.mybatisplusspringboot.mapper;

import com.mybatis.mybatisplusspringboot.pojo.User;

/**
 * @program: Mybatis-Framework-List
 * @author: yjl
 * @created: 2021/12/07
 *
**/
public interface UserMapper extends MyBaseMapper<User> {


    /**
     * 自定义findById方法
     * @param id
     * @return
     */
    public User findById(Long id);


}
