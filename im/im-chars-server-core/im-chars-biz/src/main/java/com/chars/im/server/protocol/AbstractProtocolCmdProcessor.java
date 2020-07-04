package com.chars.im.server.protocol;

import com.chars.im.server.ImChannelContext;
import com.chars.im.server.packets.Message;
import com.chars.im.server.processor.ProtocolCmdProcessor;

/**
 * @author WChao
 * @Desc
 * @date 2020-05-02 16:23
 */
public abstract class AbstractProtocolCmdProcessor implements ProtocolCmdProcessor {

    @Override
    public void process(ImChannelContext imChannelContext, Message message) {

    }
}
