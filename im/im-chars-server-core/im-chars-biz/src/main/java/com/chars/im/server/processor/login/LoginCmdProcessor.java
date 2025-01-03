/**
 * 
 */
package com.chars.im.server.processor.login;

import com.chars.im.server.ImChannelContext;
import com.chars.im.server.packets.LoginReqBody;
import com.chars.im.server.packets.LoginRespBody;
import com.chars.im.server.packets.User;
import com.chars.im.server.processor.SingleProtocolCmdProcessor;

/**
 *
 * @author WChao
 */
public interface LoginCmdProcessor extends SingleProtocolCmdProcessor {
	/**
	 * 执行登录操作接口方法
	 * @param loginReqBody
	 * @param imChannelContext
	 * @return
	 */
	 LoginRespBody doLogin(LoginReqBody loginReqBody, ImChannelContext imChannelContext);
	/**
	 * 获取用户信息接口方法
	 * @param loginReqBody
	 * @param imChannelContext
	 * @return
	 */
	User getUser(LoginReqBody loginReqBody, ImChannelContext imChannelContext);
	/**
	 * 登录成功(指的是J-IM会在用户校验完登陆逻辑后进行J-IM内部绑定)回调方法
	 * @param imChannelContext
	 */
	 void onSuccess(User user, ImChannelContext imChannelContext);

	/**
	 * 登陆失败回调方法
	 * @param imChannelContext
	 */
	 void onFailed(ImChannelContext imChannelContext);
}
