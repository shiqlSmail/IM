package com.server.tools.util;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DozerMapperUtil {
	private static final Logger logger = LoggerFactory.getLogger(DozerMapperUtil.class);

	private static final  DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();

	public static void beanCopy(Object src, Object tar) {
		logger.info(src.toString());
		dozerBeanMapper.map(src, tar);
		logger.info(tar.toString());
	}
}
