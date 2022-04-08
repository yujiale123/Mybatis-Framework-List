package com.mybatis.mapper;

import com.mybatis.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @author yujiale
 */
public interface UserMapper {


    User selectById(@Param(value = "username") String username);

    List<User> selectAllUser();


    @Select("select * from user where name = #{username}")
    User selectByName(String username);
}

