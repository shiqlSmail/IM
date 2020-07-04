package com.chars.im.server.listener;

import com.chars.im.server.ImChannelContext;
import com.chars.im.server.exception.ImException;
import com.chars.im.server.message.MessageHelper;
import com.chars.im.server.packets.Group;
import com.chars.im.server.config.ImServerConfig;

import java.util.Objects;

/**
 * @author WChao
 * @Desc
 * @date 2020-05-03 00:17
 */
public abstract class AbstractImGroupListener implements ImGroupListener {

    public abstract void doAfterBind(ImChannelContext imChannelContext, Group group) throws ImException;

    public abstract void doAfterUnbind(ImChannelContext imChannelContext, Group group) throws ImException;

    @Override
    public void onAfterBind(ImChannelContext imChannelContext, Group group) throws ImException {
        ImServerConfig imServerConfig =  (ImServerConfig)imChannelContext.getImConfig();
        MessageHelper messageHelper = imServerConfig.getMessageHelper();
        //是否开启持久化
        if(isStore(imServerConfig)){
            messageHelper.getBindListener().onAfterGroupBind(imChannelContext, group);
        }
        doAfterBind(imChannelContext, group);
    }

    @Override
    public void onAfterUnbind(ImChannelContext imChannelContext, Group group) throws ImException {
        ImServerConfig imServerConfig =  (ImServerConfig)imChannelContext.getImConfig();
        MessageHelper messageHelper = imServerConfig.getMessageHelper();
        //是否开启持久化
        if(isStore(imServerConfig)){
            messageHelper.getBindListener().onAfterGroupUnbind(imChannelContext, group);
        }
        doAfterUnbind(imChannelContext, group);
    }

    /**
     * 是否开启持久化;
     * @return
     */
    public boolean isStore(ImServerConfig imServerConfig){
        MessageHelper messageHelper = imServerConfig.getMessageHelper();
        if(imServerConfig.ON.equals(imServerConfig.getIsStore()) && Objects.nonNull(messageHelper)){
            return true;
        }
        return false;
    }

}
