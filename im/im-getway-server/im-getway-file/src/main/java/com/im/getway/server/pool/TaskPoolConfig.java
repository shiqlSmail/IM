package com.im.getway.server.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

//@Configuration
//@EnableAsync
public class TaskPoolConfig{
    protected final Logger log = LoggerFactory.getLogger(TaskPoolConfig.class);

    /*@Component
    public class ThreadAsyncConfigurer  implements AsyncConfigurer {
        @Bean
        public Executor getAsyncExecutor() {
            ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
            //设置核心线程数
            threadPool.setCorePoolSize(10);
            //设置最大线程数
            threadPool.setMaxPoolSize(100);
            //线程池所使用的缓冲队列
            threadPool.setQueueCapacity(10);
            //等待任务在关机时完成--表明等待所有线程执行完
            threadPool.setWaitForTasksToCompleteOnShutdown(true);
            // 等待时间 （默认为0，此时立即停止），并没等待xx秒后强制停止
            threadPool.setAwaitTerminationSeconds(60);
            //  线程名称前缀
            threadPool.setThreadNamePrefix("ILONW-Async:");
            // 初始化线程
            threadPool.initialize();
            return threadPool;
        }*/

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(10);
        // 设置最大线程数
        executor.setMaxPoolSize(20);
        // 设置队列容量
        executor.setQueueCapacity(20);
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(60);
        // 设置默认线程名称
        executor.setThreadNamePrefix("IM-File-Async:");
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }
}
