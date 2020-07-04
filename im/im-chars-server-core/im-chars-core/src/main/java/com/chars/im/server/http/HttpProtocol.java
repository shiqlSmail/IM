/**
 * 
 */
package com.chars.im.server.http;

import com.chars.im.server.ImChannelContext;
import com.chars.im.server.ImPacket;
import com.chars.im.server.ImSessionContext;
import com.chars.im.server.exception.ImException;
import com.chars.im.server.http.session.HttpSession;
import com.chars.im.server.protocol.AbstractProtocol;
import com.chars.im.server.protocol.IProtocolConverter;
import com.chars.im.server.utils.ImKit;

import java.nio.ByteBuffer;

/**
 *
 * @desc Http协议校验器
 * @author WChao
 * @date 2018-05-01
 */
public class HttpProtocol extends AbstractProtocol {

	@Override
	public String name() {
		return Protocol.HTTP;
	}

	public HttpProtocol(IProtocolConverter protocolConverter){
		super(protocolConverter);
	}

	@Override
	protected void init(ImChannelContext imChannelContext) {
		imChannelContext.setSessionContext(new HttpSession(imChannelContext));
		ImKit.initImClientNode(imChannelContext);
	}

	@Override
	public boolean validateProtocol(ImSessionContext imSessionContext) throws ImException {
		if(imSessionContext instanceof HttpSession) {
			return true;
		}
		return false;
	}

	@Override
	public boolean validateProtocol(ByteBuffer buffer, ImChannelContext imChannelContext) throws ImException {
		HttpRequest request = HttpRequestDecoder.decode(buffer, imChannelContext,false);
		if(request.getHeaders().get(Http.RequestHeaderKey.Sec_WebSocket_Key) == null)
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean validateProtocol(ImPacket imPacket) throws ImException {
		if(imPacket instanceof HttpPacket){
			return true;
		}
		return false;
	}

}
