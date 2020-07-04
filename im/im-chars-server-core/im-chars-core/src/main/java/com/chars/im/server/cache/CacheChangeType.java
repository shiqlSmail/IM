package com.chars.im.server.cache;

import java.util.Objects;

public enum CacheChangeType {
	/**
	 * key级别清空本地缓存
	 */
	REMOVE(1),
	/**
	 * key级别清空本地缓存
	 */
	UPDATE(2),
	/**
	 * key级别清空本地缓存
	 */
	PUT(3),
	/**
	 * cacheName级别清空本地缓存
	 */
	CLEAR(4);

	public static CacheChangeType from(Integer method) {
		CacheChangeType[] values = CacheChangeType.values();
		for (CacheChangeType v : values) {
			if (Objects.equals(v.value, method)) {
				return v;
			}
		}
		return null;
	}

    Integer value;

	private CacheChangeType(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}
	
}
