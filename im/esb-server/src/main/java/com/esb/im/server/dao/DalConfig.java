/**
 * pajk.com Inc.
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.esb.im.server.dao;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * @program: laundry
 * @description: mybatis配置
 * @author: shiqilong / jackSmail
 * @create: 2020-02-04 21:51
 */
@Configuration
@AutoConfigureAfter({DataSourceConfig.class})
@MapperScan(value = "com.ilonw.api.dao", sqlSessionFactoryRef = "sqlSessionFactory")
@EnableTransactionManagement
public class DalConfig {
    @Autowired
    @Qualifier("DataSource")
    public DruidDataSource druidDataSource;
    @Value(value = "classpath:mapper/*.xml")
    private Resource[] mapperLocations;
    @Bean(autowire = Autowire.BY_NAME)
    public TransactionTemplate transactionTemplate() {
        TransactionTemplate template = new TransactionTemplate();
        return template;
    }
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
    @Bean(autowire = Autowire.BY_NAME)
    public SqlSessionFactoryBean sqlSessionFactory() {
        SqlSessionFactoryBean ssfb = new SqlSessionFactoryBean();
        ssfb.setDataSource(druidDataSource);
        ssfb.setMapperLocations(mapperLocations);
        ssfb.setTypeAliasesPackage("com.ilonw.api.domain");
        return ssfb;
    }
}
