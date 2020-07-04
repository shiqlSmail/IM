package com.chars.im.server.processor.group;

import com.chars.im.server.ImChannelContext;
import com.chars.im.server.packets.Group;
import com.chars.im.server.packets.JoinGroupRespBody;
import com.chars.im.server.processor.SingleProtocolCmdProcessor;

/**
 * @author ensheng
 */
public interface GroupCmdProcessor extends SingleProtocolCmdProcessor {
    /**
     * 加入群组处理
     * @param joinGroup
     * @param imChannelContext
     * @return
     */
    JoinGroupRespBody join(Group joinGroup, ImChannelContext imChannelContext);
}
