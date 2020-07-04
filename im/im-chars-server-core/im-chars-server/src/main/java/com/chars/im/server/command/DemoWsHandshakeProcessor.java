/**
 * 
 */
package com.chars.im.server.command;

import com.chars.im.server.ImChannelContext;
import com.chars.im.server.ImConst;
import com.chars.im.server.ImPacket;
import com.chars.im.server.exception.ImException;
import com.chars.im.server.http.HttpRequest;
import com.chars.im.server.packets.Command;
import com.chars.im.server.packets.LoginReqBody;
import com.chars.im.server.utils.JsonKit;
import com.chars.im.server.JimServerAPI;
import com.chars.im.server.command.CommandManager;
import com.chars.im.server.command.handler.LoginReqHandler;
import com.chars.im.server.processor.handshake.WsHandshakeProcessor;

/**
 * @author WChao
 *
 */
public class DemoWsHandshakeProcessor extends WsHandshakeProcessor {

	@Override
	public void onAfterHandshake(ImPacket packet, ImChannelContext imChannelContext) throws ImException {
		LoginReqHandler loginHandler = (LoginReqHandler)CommandManager.getCommand(Command.COMMAND_LOGIN_REQ);
		HttpRequest request = (HttpRequest)packet;
		String username = request.getParams().get("username") == null ? null : (String)request.getParams().get("username")[0];
		String password = request.getParams().get("password") == null ? null : (String)request.getParams().get("password")[0];
		String token = request.getParams().get("token") == null ? null : (String)request.getParams().get("token")[0];
		LoginReqBody loginBody = new LoginReqBody(username,password,token);
		byte[] loginBytes = JsonKit.toJsonBytes(loginBody);
		request.setBody(loginBytes);
		try{
			request.setBodyString(new String(loginBytes, ImConst.CHARSET));
		}catch (Exception e){
			throw new ImException(e);
		}
		ImPacket loginRespPacket = loginHandler.handler(request, imChannelContext);
		if(loginRespPacket != null){
			JimServerAPI.send(imChannelContext, loginRespPacket);
		}
	}
}
