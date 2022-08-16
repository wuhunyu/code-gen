package com.wuhunyu.code_gen.system.config;

import cn.hutool.core.thread.NamedThreadFactory;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * springboot内置异步线程管理
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/16 17:23
 */

@Component("asyncConfigurerConfig")
@Data
@ConditionalOnProperty(prefix = "task-config", name = "enabled", havingValue = "true")
@ConfigurationProperties(prefix = "task-config")
public class AsyncConfigurerConfig implements AsyncConfigurer {

    /**
     * 是否启用线程池配置
     */
    private Boolean enabled;

    /**
     * 核心线程数
     */
    private Integer coreNum;

    /**
     * 最大线程数
     */
    private Integer maxNum;

    /**
     * 线程空闲秒数
     */
    private Integer idleSecondNum;

    /**
     * 阻塞队列等待线程数
     */
    private Integer waitNum;

    /**
     * 线程池名称前缀
     */
    private String taskNamePrefix;

    @Override
    public Executor getAsyncExecutor() {
        // 创建线程池
        return new ThreadPoolExecutor(
                coreNum,
                maxNum,
                idleSecondNum,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(waitNum),
                new NamedThreadFactory(taskNamePrefix, false),
                new ThreadPoolExecutor.AbortPolicy());
    }

}
