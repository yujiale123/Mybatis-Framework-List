package com.mybatis.mapper;

import com.mybatis.entity.User;
import java.util.List;


/**
 * @author yujiale
 */
public interface UserMapper {
    User selectById(Long id);

    List<User> selectAllUser();
}
