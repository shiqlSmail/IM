/**
 * 
 */
package com.chars.im.server.ws;

import com.chars.im.server.ImChannelContext;
import com.chars.im.server.ImPacket;
import com.chars.im.server.ImSessionContext;
import com.chars.im.server.packets.Command;
import com.chars.im.server.protocol.IProtocolConverter;

/**
 * Ws协议消息转化包
 * @author WChao
 *
 */
public class WsConvertPacket implements IProtocolConverter {

	/**
	 * WebSocket响应包;
	 */
	@Override
	public ImPacket RespPacket(byte[] body, Command command, ImChannelContext channelContext) {
		ImSessionContext sessionContext = channelContext.getSessionContext();
		//转ws协议响应包;
		if(sessionContext instanceof WsSessionContext){
			WsResponsePacket wsResponsePacket = new WsResponsePacket();
			wsResponsePacket.setBody(body);
			wsResponsePacket.setWsOpcode(Opcode.TEXT);
			wsResponsePacket.setCommand(command);
			return wsResponsePacket;
		}
		return null;
	}

	@Override
	public ImPacket RespPacket(ImPacket imPacket, Command command, ImChannelContext imChannelContext) {

		return this.RespPacket(imPacket.getBody(), command, imChannelContext);
	}

	@Override
	public ImPacket ReqPacket(byte[] body, Command command, ImChannelContext channelContext) {
		
		return null;
	}
}
