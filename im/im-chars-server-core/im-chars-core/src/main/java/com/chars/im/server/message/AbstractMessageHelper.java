/**
 * 
 */
package com.chars.im.server.message;

import com.chars.im.server.ImConst;
import com.chars.im.server.config.ImConfig;

/**
 * @author HP
 *
 */
public abstract class AbstractMessageHelper implements MessageHelper,ImConst {

	protected ImConfig imConfig;

	public ImConfig getImConfig() {
		return imConfig;
	}

	public void setImConfig(ImConfig imConfig) {
		this.imConfig = imConfig;
	}
}
