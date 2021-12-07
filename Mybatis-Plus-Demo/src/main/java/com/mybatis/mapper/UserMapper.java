package com.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mybatis.pojo.User;

import java.util.List;

/**
 * @author yujiale
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查询所有用户
     * @return
     */
    public List<User> findAll();


    /**
     * 添加用户
     * @param user
     * @return
     */
    public int insertUser(User user);





}
