package com.im.file.server.datasource;

import com.im.file.server.dataconfig.MyDataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * AOP根据注解给上下文赋值
 * @ClassName DataSourceAspect
 */
@Aspect
@Order(1)	//设置AOP执行顺序(需要在事务之前，否则事务只发生在默认库中)
@Component
public class DataSourceAspect {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//切点
	@Pointcut("execution(* com.im.file.server.biz.*.*(..)))")
	public void aspect() { }
	
	@Before("aspect()")
	private void before(JoinPoint point) {
		Object target = point.getTarget();  
        String method = point.getSignature().getName();  
        Class<?> classz = target.getClass();  
        Class<?>[] parameterTypes = ((MethodSignature) point.getSignature())  
                .getMethod().getParameterTypes();  
        try {  
            Method m = classz.getMethod(method, parameterTypes);  
            if (m != null && m.isAnnotationPresent(MyDataSource.class)) {
            	MyDataSource data = m.getAnnotation(MyDataSource.class);  
                JdbcContextHolder.putDataSource(data.value().getName());
                logger.info("===============上下文赋值完成:{}",data.value().getName());
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        
	}
}
