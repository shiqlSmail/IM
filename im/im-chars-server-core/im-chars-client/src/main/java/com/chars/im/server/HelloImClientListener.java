package com.chars.im.server;


import com.chars.im.server.listener.ImClientListener;

/**
 * @author WChao
 * @Desc
 * @date 2020-05-04 07:33
 */
public class HelloImClientListener implements ImClientListener {
    @Override
    public void onAfterConnected(ImChannelContext imChannelContext, boolean isConnected, boolean isReconnect) throws Exception {

    }

    @Override
    public void onAfterDecoded(ImChannelContext imChannelContext, ImPacket packet, int packetSize) throws Exception {

    }

    @Override
    public void onAfterReceivedBytes(ImChannelContext imChannelContext, int receivedBytes) throws Exception {

    }

    @Override
    public void onAfterSent(ImChannelContext imChannelContext, ImPacket packet, boolean isSentSuccess) throws Exception {

    }

    @Override
    public void onAfterHandled(ImChannelContext imChannelContext, ImPacket packet, long cost) throws Exception {

    }

    @Override
    public void onBeforeClose(ImChannelContext imChannelContext, Throwable throwable, String remark, boolean isRemove) throws Exception {

    }

}
