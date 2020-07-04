package com.esb.im.server.controller.esb;

import com.alibaba.fastjson.JSON;
import com.chars.im.server.*;
import com.chars.im.server.config.ImClientConfig;
import com.chars.im.server.packets.ChatBody;
import com.chars.im.server.packets.ChatType;
import com.chars.im.server.packets.Command;
import com.chars.im.server.tcp.TcpPacket;
import com.esb.im.server.config.HelloClientStarter;
import com.esb.im.server.request.IMCharsParam;
import com.esb.im.server.system.InterfaceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.tio.core.Node;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CharsClientController extends HelloClientStarter {
    protected static final Logger log = LoggerFactory.getLogger(CharsClientController.class);
    public static ImClientChannelContext imClientChannelContext = null;

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/im-chars", method = RequestMethod.POST)
    public String json(@RequestBody IMCharsParam imCharsParam) throws Exception {
        return sendChars(imCharsParam);
    }
}
