package com.chars.im.server.command.handler;

import com.chars.im.server.ImChannelContext;
import com.chars.im.server.ImPacket;
import com.chars.im.server.config.ImConfig;
import com.chars.im.server.exception.ImException;
import com.chars.im.server.packets.ChatBody;
import com.chars.im.server.packets.ChatType;
import com.chars.im.server.packets.Command;
import com.chars.im.server.packets.RespBody;
import com.chars.im.server.ImServerChannelContext;
import com.chars.im.server.JimServerAPI;
import com.chars.im.server.command.AbstractCmdHandler;
import com.chars.im.server.config.ImServerConfig;
import com.chars.im.server.protocol.ProtocolManager;
import com.chars.im.server.queue.MsgQueueRunnable;
import com.chars.im.server.util.ChatKit;

import java.util.Objects;

/**
 * 版本: [1.0]
 * 功能说明: 聊天请求cmd消息命令处理器
 * @author : WChao 创建时间: 2017年9月22日 下午2:58:59
 */
public class ChatReqHandler extends AbstractCmdHandler {

	@Override
	public ImPacket handler(ImPacket packet, ImChannelContext channelContext) throws ImException {
		ImServerChannelContext imServerChannelContext = (ImServerChannelContext)channelContext;
		if (packet.getBody() == null) {
			throw new ImException("body is null");
		}
		ChatBody chatBody = ChatKit.toChatBody(packet.getBody(), channelContext);
		packet.setBody(chatBody.toByte());
		//聊天数据格式不正确
		if(chatBody == null || ChatType.forNumber(chatBody.getChatType()) == null){
			ImPacket respChatPacket = ProtocolManager.Packet.dataInCorrect(channelContext);
			return respChatPacket;
		}
		//异步调用业务处理消息接口
		MsgQueueRunnable msgQueueRunnable = getMsgQueueRunnable(imServerChannelContext);
		msgQueueRunnable.addMsg(chatBody);
		msgQueueRunnable.executor.execute(msgQueueRunnable);
		ImPacket chatPacket = new ImPacket(Command.COMMAND_CHAT_REQ,new RespBody(Command.COMMAND_CHAT_REQ,chatBody).toByte());
		//设置同步序列号;
		chatPacket.setSynSeq(packet.getSynSeq());
		ImServerConfig imServerConfig = ImConfig.Global.get();
		boolean isStore = ImServerConfig.ON.equals(imServerConfig.getIsStore());
		//私聊
		if(ChatType.CHAT_TYPE_PRIVATE.getNumber() == chatBody.getChatType()){
			String toId = chatBody.getTo();
			if(ChatKit.isOnline(toId, isStore)){
				JimServerAPI.sendToUser(toId, chatPacket);
				//发送成功响应包
				return ProtocolManager.Packet.success(channelContext);
			}else{
				//用户不在线响应包
				return ProtocolManager.Packet.offline(channelContext);
			}
		//群聊
		}else if(ChatType.CHAT_TYPE_PUBLIC.getNumber() == chatBody.getChatType()){
			String groupId = chatBody.getGroupId();
			JimServerAPI.sendToGroup(groupId, chatPacket);
			//发送成功响应包
			return ProtocolManager.Packet.success(channelContext);
		}
		return null;
	}

	@Override
	public Command command() {
		return Command.COMMAND_CHAT_REQ;
	}

	/**
	 * 获取聊天业务处理异步消息队列
	 * @param imServerChannelContext IM通道上下文
	 * @return
	 */
	private MsgQueueRunnable getMsgQueueRunnable(ImServerChannelContext imServerChannelContext){
		MsgQueueRunnable msgQueueRunnable = (MsgQueueRunnable)imServerChannelContext.getMsgQue();
		if(Objects.nonNull(msgQueueRunnable.getProtocolCmdProcessor())){
			return msgQueueRunnable;
		}
		synchronized (MsgQueueRunnable.class){
			msgQueueRunnable.setProtocolCmdProcessor(this.getSingleProcessor());
		}
		return msgQueueRunnable;
	}

}
