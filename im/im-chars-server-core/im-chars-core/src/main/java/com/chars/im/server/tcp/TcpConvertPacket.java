/**
 * 
 */
package com.chars.im.server.tcp;

import com.chars.im.server.ImChannelContext;
import com.chars.im.server.ImPacket;
import com.chars.im.server.ImSessionContext;
import com.chars.im.server.packets.Command;
import com.chars.im.server.protocol.IProtocolConverter;

/**
 * TCP协议消息转化包
 * @author WChao
 *
 */
public class TcpConvertPacket implements IProtocolConverter {

	/**
	 * 转TCP协议响应包;
	 */
	@Override
	public ImPacket RespPacket(byte[] body, Command command, ImChannelContext imChannelContext) {
		ImSessionContext sessionContext = imChannelContext.getSessionContext();
		if(sessionContext instanceof TcpSessionContext){
			TcpPacket tcpPacket = new TcpPacket(command,body);
			TcpServerEncoder.encode(tcpPacket, imChannelContext.getImConfig(), imChannelContext);
			tcpPacket.setCommand(command);
			return tcpPacket;
		}
		return null;
	}

	@Override
	public ImPacket RespPacket(ImPacket imPacket, Command command, ImChannelContext imChannelContext) {

		return this.RespPacket(imPacket.getBody(), command, imChannelContext);
	}

	/**
	 * 转TCP协议请求包;
	 */
	@Override
	public ImPacket ReqPacket(byte[] body, Command command, ImChannelContext channelContext) {
		Object sessionContext = channelContext.getSessionContext();
		if(sessionContext instanceof TcpSessionContext){
			TcpPacket tcpPacket = new TcpPacket(command,body);
			TcpServerEncoder.encode(tcpPacket, channelContext.getImConfig(), channelContext);
			tcpPacket.setCommand(command);
			return tcpPacket;
		}
		return null;
	}

}
