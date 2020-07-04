package com.chars.im.server.cache;

import java.io.Serializable;

public interface IL2Cache {
	
	public void putL2Async(String key, Serializable value);
}
