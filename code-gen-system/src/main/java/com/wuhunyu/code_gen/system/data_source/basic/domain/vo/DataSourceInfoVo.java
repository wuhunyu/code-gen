package com.wuhunyu.code_gen.system.data_source.basic.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.wuhunyu.code_gen.common.constants.CommonConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 数据源Vo
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/19 17:19
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataSourceInfoVo {

    /**
     * 数据源id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long dataSourceId;

    /**
     * 数据库类型
     */
    private String dbTypeName;

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
     * 更新日期时间
     */
    @JsonFormat(pattern = CommonConstant.DATE_TIME_FORMATTER_STR)
    private LocalDateTime updateDatetime;

}
