package com.wuhunyu.code_gen.system.data_source.basic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 数据源配置信息
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/19 12:09
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataSourceInfo {

    /**
     * 数据源id
     */
    private Long dataSourceId;

    /**
     * 数据库类型
     */
    private Integer dbType;

    /**
     * 连接名
     */
    private String connectionName;

    /**
     * 连接url
     */
    private String connectionUrl;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 修改日期时间
     */
    private LocalDateTime updateDatetime;

}
