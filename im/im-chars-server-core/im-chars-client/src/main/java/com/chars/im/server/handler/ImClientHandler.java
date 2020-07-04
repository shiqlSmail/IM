package com.chars.im.server.handler;


import com.chars.im.server.ImChannelContext;
import com.chars.im.server.ImHandler;
import com.chars.im.server.ImPacket;

/**
 *
 * 客户端回调
 * @author WChao 
 *
 */
public interface ImClientHandler extends ImHandler {
    /**
     * 心跳包接口
     * @param imChannelContext
     * @return
     */
    ImPacket heartbeatPacket(ImChannelContext imChannelContext);
}
