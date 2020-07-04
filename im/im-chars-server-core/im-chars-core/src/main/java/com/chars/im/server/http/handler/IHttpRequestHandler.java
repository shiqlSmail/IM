package com.chars.im.server.http.handler;

import com.chars.im.server.exception.ImException;
import com.chars.im.server.http.HttpRequest;
import com.chars.im.server.http.HttpResponse;
import com.chars.im.server.http.RequestLine;

/**
 *
 * @author wchao
 *
 */
public interface IHttpRequestHandler {
	/**
	 *
	 * @param packet
	 * @param requestLine
	 * @return
	 * @throws ImException
	 * @author wchao
	 */
	public HttpResponse handler(HttpRequest packet, RequestLine requestLine) throws ImException;

	/**
	 *
	 * @param request
	 * @param requestLine
	 * @return
	 * @author wchao
	 */
	public HttpResponse resp404(HttpRequest request, RequestLine requestLine);

	/**
	 *
	 * @param request
	 * @param requestLine
	 * @param throwable
	 * @return
	 * @author wchao
	 */
	public HttpResponse resp500(HttpRequest request, RequestLine requestLine, Throwable throwable);
	
	/**
	 * 清空静态资源缓存，如果没有缓存，可以不处理
	 * @param request
	 * @author: wchao
	 */
	public void clearStaticResCache(HttpRequest request);
}
