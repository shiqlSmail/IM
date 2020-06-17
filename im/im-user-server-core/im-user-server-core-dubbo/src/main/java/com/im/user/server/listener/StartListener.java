package com.im.user.server.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class StartListener implements ApplicationListener {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    public void onApplicationEvent(ApplicationEvent event) {
        if (null != event) {
            if (event.getSource() instanceof SpringApplication) {
                log.info("---- IM【 用户 】服务核心项目启动完成---");
            }
        }
    }
}

