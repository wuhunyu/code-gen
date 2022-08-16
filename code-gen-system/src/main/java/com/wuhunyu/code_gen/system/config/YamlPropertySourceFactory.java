package com.wuhunyu.code_gen.system.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * 读取yaml配置文件
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/16 16:44
 */

public class YamlPropertySourceFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) {
        Properties propertiesFromYaml = loadYamlIntoProperties(resource);
        return new PropertiesPropertySource(StringUtils.isNoneBlank(name) ? name : resource.getResource().getFilename(), propertiesFromYaml);
    }

    /**
     * 读取配置文件
     *
     * @param resource 配置文件资源
     * @return properties
     */
    private Properties loadYamlIntoProperties(EncodedResource resource) {
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(resource.getResource());
        factory.afterPropertiesSet();
        return factory.getObject();
    }
}
