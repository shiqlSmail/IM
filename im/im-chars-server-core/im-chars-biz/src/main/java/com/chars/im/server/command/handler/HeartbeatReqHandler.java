package com.chars.im.server.command.handler;

import com.chars.im.server.ImChannelContext;
import com.chars.im.server.ImPacket;
import com.chars.im.server.exception.ImException;
import com.chars.im.server.packets.Command;
import com.chars.im.server.packets.HeartbeatBody;
import com.chars.im.server.packets.RespBody;
import com.chars.im.server.command.AbstractCmdHandler;
import com.chars.im.server.protocol.ProtocolManager;

/**
 *
 */
public class HeartbeatReqHandler extends AbstractCmdHandler
{
	@Override
	public ImPacket handler(ImPacket packet, ImChannelContext channelContext) throws ImException
	{
		RespBody heartbeatBody = new RespBody(Command.COMMAND_HEARTBEAT_REQ).setData(new HeartbeatBody(Protocol.HEARTBEAT_BYTE));
		ImPacket heartbeatPacket = ProtocolManager.Converter.respPacket(heartbeatBody,channelContext);
		return heartbeatPacket;
	}

	@Override
	public Command command() {
		return Command.COMMAND_HEARTBEAT_REQ;
	}
}
