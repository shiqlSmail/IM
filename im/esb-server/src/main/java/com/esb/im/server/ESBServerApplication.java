package com.esb.im.server;

import com.alibaba.fastjson.parser.ParserConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@ServletComponentScan
@MapperScan("com.esb.im.server.dao")
@SpringBootApplication(exclude={SecurityAutoConfiguration.class,  SecurityFilterAutoConfiguration.class,DataSourceAutoConfiguration.class})
public class ESBServerApplication extends SpringBootServletInitializer {

    public static void main(String[] args) throws Exception  {
        ParserConfig.getGlobalInstance().setAsmEnable(true);
        System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(ESBServerApplication.class, args);
        //System.in.read(); // 为保证服务一直开着，利用输入流的阻塞来模拟
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ESBServerApplication.class);
    }
}
