package com.chars.im.server.command.handler.userInfo;

import com.chars.im.server.ImChannelContext;
import com.chars.im.server.packets.User;
import com.chars.im.server.packets.UserReqBody;

public interface IUserInfo {
    /**
     * 获取用户信息接口
     * @param userReqBody
     * @param imChannelContext
     * @return
     */
    User getUserInfo(UserReqBody userReqBody, ImChannelContext imChannelContext);
}
