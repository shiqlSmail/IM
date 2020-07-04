/**
 * 
 */
package com.chars.im.server.protocol;

import com.chars.im.server.ImChannelContext;
import com.chars.im.server.ImPacket;
import com.chars.im.server.packets.Command;

/**
 * 转换不同协议消息包;
 * @author WChao
 *
 */
public interface IProtocolConverter {
	/**
	 * 转化请求包
	 * @param body
	 * @param command
	 * @param imChannelContext
	 * @return
	 */
	ImPacket ReqPacket(byte[] body, Command command, ImChannelContext imChannelContext);
	/**
	 * 转化响应包
	 * @param body
	 * @param command
	 * @param imChannelContext
	 * @return
	 */
	ImPacket RespPacket(byte[] body, Command command, ImChannelContext imChannelContext);

	/**
	 * 转化响应包
	 * @param imPacket
	 * @param command
	 * @param imChannelContext
	 * @return
	 */
	ImPacket RespPacket(ImPacket imPacket, Command command, ImChannelContext imChannelContext);
}
