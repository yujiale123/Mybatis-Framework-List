package com.mybatis.mybatisplusspringboot.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * @program: Mybatis-Framework-List
 * @author: yjl
 * @created: 2021/12/07
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {

        Object version = getFieldValByName("version", metaObject);
        if (null == version) {
            // 该属性为空，可以进行填充
            setFieldValByName("version", 1, metaObject);
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
