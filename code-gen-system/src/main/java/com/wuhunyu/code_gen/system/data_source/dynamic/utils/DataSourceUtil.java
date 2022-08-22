package com.wuhunyu.code_gen.system.data_source.dynamic.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据源工具类
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/22 13:51
 */

@Slf4j
public final class DataSourceUtil {

    private DataSourceUtil() {
    }

    /**
     * 测试数据源连接是否可用
     *
     * @param url      数据源连接地址
     * @param userName 用户名
     * @param password 密码
     * @return 是否可用(true : 可用 ; false : 不可用)
     */
    public static boolean checkDataSourceConnection(String url, String userName, String password) {
        if (StringUtils.isBlank(url) ||
                StringUtils.isBlank(userName) ||
                StringUtils.isBlank(password)) {
            return false;
        }
        try (Connection connection = DriverManager.getConnection(url, userName, password)) {
            return true;
        } catch (SQLException e) {
            log.info("连接数据源异常: url: {}, userName: {}, password: {}", url, userName, password);
            log.info("{}", e.getLocalizedMessage(), e);
            return false;
        }
    }

}
