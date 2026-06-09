package com.elderly.common.config;

import com.elderly.common.interceptor.SqlInjectInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * MyBatis 配置类
 * 注册 SQL 注入检测拦截器
 */
@Configuration
@MapperScan("com.elderly.mapper")
public class MyBatisConfig {

    /**
     * 配置 SqlSessionFactory，注册 SQL 注入检测拦截器
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        
        // 注册 SQL 注入检测拦截器
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.addInterceptor(new SqlInjectInterceptor());
        sessionFactory.setConfiguration(configuration);
        
        return sessionFactory.getObject();
    }

    /**
     * SQL 注入检测拦截器 Bean
     */
    @Bean
    public SqlInjectInterceptor sqlInjectInterceptor() {
        return new SqlInjectInterceptor();
    }
}