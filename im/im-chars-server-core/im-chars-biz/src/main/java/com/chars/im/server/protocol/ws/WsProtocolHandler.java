/**
 * 
 */
package com.chars.im.server.protocol.ws;

import com.chars.im.server.*;
import com.chars.im.server.config.ImConfig;
import com.chars.im.server.exception.ImDecodeException;
import com.chars.im.server.exception.ImException;
import com.chars.im.server.http.HttpRequest;
import com.chars.im.server.http.HttpRequestDecoder;
import com.chars.im.server.http.HttpResponse;
import com.chars.im.server.http.HttpResponseEncoder;
import com.chars.im.server.packets.Command;
import com.chars.im.server.packets.Message;
import com.chars.im.server.packets.RespBody;
import com.chars.im.server.protocol.AbstractProtocol;
import com.chars.im.server.utils.JsonKit;
import com.chars.im.server.ws.*;
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
 * @author : WChao 创建时间: 2017年8月3日 下午6:38:36
 */
public class WsProtocolHandler extends AbstractProtocolHandler {
	
	private Logger logger = LoggerFactory.getLogger(WsProtocolHandler.class);
	
	private WsConfig wsServerConfig;

	private IWsMsgHandler wsMsgHandler;
	
	public WsProtocolHandler() {
		this.protocol = new WsProtocol(new WsConvertPacket());
	}
	
	public WsProtocolHandler(WsConfig wsServerConfig, AbstractProtocol protocol) {
		super(protocol);
		this.wsServerConfig = wsServerConfig;
	}
	@Override
	public void init(ImServerConfig imServerConfig) {
		WsConfig wsConfig = imServerConfig.getWsConfig();
		if(Objects.isNull(wsConfig)){
			wsConfig = WsConfig.newBuilder().build();
			imServerConfig.setWsConfig(wsConfig);
		}
		IWsMsgHandler wsMsgHandler = wsConfig.getWsMsgHandler();
		if(Objects.isNull(wsMsgHandler)){
			wsConfig.setWsMsgHandler(new WsMsgHandler());
		}
		this.wsServerConfig = wsConfig;
		this.wsMsgHandler = wsServerConfig.getWsMsgHandler();
		logger.info("WebSocket Protocol  initialized");
	}

	@Override
	public ByteBuffer encode(ImPacket imPacket, ImConfig imConfig, ImChannelContext imChannelContext) {
		WsSessionContext wsSessionContext = (WsSessionContext)imChannelContext.getSessionContext();
		WsResponsePacket wsResponsePacket = (WsResponsePacket)imPacket;
		if (wsResponsePacket.getCommand() == Command.COMMAND_HANDSHAKE_RESP) {
			//握手包
			HttpResponse handshakeResponsePacket = wsSessionContext.getHandshakeResponsePacket();
			return HttpResponseEncoder.encode(handshakeResponsePacket, imChannelContext,true);
		}else{
			return WsServerEncoder.encode(wsResponsePacket , imChannelContext);
		}
	}

	@Override
	public void handler(ImPacket imPacket, ImChannelContext imChannelContext) throws ImException {
		WsRequestPacket wsRequestPacket = (WsRequestPacket) imPacket;
		AbstractCmdHandler cmdHandler = CommandManager.getCommand(wsRequestPacket.getCommand());
		if(cmdHandler == null){
			//是否ws分片发包尾帧包
			if(!wsRequestPacket.isWsEof()) {
				return;
			}
			ImPacket wsPacket = new ImPacket(Command.COMMAND_UNKNOW, new RespBody(Command.COMMAND_UNKNOW,ImStatus.C10017).toByte());
			JimServerAPI.send(imChannelContext, wsPacket);
			return;
		}
		ImPacket response = cmdHandler.handler(wsRequestPacket, imChannelContext);
		if(Objects.nonNull(response)){
			JimServerAPI.send(imChannelContext, response);
		}
	}

	@Override
	public ImPacket decode(ByteBuffer buffer, int limit, int position, int readableLength, ImChannelContext imChannelContext) throws ImDecodeException {
		WsSessionContext wsSessionContext = (WsSessionContext)imChannelContext.getSessionContext();
		//握手
		if(!wsSessionContext.isHandshaked()){
			HttpRequest  httpRequest = HttpRequestDecoder.decode(buffer,imChannelContext,true);
			if(httpRequest == null) {
				return null;
			}
			//升级到WebSocket协议处理
			HttpResponse httpResponse = WsServerDecoder.updateWebSocketProtocol(httpRequest, imChannelContext);
			if (httpResponse == null) {
				throw new ImDecodeException("http协议升级到webSocket协议失败");
			}
			wsSessionContext.setHandshakeRequestPacket(httpRequest);
			wsSessionContext.setHandshakeResponsePacket(httpResponse);

			WsRequestPacket wsRequestPacket = new WsRequestPacket();
			wsRequestPacket.setHandShake(true);
			wsRequestPacket.setCommand(Command.COMMAND_HANDSHAKE_REQ);
			return wsRequestPacket;
		}else{
			WsRequestPacket wsRequestPacket = WsServerDecoder.decode(buffer, imChannelContext);
			if(wsRequestPacket == null) {
				return null;
			}
			Command command = null;
			if(wsRequestPacket.getWsOpcode() == Opcode.CLOSE){
				command = Command.COMMAND_CLOSE_REQ;
			}else{
				try{
					Message message = JsonKit.toBean(wsRequestPacket.getBody(),Message.class);
					command = Command.forNumber(message.getCmd());
				}catch(Exception e){
					return wsRequestPacket;
				}
			}
			wsRequestPacket.setCommand(command);
			return wsRequestPacket;
		}
	}
	public WsConfig getWsServerConfig() {
		return wsServerConfig;
	}

	public void setWsServerConfig(WsConfig wsServerConfig) {
		this.wsServerConfig = wsServerConfig;
	}

	public IWsMsgHandler getWsMsgHandler() {
		return wsMsgHandler;
	}

	public void setWsMsgHandler(IWsMsgHandler wsMsgHandler) {
		this.wsMsgHandler = wsMsgHandler;
	}

}
