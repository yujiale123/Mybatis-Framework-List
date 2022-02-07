package com.mybatis.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Data // getter setter toString
@NoArgsConstructor //生成无参构造
@AllArgsConstructor // 生成全参构造
@TableName("user")
public class User {

    private Long id;
    private String name;
    private Integer age;
    private String email;



}


