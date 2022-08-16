package com.wuhunyu.code_gen.system.environment.model;

import com.wuhunyu.code_gen.system.config.YamlPropertySourceFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 代码生成model
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/16 16:23
 */

@Data
@Component("codeGenConfigModel")
@PropertySource(name = "CodeGenConfig", value = "classpath:CodeGenConfig.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "code-gen-config")
public class CodeGenConfigModel {

    /**
     * 配置名称
     */
    private String configName;

    /**
     * 需要去除的表前缀
     */
    private String tablePrefix;

    /**
     * 项目包名
     */
    private String projectPackage;

    /**
     * 模块名
     */
    private String moduleName;

    /**
     * 子模块名
     */
    private String subModuleName;

    /**
     * 作者名称
     */
    private String author;

    /**
     * 代码名称
     */
    private String version;

}
