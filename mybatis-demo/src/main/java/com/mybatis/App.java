package com.mybatis;

import com.mybatis.entity.User;
import com.mybatis.mapper.UserMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.*;


/**
 * @author yujiale
 */
public class App {
    public static void main(String[] args) throws IOException {
        simpleMybatis();
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
                User user = (User) session.selectOne("com.mybatis.mapper.UserMapper.selectById", "nacos");
                session.commit();

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
        System.out.println("=============");
        User user2 = sqlSession.selectOne("com.mybatis.mapper.UserMapper.selectById", "nacos");
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


    /**
     * 简单定义mybatis基本流程
     */
    public static void simpleMybatis() {
        UserMapper userMapper = (UserMapper) Proxy.newProxyInstance(App.class.getClassLoader(),
                new Class[]{UserMapper.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //1、通过参数完成注解中替换，优先解析参数，并完成替换。
                        Map<String, Object> parseArgsMap = parseArgs(method, args);
                        System.out.println(parseArgsMap);
                        //获取方法上的注解
                        Select select = method.getAnnotation(Select.class);
                        if (select != null) {
                            //核心逻辑处理
                            String[] value = select.value();
                            String sql = value[0];
                            sql = parseSql(sql, parseArgsMap);
                            System.out.println(sql);
                        }
                        //数据库操作
                        //结果集的映射处理
                        return null;
                    }
                });
        userMapper.selectByName("张三");
    }

    /**
     * 方法参数解析
     *
     * @param method
     * @param args
     * @return
     */
    public static Map<String, Object> parseArgs(Method method, Object[] args) {
        HashMap<String, Object> map = new HashMap<>();
        //获取方法的参数
        Parameter[] parameters = method.getParameters();
        int index[] = {0};
        Arrays.asList(parameters).forEach(parameter -> {
            String name = parameter.getName();
            map.put(name, args[index[0]]);
            index[0]++;
        });
        return map;
    }


    /**
     *  取到#{}之间的username属性key，然后在map中取到对应值进行填充。
     *
     * @param sql
     * @param map
     * @return
     */
    public static String parseSql(String sql, Map<String, Object> map) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < sql.length(); i++) {
            char c = sql.charAt(i);
            if ('#' == c) {
                int index = i + 1;
                char nextChar = sql.charAt(index);
                if (nextChar != '{') {
                    throw new RuntimeException("sql语句错误");
                }
                StringBuilder argBuilder = new StringBuilder();
                i = parseSqlArgs(argBuilder, sql, index);
                String argName = argBuilder.toString();
                Object value = map.get(argName);
                builder.append(value.toString());
                continue;
            }
            builder.append(c);
        }
        return builder.toString();
    }

    /**
     * 将当前字符串
     * @param argBuilder
     * @param sql
     * @param index
     * @return
     */
    public static int parseSqlArgs(StringBuilder argBuilder, String sql, int index) {
        index++;
        for (; index < sql.length(); index++) {
            char c = sql.charAt(index);
            if ('}' != c) {
                argBuilder.append(c);
                continue;
            }
            if ('}' == c) {
                return index;
            }
        }
        throw new RuntimeException("sql语句错误，没有以}结尾");
    }
}
