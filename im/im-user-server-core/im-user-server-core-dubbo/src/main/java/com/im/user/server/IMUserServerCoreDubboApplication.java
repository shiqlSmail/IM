package com.im.user.server;

import com.github.pagehelper.PageHelper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@ImportResource(locations = {"classpath:druid-bean.xml"})
@EnableTransactionManagement(order = 2)	//设置事务执行顺序(需要在切换数据源之后，否则只走主库)
@MapperScan(basePackages = "com.im.user.server.repository")
//@EnableScheduling //开启定时任务
public class IMUserServerCoreDubboApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(IMUserServerCoreDubboApplication.class, args);
        //System.in.read(); // 为保证服务一直开着，利用输入流的阻塞来模拟
    }

    //配置mybatis的分页插件pageHelper
    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("reasonable", "true");
        properties.setProperty("dialect", "mysql");    //配置mysql数据库的方言
        pageHelper.setProperties(properties);
        return pageHelper;
    }
}
