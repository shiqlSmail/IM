/**
 * 
 */
package com.chars.im.server;

import com.chars.im.server.command.CommandManager;
import com.chars.im.server.command.DemoWsHandshakeProcessor;
import com.chars.im.server.command.handler.ChatReqHandler;
import com.chars.im.server.command.handler.HandshakeReqHandler;
import com.chars.im.server.command.handler.LoginReqHandler;
import com.chars.im.server.config.ImServerConfig;
import com.chars.im.server.config.PropertyImServerConfigBuilder;
import com.chars.im.server.listener.ImDemoGroupListener;
import com.chars.im.server.listener.ImDemoUserListener;
import com.chars.im.server.packets.Command;
import com.chars.im.server.processor.chat.DefaultAsyncChatMessageProcessor;
import com.chars.im.server.service.LoginServiceProcessor;
import com.chars.im.server.utils.PropUtil;
import org.apache.commons.lang3.StringUtils;
import org.tio.core.ssl.SslConfig;

/**
 * IM服务端DEMO演示启动类;
 * @author WChao
 * @date 2018-04-05 23:50:25
 */
public class ImServerDemoStart {

	public static void main(String[] args)throws Exception{
		ImServerConfig imServerConfig = new PropertyImServerConfigBuilder("config/jim.properties").build();
		//初始化SSL;(开启SSL之前,你要保证你有SSL证书哦...)
		initSsl(imServerConfig);
		//设置群组监听器，非必须，根据需要自己选择性实现;
		imServerConfig.setImGroupListener(new ImDemoGroupListener());
		//设置绑定用户监听器，非必须，根据需要自己选择性实现;
		imServerConfig.setImUserListener(new ImDemoUserListener());
		JimServer jimServer = new JimServer(imServerConfig);

		/*****************start 以下处理器根据业务需要自行添加与扩展，每个Command都可以添加扩展,此处为demo中处理**********************************/

		HandshakeReqHandler handshakeReqHandler = CommandManager.getCommand(Command.COMMAND_HANDSHAKE_REQ, HandshakeReqHandler.class);
		//添加自定义握手处理器;
		handshakeReqHandler.addMultiProtocolProcessor(new DemoWsHandshakeProcessor());
		LoginReqHandler loginReqHandler = CommandManager.getCommand(Command.COMMAND_LOGIN_REQ,LoginReqHandler.class);
		//添加登录业务处理器;
		loginReqHandler.setSingleProcessor(new LoginServiceProcessor());
		//添加用户业务聊天记录处理器，用户自己继承抽象类BaseAsyncChatMessageProcessor即可，以下为内置默认的处理器！
		ChatReqHandler chatReqHandler = CommandManager.getCommand(Command.COMMAND_CHAT_REQ, ChatReqHandler.class);
		chatReqHandler.setSingleProcessor(new DefaultAsyncChatMessageProcessor());
		/*****************end *******************************************************************************************/
		jimServer.start();
	}

	/**
	 * 开启SSL之前，你要保证你有SSL证书哦！
	 * @param imServerConfig
	 * @throws Exception
	 */
	private static void initSsl(ImServerConfig imServerConfig) throws Exception {
		//开启SSL
		if(ImServerConfig.ON.equals(imServerConfig.getIsSSL())){
			String keyStorePath = PropUtil.get("jim.key.store.path");
			String keyStoreFile = keyStorePath;
			String trustStoreFile = keyStorePath;
			String keyStorePwd = PropUtil.get("jim.key.store.pwd");
			if (StringUtils.isNotBlank(keyStoreFile) && StringUtils.isNotBlank(trustStoreFile)) {
				SslConfig sslConfig = SslConfig.forServer(keyStoreFile, trustStoreFile, keyStorePwd);
				imServerConfig.setSslConfig(sslConfig);
			}
		}
	}

}
