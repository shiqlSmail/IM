package com.esb.im.server.serviceimpl;

import com.esb.im.server.dao.IMLoginMapper;
import com.esb.im.server.dao.IMSysConfigMapper;
import com.esb.im.server.domain.IMLoginDO;
import com.esb.im.server.domain.IMSysConfigDO;
import com.esb.im.server.service.IMLoginService;
import com.esb.im.server.service.IMSysConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("imSysConfigService")
public class IMSysConfigServiceImpl implements IMSysConfigService {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired(required = false)
    private IMSysConfigMapper imSysConfigMapper;

    @Override
    public String findConfigByCode(String code) {
        IMSysConfigDO imSysConfigDO = new IMSysConfigDO();
        imSysConfigDO.setSysConfigCode(code);
        IMSysConfigDO resultIMSysConfig = imSysConfigMapper.findConfigByCode(imSysConfigDO);
        if(StringUtils.isEmpty(resultIMSysConfig)){
            return null;
        }
        return resultIMSysConfig.getSysConfigValue();
    }
}
