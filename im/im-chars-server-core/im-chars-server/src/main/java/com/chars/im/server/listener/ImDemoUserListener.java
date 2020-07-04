package com.chars.im.server.listener;

import com.alibaba.fastjson.JSONObject;
import com.chars.im.server.ImChannelContext;
import com.chars.im.server.exception.ImException;
import com.chars.im.server.packets.User;
import com.chars.im.server.listener.AbstractImUserListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author WChao
 * @Desc
 * @date 2020-05-02 18:18
 */
public class ImDemoUserListener extends AbstractImUserListener {

    private static Logger logger = LoggerFactory.getLogger(ImDemoUserListener.class);

    @Override
    public void doAfterBind(ImChannelContext imChannelContext, User user) throws ImException {
        logger.info("绑定用户:{}", JSONObject.toJSONString(user));
    }

    @Override
    public void doAfterUnbind(ImChannelContext imChannelContext, User user) throws ImException {
        logger.info("解绑用户:{}",JSONObject.toJSONString(user));
    }
}
