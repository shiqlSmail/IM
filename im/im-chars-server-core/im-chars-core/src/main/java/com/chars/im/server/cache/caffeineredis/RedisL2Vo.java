package com.chars.im.server.cache.caffeineredis;

import com.chars.im.server.cache.redis.RedisCache;

import java.io.Serializable;

public class RedisL2Vo {
	
	private RedisCache redisCache;
	private String key;
	private Serializable value;
	
	public RedisL2Vo(RedisCache redisCache , String key , Serializable value){
		this.redisCache = redisCache;
		this.key = key;
		this.value = value;
	}

	public RedisCache getRedisCache() {
		return redisCache;
	}

	public void setRedisCache(RedisCache redisCache) {
		this.redisCache = redisCache;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Serializable getValue() {
		return value;
	}

	public void setValue(Serializable value) {
		this.value = value;
	}
	
}
