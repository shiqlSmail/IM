/**
 * 
 */
package com.chars.im.server.protocol.tcp;

import com.chars.im.server.*;
import com.chars.im.server.config.ImConfig;
import com.chars.im.server.exception.ImDecodeException;
import com.chars.im.server.exception.ImException;
import com.chars.im.server.packets.Command;
import com.chars.im.server.packets.RespBody;
import com.chars.im.server.protocol.AbstractProtocol;
import com.chars.im.server.tcp.*;
import com.chars.im.server.JimServerAPI;
import com.chars.im.server.command.AbstractCmdHandler;
import com.chars.im.server.command.CommandManager;
import com.chars.im.server.config.ImServerConfig;
import com.chars.im.server.protocol.AbstractProtocolHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.Objects;

/**
 * 版本: [1.0]
 * 功能说明: 
 * @author : WChao 创建时间: 2017年8月3日 下午7:44:48
 */
public class TcpProtocolHandler extends AbstractProtocolHandler {
	
	Logger logger = LoggerFactory.getLogger(TcpProtocolHandler.class);

	public TcpProtocolHandler(){
		this.protocol = new TcpProtocol(new TcpConvertPacket());
	}

	public TcpProtocolHandler(AbstractProtocol protocol){
		super(protocol);
	}

	@Override
	public void init(ImServerConfig imServerConfig) {
		logger.info("Socket Protocol initialized");
	}
	@Override
	public ByteBuffer encode(ImPacket imPacket, ImConfig imConfig, ImChannelContext imChannelContext) {
		TcpPacket tcpPacket = (TcpPacket)imPacket;
		return TcpServerEncoder.encode(tcpPacket, imConfig, imChannelContext);
	}

	@Override
	public void handler(ImPacket packet, ImChannelContext imChannelContext)throws ImException {
		TcpPacket tcpPacket = (TcpPacket)packet;
		AbstractCmdHandler cmdHandler = CommandManager.getCommand(tcpPacket.getCommand());
		if(cmdHandler == null){
			ImPacket imPacket = new ImPacket(Command.COMMAND_UNKNOW, new RespBody(Command.COMMAND_UNKNOW,ImStatus.C10017).toByte());
			JimServerAPI.send(imChannelContext, imPacket);
			return;
		}
		ImPacket response = cmdHandler.handler(tcpPacket, imChannelContext);
		if(Objects.nonNull(response) && tcpPacket.getSynSeq() < 1){
			JimServerAPI.send(imChannelContext, response);
		}
	}

	@Override
	public TcpPacket decode(ByteBuffer buffer, int limit, int position, int readableLength, ImChannelContext imChannelContext)throws ImDecodeException {
		TcpPacket tcpPacket = TcpServerDecoder.decode(buffer, imChannelContext);
		return tcpPacket;
	}

}
