package com.chars.im.server.id;

import com.chars.im.server.http.HttpConfig;

/**
 * @author wchao
 * 2017年8月15日 上午10:49:58
 */
public interface ISessionIdGenerator {

	/**
	 *
	 * @return
	 * @author wchao
	 */
	String sessionId(HttpConfig httpConfig);

}
