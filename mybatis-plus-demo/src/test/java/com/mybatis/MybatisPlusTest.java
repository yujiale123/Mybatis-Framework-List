package com.mybatis;

import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.mybatis.mapper.UserMapper;
import com.mybatis.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @program: Mybatis-Framework-List
 * @author: yjl
 * @created: 2021/12/07
 */
public class MybatisPlusTest {
    @Test
    public void mybatisTest() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new MybatisSqlSessionFactoryBuilder().build(resourceAsStream);SqlSession sqlSession = sqlSessionFactory.openSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.selectList(null);
        for (User user : users) {
            System.out.println(user);
        }


    }
}
