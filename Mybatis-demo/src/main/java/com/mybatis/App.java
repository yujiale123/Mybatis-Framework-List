package com.mybatis;

import com.mybatis.entity.User;
import com.mybatis.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;


/**
 * @author yujiale
 */
public class App {
    public static void main(String[] args) throws IOException {
        test1();
    }

    public static void test0() {
        String resource = "mybatis-config.xml";
        Reader reader;
        try {
            //将XML配置文件构建为Configuration配置类
            reader = Resources.getResourceAsReader(resource);
            // 通过加载配置文件流构建一个SqlSessionFactory  DefaultSqlSessionFactory
            SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);
            // 数据源 执行器  DefaultSqlSession
            SqlSession session = sqlMapper.openSession();
            try {
                // 执行查询 底层执行jdbc
                User user = (User) session.selectOne("com.mybatis.mapper.UserMapper.selectById", "");
                session.commit();
              //  System.out.println(user.getUsername());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                session.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 传统方式
     *
     * @throws IOException
     */
    public static void test1() throws IOException {
        // 1. 读取配置文件，读成字节输入流，注意：现在还没解析
        InputStream resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");

        // 2. 解析配置文件，封装Configuration对象   创建DefaultSqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        // 3. 生产了DefaultSqlSession实例对象   设置了事务不自动提交  完成了executor对象的创建
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 4.(1)根据statementId来从Configuration中map集合中获取到了指定的MappedStatement对象
        //(2)将查询任务委派了executor执行器
        User user = sqlSession.selectOne("com.mybatis.mapper.UserMapper.selectById", "nacos");
        System.out.println(user);
        User user2 = sqlSession.selectOne("com.mybatis.mapper.UserMapper.selectById", "");
        System.out.println(user2);
        // 5.释放资源
        sqlSession.close();

    }

    /**
     * mapper代理方式
     */

    public static void test2() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = factory.openSession();
        // 使用JDK动态代理对mapper接口产生代理对象
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        //代理对象调用接口中的任意方法，执行的都是动态代理中的invoke方法
        List<User> all = mapper.selectAllUser();
        for (User user : all) {
            System.out.println(user);
        }
    }
}
