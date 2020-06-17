package com.server.tools.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UrlUtil {
	public static List<String> map2kvString(Map<String, String> map) {
		List<String> list = new ArrayList<String>();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String str = entry.getKey() + "=" + entry.getValue();
			list.add(str);
		}
		return list;
	}

	public Map<String, String> getUrlUtil(String text) {
		Map<String, String> map = new HashMap();
		String type = "";
		if (text.contains("taobao") || text.contains("m.tb")) {
			type = "taobao";
		} else if (text.contains("jd") || text.contains("m.jd")) {
			type = "jingdong";
		} else if (text.contains("meituan")) {
			type = "meituan";
		} else if (text.contains("weibo")) {
			type = "weibo";
		}
		map.put("type", type);
		try {
			String[] split = text.split("http");
			String oneString = "http" + split[1];
			if (oneString.contains(" ")) {
				String[] twoString = oneString.split(" ");
				String result = twoString[0].replaceAll("\\\\", "");
				if (result.contains("查看")){
					result = result.replaceAll("查看","");
				}
				map.put("url", result);
				return map;
			}
			oneString = oneString.replaceAll("\\\\", "");
			if (oneString.contains("查看")){
				oneString = oneString.replaceAll("查看","");
			}
			map.put("url", oneString);
			return map;
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {
		String text = "【儿童走秀服装时尚女童潮流夏装模特,这一款女童服装设计，采用专业的面料，亲肤舒适，不会有闷热感】 http://m.tb.cn/h.31xoQhI 点击链接，再选择浏览器咑閞；或復·制这段描述€Hbrw0AXfywa€后咑閞淘寳[来自超级会员的分享]";
		String text1 = "在美团发现这个团购很不错哦！仅售368元！价值468元的3至4人套餐1份，提供免费WiFi。 @美团 http://www.meituan.com/deal/37069262.html?utm_term=AandroidBgroupC9.5.1DweiboEdealG201807232208560000000000000A00DDD1112844C79893B3C37AB27C7240000000000003513891692&utm_source=appshare&utm_medium=androidweb&utm_fromapp=查看";
		String tt = "【户外防腐碳化实木凉亭木屋葡萄架室外庭院廊架阳台木亭子桌椅花架】http:\\/\\/m.tb.cn\\/h.32K3h7L 点击链接，再选择浏览器咑閞；或復·制这段描述€JbDzb08C6tD€后到淘寳";
		String qw = "https://item.m.jd.com/product/5154672.html?ShareTm=o42r0XDJ4QE3JpwyUcaF2Ij%2FgQ8J2zb%2Fk14NUIPvRy6%2F9I79mvpXQnhW1RqivpFqEU46rMGdKMTiF5tgxp9v6zp4eMAI5KTKv4z2Eaf6E3aaekOyE9l8I6iGMTE%2B%2B%2B2r44rP1%2BzvtS8tqWfV5meCDDOUii8fG9EkN8eRtcGQiV8%3D&ad_od=share&utm_source=androidapp&utm_medium=appshare&utm_campaign=t_335139774&utm_term=CopyURL";
		new UrlUtil().getUrlUtil(text1);
	}
	
	 public static boolean isPhoneNumber(String phoneNumber){
	       if (null == phoneNumber || "".equals(phoneNumber)) return false;
	        String regex = "^1[2|3|4|5|6|7|8|9][0-9]\\d{8}$";
	        return phoneNumber.matches(regex);
	  }
}
