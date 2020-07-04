package com.chars.im.server.processor;

import com.chars.im.server.ImChannelContext;
import com.chars.im.server.ImConst;
import com.chars.im.server.packets.Message;

/**
 * @author WChao
 * @Desc 不同协议CMD命令处理器接口
 * @date 2020-05-02 14:31
 */
public interface ProtocolCmdProcessor extends ImConst {
    /**
     * cmd命令处理器方法
     * @param imChannelContext IM通道上下文
     * @param message 消息
     */
    void process(ImChannelContext imChannelContext, Message message);

}
