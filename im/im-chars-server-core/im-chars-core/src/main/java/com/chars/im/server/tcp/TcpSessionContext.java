/**
 * 
 */
package com.chars.im.server.tcp;

import com.chars.im.server.ImChannelContext;
import com.chars.im.server.ImSessionContext;

/**
 * 版本: [1.0]
 * 功能说明: 
 * @author : WChao 创建时间: 2017年9月6日 下午5:03:15
 */
public class TcpSessionContext extends ImSessionContext {

    public TcpSessionContext(ImChannelContext imChannelContext){
        super(imChannelContext);
    }

}
