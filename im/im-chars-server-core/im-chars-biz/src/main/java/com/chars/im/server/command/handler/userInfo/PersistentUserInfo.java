package com.chars.im.server.command.handler.userInfo;

import com.chars.im.server.ImChannelContext;
import com.chars.im.server.config.ImConfig;
import com.chars.im.server.message.MessageHelper;
import com.chars.im.server.packets.User;
import com.chars.im.server.packets.UserReqBody;
import com.chars.im.server.config.ImServerConfig;

import java.util.Objects;

/**
 * 持久化获取用户信息处理
 */
public class PersistentUserInfo implements IUserInfo {

    @Override
    public User getUserInfo(UserReqBody userReqBody, ImChannelContext imChannelContext) {
        ImServerConfig imServerConfig = ImConfig.Global.get();
        String userId = userReqBody.getUserId();
        Integer type = userReqBody.getType();
        //消息持久化助手;
        MessageHelper messageHelper = imServerConfig.getMessageHelper();
        User user = messageHelper.getUserByType(userId, type);
        if(Objects.nonNull(user)) {
            user.setFriends(messageHelper.getAllFriendUsers(userId, type));
            user.setGroups(messageHelper.getAllGroupUsers(userId, type));
        }
        return user;
    }

}
