package com.server.tools.send;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.server.tools.common.SendSms;

import java.util.HashMap;
import java.util.Map;

/**
 * 阿里云发送短信验证信息
 * @author shiqilong
 *
 */
public class SendSmsUtils extends SendSms {

	public static boolean send(String phone,Integer smsCode) throws ClientException {
		// 设置超时时间-可自行调整
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");
		// 初始化ascClient需要的几个参数
		final String product = PRODUCT;// 短信API产品名称（短信产品名固定，无需修改）
		final String domain = DOMAIN;// 短信API产品域名（接口地址固定，无需修改）
		// 替换成你的AK
		final String accessKeyId = ACCESSKEYID;// 你的accessKeyId,参考本文档步骤2
		final String accessKeySecret = ACCESSKEYSECRET;// 你的accessKeySecret，参考本文档步骤2
		IClientProfile profile = DefaultProfile.getProfile(PROFILE, accessKeyId, accessKeySecret);
		DefaultProfile.addEndpoint(PROFILE, PROFILE, product, domain);
		IAcsClient acsClient = new DefaultAcsClient(profile);
		// 组装请求对象
		SendSmsRequest request = new SendSmsRequest();
		request.setMethod(MethodType.POST);
		request.setPhoneNumbers(phone);
		request.setSignName(SIGNNAME);
		// 必填:短信模板-可在短信控制台中找到
		request.setTemplateCode(TEMPLATECODE);

		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("code", smsCode);
		JSON json = (JSON) JSON.toJSON(map);
		request.setTemplateParam(json.toJSONString());
		SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
		if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) throws ClientException {
		send("17621663876",123456);
	}
}
