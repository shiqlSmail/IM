package com.im.user.server.dataconfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据源选择--自定义注解
 * @ClassName DataSource
 * @Description TODO
 */
@Retention(RetentionPolicy.RUNTIME)  
@Target(ElementType.METHOD) 
public @interface MyDataSource {

	DataSourceType value() default DataSourceType.Master;	//默认主表
	
}
