package com.im.user.server.test;

import com.alibaba.fastjson.JSON;
import com.im.user.server.domain.IMUserEntity;
import com.im.user.server.facade.IMUserFacade;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Map;

public class DemoTest extends IMApplicationTests {

    @Resource
    public IMUserFacade imUserFacade;
    //@Ignore("not ready yet")

    @Test
    public void test1(){
        for (int i = 0; i < 100; i++) {
            IMUserEntity param = new IMUserEntity();
            param.setImUserEmail("jack1053996819@163.com");
            Map<String, Object> testBOPageBean= imUserFacade.checkIMUserByEmailExits(param);
            System.out.print("==============================>>>>.111111>>>"+i+"================>>>"+ JSON.toJSON(testBOPageBean));
        }
    }
}
