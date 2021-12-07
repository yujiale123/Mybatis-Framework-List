package com.mybatis.mapper;

import com.mybatis.entity.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author yujiale
 */
public interface IRoleMapper {


    @Select("select * from sys_role r,sys_user_role ur where r.id = ur.roleid and ur.userid = #{uid}")
    public List<Role> findRoleByUid(Integer uid);
}
