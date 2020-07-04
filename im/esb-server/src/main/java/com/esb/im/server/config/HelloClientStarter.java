package com.esb.im.server.config;

import com.alibaba.fastjson.JSON;
import com.chars.im.server.*;
import com.chars.im.server.config.ImClientConfig;
import com.chars.im.server.packets.ChatBody;
import com.chars.im.server.packets.ChatType;
import com.chars.im.server.packets.Command;
import com.chars.im.server.tcp.TcpPacket;
import com.esb.im.server.request.IMCharsParam;
import com.esb.im.server.system.InterfaceBean;
import org.tio.core.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * 版本: [1.0]
 * 功能说明:
 * 作者: WChao 创建时间: 2017年8月30日 下午1:05:17
 */

public class HelloClientStarter{

    public static ImClientChannelContext imClientChannelContext = null;

    /**
     * 启动程序入口
     */
    public static String sendChars(IMCharsParam imCharsParam) throws Exception {
        //服务器节点
        Node serverNode = new Node("127.0.0.1", ImConst.SERVER_PORT);
        //构建客户端配置信息
        ImClientConfig imClientConfig = ImClientConfig.newBuilder()
                //客户端业务回调器,不可以为NULL
                .clientHandler(new HelloImClientHandler())
                //客户端事件监听器，可以为null，但建议自己实现该接口
                .clientListener(new HelloImClientListener())
                //心跳时长不设置，就不发送心跳包
                //.heartbeatTimeout(5000)
                //断链后自动连接的，不想自动连接请设为null
                //.reConnConf(new ReconnConf(5000L))
                .build();
        //生成客户端对象;
        JimClient jimClient = new JimClient(imClientConfig);
        //连接服务端
        imClientChannelContext = jimClient.connect(serverNode);

        /*Integer msgType = 0;
        try{
            //判断消息类型是否正确
            msgType = Integer.valueOf(imCharsParam.getMsgType());
        }catch(NumberFormatException numberFormatException){
            Map<String,String> resultMap = new HashMap<>();
            resultMap.put("data", "消息类型传输有误");
            resultMap.put("code", "MSG-TYPE-ERRPR");
            return JSON.toJSONString(resultMap);
        }*/
        //连上后，发条消息测试一下
        return send(imCharsParam.getFromUser(),
                imCharsParam.getToUser(),
                0,
                ChatType.CHAT_TYPE_PRIVATE.getNumber(),
                "100",
                imCharsParam.getMsgContent());
    }

    /**
     * 发送消息的方法
     *
     * @param fromUser   发送消息者ID
     * @param toUser     接收消息者ID
     * @param msgType    消息类型
     * @param charType   聊天类型  公聊还是私聊
     * @param groupID    群聊，暂时不用
     * @param msgContent 消息体
     * @throws Exception
     */
    private static String send(String fromUser, String toUser, Integer msgType, Integer charType, String groupID, String msgContent) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        try{
            ChatBody chatBody = ChatBody.newBuilder()
                    .from(fromUser)
                    .to(toUser)
                    .msgType(msgType)
                    .chatType(charType)
                    .groupId(groupID)
                    .content(msgContent).build();
            TcpPacket chatPacket = new TcpPacket(Command.COMMAND_CHAT_REQ, chatBody.toByte());
            JimClientAPI.send(imClientChannelContext, chatPacket);

            map.put("resCode", "SYS_000000");
            map.put("resMsg", "发送成功");
            map.put("resData", "");
            return JSON.toJSONString(map);
        }catch (Exception e){
            map.put("resCode", "ERROR");
            map.put("resMsg", "发送失败");
            map.put("resData", "");
            return JSON.toJSONString(map);
        }
    }
}