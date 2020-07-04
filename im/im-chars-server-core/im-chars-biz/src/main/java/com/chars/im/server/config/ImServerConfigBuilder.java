/**
 * 
 */
package com.chars.im.server.config;

import com.chars.im.server.http.HttpConfig;
import com.chars.im.server.ws.WsConfig;
import com.chars.im.server.listener.ImServerListener;

/**
 * @author WChao
 * 2018/08/26
 */
public abstract class ImServerConfigBuilder<T extends ImServerConfig, B extends ImServerConfigBuilder>{

	protected T conf;
	protected ImServerListener serverListener;
	protected HttpConfig httpConfig;
	protected WsConfig wsConfig;

    /**
     * 留给子类配置Http服务器相关配置
     * @param httpConfig
     * @throws Exception
     * @return
     */
	public abstract B configHttp(HttpConfig httpConfig)throws Exception;

    /**
     * 配置WebSocket服务器相关配置
     * @param wsConfig
     * @throws Exception
     * @return
     *
     */
	public abstract B configWs(WsConfig wsConfig)throws Exception;

	/**
	 * 供子类获取自身builder抽象类;
	 * @return
	 */
	protected abstract B getThis();

	public B serverListener(ImServerListener serverListener){
		this.serverListener = serverListener;
		return getThis();
	}

    public T build() throws Exception{
		this.httpConfig = HttpConfig.newBuilder().build();
		this.wsConfig = WsConfig.newBuilder().build();
        this.configHttp(httpConfig);
        this.configWs(wsConfig);
        return conf;
	}

}
