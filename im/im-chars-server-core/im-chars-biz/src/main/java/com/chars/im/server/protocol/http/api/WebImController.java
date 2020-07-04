/**
 * 
 */
package com.chars.im.server.protocol.http.api;

import cn.hutool.core.io.FileUtil;
import org.apache.commons.lang3.StringUtils;
import com.chars.im.server.http.HttpRequest;
import com.chars.im.server.http.HttpResponse;
import com.chars.im.server.protocol.http.annotation.RequestPath;
import com.chars.im.server.util.HttpResps;

import java.io.File;

/**
 * 版本: [1.0]
 * 功能说明: 
 * 作者: WChao 创建时间: 2017年9月27日 下午4:54:35
 */
@RequestPath(value = "/webim")
public class WebImController {
	
	public HttpResponse webim(HttpRequest request) throws Exception {
		String root = FileUtil.getAbsolutePath(request.getHttpConfig().getPageRoot());
		String path = request.getRequestLine().getPath();
		File file = new File(root + path);
		if (!file.exists() || file.isDirectory()) {
			if (StringUtils.endsWith(path, "/")) {
				path = path + "index.html";
			} else {
				path = path + "/index.html";
			}
			file = new File(root, path);
		}
		HttpResponse ret = HttpResps.file(request, file);
		return ret;
	}
}
