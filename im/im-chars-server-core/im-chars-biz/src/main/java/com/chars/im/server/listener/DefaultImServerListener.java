package com.chars.im.server.listener;

import com.chars.im.server.ImChannelContext;
import com.chars.im.server.ImPacket;

/**
 * @ClassName DefaultImServerListener
 * @Description 默认IM服务端连接监听器
 * @Author WChao
 * @Date 2020/1/4 11:15
 * @Version 1.0
 **/
public class DefaultImServerListener implements ImServerListener {


    @Override
    public boolean onHeartbeatTimeout(ImChannelContext channelContext, Long interval, int heartbeatTimeoutCount) {
        return false;
    }

    @Override
    public void onAfterConnected(ImChannelContext channelContext, boolean isConnected, boolean isReconnect) throws Exception {

    }

    @Override
    public void onAfterDecoded(ImChannelContext channelContext, ImPacket packet, int packetSize) throws Exception {

    }

    @Override
    public void onAfterReceivedBytes(ImChannelContext channelContext, int receivedBytes) throws Exception {

    }

    @Override
    public void onAfterSent(ImChannelContext channelContext, ImPacket packet, boolean isSentSuccess) throws Exception {

    }

    @Override
    public void onAfterHandled(ImChannelContext channelContext, ImPacket packet, long cost) throws Exception {

    }

    @Override
    public void onBeforeClose(ImChannelContext channelContext, Throwable throwable, String remark, boolean isRemove) throws Exception {
    }
}
