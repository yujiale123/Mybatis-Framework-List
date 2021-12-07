package com.mybatis.mybatisplusspringboot.config;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.mybatis.mybatisplusspringboot.Injector.MySqlInjector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: Mybatis-Framework-List
 * @author: yjl
 * @created: 2021/12/07
 *
 * 插件集合
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 分页插件
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 性能分析插件
     */
    @Bean
    public PerformanceInterceptor performanceInterceptor() {

        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        // 设置sql语句的最大执行时间
        performanceInterceptor.setMaxTime(100);
        // 设置sql是否格式化显示
        performanceInterceptor.setFormat(true);

        return performanceInterceptor;
    }

    /**
     * 乐观锁插件
     *
     * @return
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {

        return new OptimisticLockerInterceptor();
    }

    /**
     * 自定义的sql注入器
     *
     * @return
     */
    @Bean
    public MySqlInjector mySqlInjector() {
        return new MySqlInjector();
    }


}
