package com.im.file.server.datasource;
/**
 * 动态数据源的上下文 threadlocal
 * @ClassName JdbcContextHolder
 * @Description TODO
 */
public class JdbcContextHolder {
	
	private final static ThreadLocal<String> local = new ThreadLocal<>();
	
	public static void putDataSource(String name) {
		local.set(name);
	}
	
	public static String getDataSource() {
		return local.get();
	}
	
}