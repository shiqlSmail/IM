package com.chars.im.server.command.handler;

import com.chars.im.server.ImChannelContext;
import com.chars.im.server.ImConst;
import com.chars.im.server.ImPacket;
import com.chars.im.server.ImStatus;
import com.chars.im.server.exception.ImException;
import com.chars.im.server.packets.AuthReqBody;
import com.chars.im.server.packets.Command;
import com.chars.im.server.packets.RespBody;
import com.chars.im.server.utils.JsonKit;
import com.chars.im.server.command.AbstractCmdHandler;
import com.chars.im.server.protocol.ProtocolManager;

/**
 * 
 * 版本: [1.0]
 * 功能说明: 鉴权请求消息命令处理器
 * @author : WChao 创建时间: 2017年9月13日 下午1:39:35
 */
public class AuthReqHandler extends AbstractCmdHandler
{
	@Override
	public ImPacket handler(ImPacket packet, ImChannelContext channelContext) throws ImException
	{
		if (packet.getBody() == null) {
			RespBody respBody = new RespBody(Command.COMMAND_AUTH_RESP,ImStatus.C10010);
			return ProtocolManager.Converter.respPacket(respBody, channelContext);
		}
		AuthReqBody authReqBody = JsonKit.toBean(packet.getBody(), AuthReqBody.class);
		String token = authReqBody.getToken() == null ? "" : authReqBody.getToken();
		String data = token +  ImConst.AUTH_KEY;
		authReqBody.setToken(data);
		authReqBody.setCmd(null);
		RespBody respBody = new RespBody(Command.COMMAND_AUTH_RESP,ImStatus.C10009).setData(authReqBody);
		return ProtocolManager.Converter.respPacket(respBody, channelContext);
	}

	@Override
	public Command command() {
		return Command.COMMAND_AUTH_REQ;
	}
}
