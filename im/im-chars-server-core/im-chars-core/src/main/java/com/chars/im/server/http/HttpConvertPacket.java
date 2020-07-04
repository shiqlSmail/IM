/**
 * 
 */
package com.chars.im.server.http;

import com.chars.im.server.ImChannelContext;
import com.chars.im.server.ImConst;
import com.chars.im.server.ImPacket;
import com.chars.im.server.ImSessionContext;
import com.chars.im.server.http.session.HttpSession;
import com.chars.im.server.packets.Command;
import com.chars.im.server.protocol.IProtocolConverter;

/**
 * HTTP协议消息转化包
 * @author WChao
 *
 */
public class HttpConvertPacket implements IProtocolConverter {

	/**
	 * 转HTTP协议响应包;
	 */
	@Override
	public ImPacket RespPacket(byte[] body, Command command, ImChannelContext channelContext) {
		ImSessionContext sessionContext = channelContext.getSessionContext();
		if(sessionContext instanceof HttpSession){
			HttpRequest request = (HttpRequest)channelContext.getAttribute(ImConst.HTTP_REQUEST);
			HttpResponse response = new HttpResponse(request,request.getHttpConfig());
			response.setBody(body, request);
			response.setCommand(command);
			return response;
		}
		return null;
	}

	@Override
	public ImPacket RespPacket(ImPacket imPacket, Command command, ImChannelContext imChannelContext) {
		ImSessionContext sessionContext = imChannelContext.getSessionContext();
		if(sessionContext instanceof HttpSession){
			HttpResponse response = (HttpResponse)imPacket;
			response.setCommand(command);
			return response;
		}
		return null;
	}

	@Override
	public ImPacket ReqPacket(byte[] body, Command command, ImChannelContext channelContext) {
		
		return null;
	}

}
