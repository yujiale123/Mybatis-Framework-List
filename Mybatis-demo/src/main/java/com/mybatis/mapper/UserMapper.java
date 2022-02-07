package com.mybatis.mapper;

import com.mybatis.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author yujiale
 */
public interface UserMapper {
    User selectById(@Param("userName") String username);

    List<User> selectAllUser();
}

