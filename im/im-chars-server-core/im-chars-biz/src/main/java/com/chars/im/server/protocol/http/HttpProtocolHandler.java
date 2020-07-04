/**
 * 
 */
package com.chars.im.server.protocol.http;

import com.chars.im.server.ImChannelContext;
import com.chars.im.server.ImConst;
import com.chars.im.server.ImPacket;
import com.chars.im.server.config.ImConfig;
import com.chars.im.server.exception.ImDecodeException;
import com.chars.im.server.exception.ImException;
import com.chars.im.server.http.*;
import com.chars.im.server.http.handler.IHttpRequestHandler;
import com.chars.im.server.protocol.AbstractProtocol;
import com.chars.im.server.id.impl.UUIDSessionIdGenerator;
import com.chars.im.server.JimServer;
import com.chars.im.server.JimServerAPI;
import com.chars.im.server.config.ImServerConfig;
import com.chars.im.server.protocol.AbstractProtocolHandler;
import com.chars.im.server.protocol.http.mvc.Routes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.utils.cache.guava.GuavaCache;

import java.nio.ByteBuffer;
import java.util.Objects;

/**
 * 版本: [1.0]
 * 功能说明: 
 * @author : WChao 创建时间: 2017年8月3日 下午3:07:54
 */
public class HttpProtocolHandler extends AbstractProtocolHandler {
	
	private Logger log = LoggerFactory.getLogger(HttpProtocolHandler.class);

	private HttpConfig httpConfig;
	
	private IHttpRequestHandler httpRequestHandler;

	public HttpProtocolHandler(){
		this(null, new HttpProtocol(new HttpConvertPacket()));
	};

	public HttpProtocolHandler(HttpConfig httpConfig, AbstractProtocol protocol){
		super(protocol);
		this.httpConfig = httpConfig;
	}

	@Override
	public void init(ImServerConfig imServerConfig)throws ImException {
		this.httpConfig = imServerConfig.getHttpConfig();
		if (Objects.isNull(httpConfig.getSessionStore())) {
			GuavaCache guavaCache = GuavaCache.register(httpConfig.getSessionCacheName(), null, httpConfig.getSessionTimeout());
			httpConfig.setSessionStore(guavaCache);
		}
		if (Objects.isNull(httpConfig.getSessionIdGenerator())) {
			httpConfig.setSessionIdGenerator(UUIDSessionIdGenerator.instance);
		}
		if(Objects.isNull(httpConfig.getScanPackages())){
			//J-IM MVC需要扫描的根目录包
			String[] scanPackages = new String[] { JimServer.class.getPackage().getName() };
			httpConfig.setScanPackages(scanPackages);
		}else{
			String[] scanPackages = new String[httpConfig.getScanPackages().length+1];
			scanPackages[0] = JimServer.class.getPackage().getName();
			System.arraycopy(httpConfig.getScanPackages(), 0, scanPackages, 1, httpConfig.getScanPackages().length);
			httpConfig.setScanPackages(scanPackages);
		}
		Routes routes = new Routes(httpConfig.getScanPackages());
		httpRequestHandler = new DefaultHttpRequestHandler(httpConfig, routes);
		httpConfig.setHttpRequestHandler(httpRequestHandler);
		log.info("Http Protocol initialized");
	}
	
	@Override
	public ByteBuffer encode(ImPacket imPacket, ImConfig imConfig, ImChannelContext imChannelContext) {
		HttpResponse httpResponsePacket = (HttpResponse) imPacket;
		ByteBuffer byteBuffer = HttpResponseEncoder.encode(httpResponsePacket, imChannelContext,false);
		return byteBuffer;
	}

	@Override
	public void handler(ImPacket imPacket, ImChannelContext imChannelContext)throws ImException {
		HttpRequest httpRequestPacket = (HttpRequest) imPacket;
		HttpResponse httpResponsePacket = httpRequestHandler.handler(httpRequestPacket, httpRequestPacket.getRequestLine());
		JimServerAPI.send(imChannelContext, httpResponsePacket);
	}

	@Override
	public ImPacket decode(ByteBuffer buffer, int limit, int position, int readableLength, ImChannelContext imChannelContext)throws ImDecodeException {
		HttpRequest request = HttpRequestDecoder.decode(buffer, imChannelContext,true);
		imChannelContext.setAttribute(ImConst.HTTP_REQUEST,request);
		return request;
	}
	
	public IHttpRequestHandler getHttpRequestHandler() {
		return httpRequestHandler;
	}

	public void setHttpRequestHandler(IHttpRequestHandler httpRequestHandler) {
		this.httpRequestHandler = httpRequestHandler;
	}
	
	public HttpConfig getHttpConfig() {
		return httpConfig;
	}

	public void setHttpConfig(HttpConfig httpConfig) {
		this.httpConfig = httpConfig;
	}

}
