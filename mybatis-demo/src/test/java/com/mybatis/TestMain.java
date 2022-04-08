package com.mybatis;

import java.util.Collections;
import java.util.HashMap;

/**
 * @program: mybatis-framework-source-code
 * @author: yjl
 * @created: 2022/03/24
 */
public class TestMain {

    public static void main(String[] args) {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        Collections.synchronizedMap(objectObjectHashMap);
//        ConcurrentHashMap<String, String>;
    }
}
