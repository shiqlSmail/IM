/**
 * 
 */
package com.chars.im.server.tcp;

import com.chars.im.server.ImChannelContext;
import com.chars.im.server.ImPacket;
import com.chars.im.server.ImSessionContext;
import com.chars.im.server.exception.ImException;
import com.chars.im.server.protocol.AbstractProtocol;
import com.chars.im.server.protocol.IProtocolConverter;
import com.chars.im.server.utils.ImKit;

import java.nio.ByteBuffer;

/**
 * @desc Tcp协议校验器
 * @author WChao
 * @date 2018-05-01
 */
public class TcpProtocol extends AbstractProtocol {

	public TcpProtocol(IProtocolConverter converter){
		super(converter);
	}

	@Override
	public String name() {
		return Protocol.TCP;
	}

	@Override
	protected void init(ImChannelContext imChannelContext) {
		imChannelContext.setSessionContext(new TcpSessionContext(imChannelContext));
		ImKit.initImClientNode(imChannelContext);
	}

	@Override
	public boolean validateProtocol(ImSessionContext imSessionContext) throws ImException {
		if(imSessionContext instanceof TcpSessionContext){
			return true;
		}
		return false;
	}

	@Override
	public boolean validateProtocol(ByteBuffer buffer, ImChannelContext imChannelContext) throws ImException {
		//获取第一个字节协议版本号,TCP协议;
		if(buffer.get() == Protocol.VERSION){
			return true;
		}
		return false;
	}

	@Override
	public boolean validateProtocol(ImPacket imPacket) throws ImException {
		if(imPacket instanceof TcpPacket){
			return true;
		}
		return false;
	}

}
