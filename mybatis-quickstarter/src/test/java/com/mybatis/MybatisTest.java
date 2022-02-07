package com.mybatis;

import com.mybatis.entity.User;
import com.mybatis.mapper.IUserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @program: Mybatis-Frameword-List
 * @author: yjl
 * @created: 2021/12/07
 */
public class MybatisTest {

    private SqlSession sqlSession;

    /**
     * junit会在每一个@Test方法前执行@Before方法
     *
     * @throws IOException
     */
    @Before
    public void init() throws IOException {
        //1.Resources工具类，配置文件的加载，把配置文件加载成字节输入流
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        //2.解析了配置文件，并创建了sqlSessionFactory工厂
        SqlSessionFactory build = new SqlSessionFactoryBuilder()
                .build(resourceAsStream);

        //3.生产sqlSession
        sqlSession = build.openSession();// 默认开启一个事务，但是该事务不会自动提交

    }

    /**
     * junit会在每一个@Test方法后执行@After方法
     */
    @After
    public void clear() {
        sqlSession.commit();
        sqlSession.close();
    }


    @Test
    public void test1() throws IOException {
        //在进行增删改操作时，要手动提交事务
        //4.sqlSession调用方法：查询所有selectList  查询单个：selectOne 添加：insert  修改：update 删除：delete
        List<User> users = sqlSession.selectList("user.findAll");
        for (User user : users) {
            System.out.println(user);
        }
    }


    @Test
    public void test2() throws IOException {
        User user = new User();
        user.setId(6);
        user.setUsername("tom");
        sqlSession.insert("user.saveUser", user);
    }

    @Test
    public void test3() throws IOException {
        User user = new User();
        user.setId(4);
        user.setUsername("lucy");
        sqlSession.update("user.updateUser", user);
    }

    @Test
    public void test4() throws IOException {
        sqlSession.delete("com.mybatis.mapper.IUserMapper.deleteUser", 6);
    }


    @Test
    public void test5() throws IOException {
        IUserMapper mapper = sqlSession.getMapper(IUserMapper.class);
        List<User> all = mapper.findAll();
        for (User user : all) {
            System.out.println(user);
        }


    }

    @Test
    public void test6() throws IOException {
        IUserMapper mapper = sqlSession.getMapper(IUserMapper.class);
        User user1 = new User();
        user1.setId(4);
        user1.setUsername("lucy");
        List<User> all = mapper.findByCondition(user1);
        for (User user : all) {
            System.out.println(user);
        }


    }

    @Test
    public void test7() throws IOException {
        IUserMapper mapper = sqlSession.getMapper(IUserMapper.class);
        int[] arr = {1, 2};
        List<User> all = mapper.findByIds(arr);
        for (User user : all) {
            System.out.println(user);
        }
    }

}
