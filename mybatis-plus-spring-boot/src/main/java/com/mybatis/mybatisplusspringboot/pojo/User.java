package com.mybatis.mybatisplusspringboot.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: Mybatis-Framework-List
 * @author: yjl
 * @created: 2021/12/07
 */
@Data // getter setter toString
@NoArgsConstructor //生成无参构造
@AllArgsConstructor // 生成全参构造

public class User extends Model<User> {

    //@TableId(type = IdType.AUTO)
    private Long id;
    //查询的时候，不返回该字段的值
    @TableField(select = true)
    private String name;
    private Integer age;
    // 解决字段名不一致问题
    @TableField(value = "email")
    private String mail;
    // 该字段在数据库表中不存在
    @TableField(exist = false)
    private String address;

    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer version;

    @TableLogic
    private Integer deleted;


}
