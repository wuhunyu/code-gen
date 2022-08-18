package com.wuhunyu.code_gen.system.class_type.model;

import cn.hutool.core.collection.CollUtil;
import com.wuhunyu.code_gen.system.config.YamlPropertySourceFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 类型映射model
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/18 17:11
 */

@Data
@Component("classTypeModel")
@PropertySource(name = "ClassTypeMapping", value = "classpath:ClassTypeMapping.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "class-type-mapping")
public class ClassTypeModel {

    /**
     * 对应关系
     */
    @Data
    public static class ClassTypeInnerModel {

        private String jdbcType;

        private String javaTypePackage;

        private String javaTypeName;

    }

    /**
     * 不需要手动导入的包
     */
    private String defaultJavaPackage;

    /**
     * 默认对应关系
     */
    private List<ClassTypeInnerModel> defaultList;

    /**
     * 默认对应关系 map形式
     */
    private Map<String, ClassTypeInnerModel> defaultMap;

    /**
     * 将默认对应关系转成map
     */
    @PostConstruct
    public void listToMap() {
        if (CollUtil.isEmpty(defaultList)) {
            defaultMap = Collections.emptyMap();
        }
        defaultMap = defaultList.stream()
                .collect(Collectors.toMap(
                        ClassTypeInnerModel::getJdbcType,
                        item -> item,
                        (item1, item2) -> item1,
                        ConcurrentHashMap::new));
    }

}
