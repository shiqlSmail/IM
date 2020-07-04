package com.chars.im.server.command.handler;

import org.apache.commons.lang3.StringUtils;
import com.chars.im.server.*;
import com.chars.im.server.exception.ImException;
import com.chars.im.server.packets.Command;
import com.chars.im.server.packets.Group;
import com.chars.im.server.packets.JoinGroupRespBody;
import com.chars.im.server.packets.JoinGroupResult;
import com.chars.im.server.utils.JsonKit;
import com.chars.im.server.JimServerAPI;
import com.chars.im.server.command.AbstractCmdHandler;
import com.chars.im.server.processor.group.GroupCmdProcessor;
import com.chars.im.server.protocol.ProtocolManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * 
 * 版本: [1.0]
 * 功能说明: 加入群组消息cmd命令处理器
 * @author : WChao 创建时间: 2017年9月21日 下午3:33:23
 */
public class JoinGroupReqHandler extends AbstractCmdHandler {
	
	private static Logger log = LoggerFactory.getLogger(JoinGroupReqHandler.class);
	
	@Override
	public ImPacket handler(ImPacket packet, ImChannelContext imChannelContext) throws ImException {
		//绑定群组;
		Group joinGroup = JsonKit.toBean(packet.getBody(),Group.class);
		String groupId = joinGroup.getGroupId();
		if (StringUtils.isBlank(groupId)) {
			log.error("group is null,{}", imChannelContext);
			JimServerAPI.close(imChannelContext, "group is null when join group");
			return null;
		}
		//实际绑定之前执行处理器动作
		GroupCmdProcessor groupProcessor = this.getSingleProcessor(GroupCmdProcessor.class);
		//当有群组处理器时候才会去处理
		if(Objects.nonNull(groupProcessor)){
			JoinGroupRespBody joinGroupRespBody = groupProcessor.join(joinGroup, imChannelContext);
			boolean joinGroupIsTrue = Objects.isNull(joinGroupRespBody) || JoinGroupResult.JOIN_GROUP_RESULT_OK.getNumber() != joinGroupRespBody.getResult().getNumber();
			if (joinGroupIsTrue) {
				joinGroupRespBody = JoinGroupRespBody.failed().setData(joinGroupRespBody);
				ImPacket respPacket = ProtocolManager.Converter.respPacket(joinGroupRespBody, imChannelContext);
				return respPacket;
			}
		}
		//处理完处理器内容后
		JimServerAPI.bindGroup(imChannelContext, joinGroup);
		return null;
	}
	@Override
	public Command command() {
		
		return Command.COMMAND_JOIN_GROUP_REQ;
	}
}
