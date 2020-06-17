package com.im.user.server.datasource;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.im.user.server.dataconfig.DataSourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 数据源配置
 *
 * @ClassName DataSourceConfig
 * @Description TODO
 * @author lide
 * @date 2018年2月27日 下午1:21:39
 */
@Configuration
public class DataSourceConfig {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Bean(name = "master")
	@ConfigurationProperties(prefix = "datasource.master")
	public DataSource dataBaseSource() {
		logger.info("【DruidConfigration】==》【master】===============主数据源配置完成");
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "slave")
	@ConfigurationProperties(prefix = "datasource.slave")
	public DataSource dataSlaveSource() {
		logger.info("【DruidConfigration】==》【slave】===============从数据源配置完成");
		return DataSourceBuilder.create().build();
	}

	@Bean(name="dynamicDataSource")
	@Primary	//优先使用，多数据源
	public DataSource dataSource() {
		DynamicDataSource dynamicDataSource = new DynamicDataSource();
		try{
			DataSource master = dataBaseSource();
			DataSource slave = dataSlaveSource();
			//设置默认数据源
			dynamicDataSource.setDefaultTargetDataSource(master);
			//配置多数据源
			Map<Object,Object> map = new HashMap<>();
			map.put(DataSourceType.Master.getName(), master);	//key需要跟ThreadLocal中的值对应
			map.put(DataSourceType.Slave.getName(), slave);
			dynamicDataSource.setTargetDataSources(map);
		}catch (Exception e){
			logger.info("【DataSourceConfig】数据源加载出错："+e.getMessage());
		}
		return dynamicDataSource;
	}

}

