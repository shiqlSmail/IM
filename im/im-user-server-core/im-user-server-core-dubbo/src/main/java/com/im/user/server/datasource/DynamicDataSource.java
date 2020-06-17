package com.im.user.server.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 
 * @ClassName DynamicDataSource
 * @Description TODO
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	protected Object determineCurrentLookupKey() {
		logger.info("当前数据源为{}", JdbcContextHolder.getDataSource());
		return JdbcContextHolder.getDataSource();
	}

}