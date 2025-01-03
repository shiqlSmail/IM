package com.chars.im.server.command.handler;

import com.chars.im.server.ImChannelContext;
import com.chars.im.server.ImPacket;
import com.chars.im.server.exception.ImException;
import com.chars.im.server.http.HttpRequest;
import com.chars.im.server.packets.Command;
import com.chars.im.server.ws.WsSessionContext;
import com.chars.im.server.JimServerAPI;
import com.chars.im.server.command.AbstractCmdHandler;
import com.chars.im.server.processor.handshake.HandshakeCmdProcessor;

import java.util.Objects;

/**
 * 版本: [1.0]
 * 功能说明: 心跳cmd命令处理器
 * @author : WChao 创建时间: 2017年9月21日 下午3:33:23
 */
public class HandshakeReqHandler extends AbstractCmdHandler {
	
	@Override
	public ImPacket handler(ImPacket packet, ImChannelContext channelContext) throws ImException {

		HandshakeCmdProcessor handshakeProcessor = this.getMultiProcessor(channelContext,HandshakeCmdProcessor.class);
		if(Objects.isNull(handshakeProcessor)){
			JimServerAPI.remove(channelContext, "没有对应的握手协议处理器HandshakeCmdProcessor...");
			return null;
		}
		ImPacket handShakePacket = handshakeProcessor.handshake(packet, channelContext);
		if (handShakePacket == null) {
			JimServerAPI.remove(channelContext, "业务层不同意握手");
			return null;
		}
		JimServerAPI.send(channelContext, handShakePacket);
		WsSessionContext wsSessionContext = (WsSessionContext) channelContext.getSessionContext();
		HttpRequest request = wsSessionContext.getHandshakeRequestPacket();
		handshakeProcessor.onAfterHandshake(request, channelContext);
		return null;
	}

	@Override
	public Command command() {
		return Command.COMMAND_HANDSHAKE_REQ;
	}
}
