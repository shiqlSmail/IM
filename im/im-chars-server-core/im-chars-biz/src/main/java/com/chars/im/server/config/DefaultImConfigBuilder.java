/**
 * 
 */
package com.chars.im.server.config;

import com.chars.im.server.http.HttpConfig;
import com.chars.im.server.ws.WsConfig;

/**
 * @author WChao
 *
 */
public class DefaultImConfigBuilder extends ImServerConfigBuilder {

	@Override
	public ImServerConfigBuilder configHttp(HttpConfig httpConfig) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ImServerConfigBuilder configWs(WsConfig wsServerConfig) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	protected ImServerConfigBuilder getThis() {
		return this;
	}

}
