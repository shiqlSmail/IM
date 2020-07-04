/**
 * 
 */
package com.chars.im.server.ws;

import com.chars.im.server.ImChannelContext;
import com.chars.im.server.ImPacket;
import com.chars.im.server.ImSessionContext;
import com.chars.im.server.exception.ImException;
import com.chars.im.server.http.HttpRequest;
import com.chars.im.server.http.HttpRequestDecoder;
import com.chars.im.server.protocol.AbstractProtocol;
import com.chars.im.server.protocol.IProtocolConverter;
import com.chars.im.server.utils.ImKit;

import java.nio.ByteBuffer;

/**
 * WebSocket协议判断器
 * @author WChao
 *
 */
public class WsProtocol extends AbstractProtocol {

	@Override
	public String name() {
		return Protocol.WEB_SOCKET;
	}

	public WsProtocol(IProtocolConverter converter){
		super(converter);
	}
	
	@Override
	protected void init(ImChannelContext imChannelContext) {
		imChannelContext.setSessionContext(new WsSessionContext(imChannelContext));
		ImKit.initImClientNode(imChannelContext);
	}

	@Override
	protected boolean validateProtocol(ImSessionContext imSessionContext) throws ImException {
		if(imSessionContext instanceof WsSessionContext) {
			return true;
		}
		return false;
	}

	@Override
	protected boolean validateProtocol(ByteBuffer buffer, ImChannelContext imChannelContext) throws ImException {
		//第一次连接;
		HttpRequest request = HttpRequestDecoder.decode(buffer, imChannelContext,false);
		if(request.getHeaders().get(Http.RequestHeaderKey.Sec_WebSocket_Key) != null)
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean validateProtocol(ImPacket imPacket) throws ImException {
		if(imPacket instanceof WsPacket){
			return true;
		}
		return false;
	}

}
