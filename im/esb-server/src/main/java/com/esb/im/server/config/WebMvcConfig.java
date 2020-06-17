package com.esb.im.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/esb").setViewName("forward:/index.htm");
    WebMvcConfigurer.super.addViewControllers(registry);
    }
}
