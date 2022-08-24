package com.wuhunyu.code_gen.system.data_source.dynamic.utils;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DruidDataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.wuhunyu.code_gen.system.data_source.basic.domain.dto.DataSourceInfoDto;
import com.wuhunyu.code_gen.system.data_source.basic.enums.DBTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

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

    /**
     * 新增一个数据源
     *
     * @param dataSourceInfoDto 数据源配置
     */
    public static void addDataSource(DataSourceInfoDto dataSourceInfoDto) {
        // 数据源属性配置
        DataSourceProperty dataSourceProperty = new DataSourceProperty();
        dataSourceProperty.setLazy(true);
        dataSourceProperty.setType(DataSourceUtil.findDataSourceByDbType(dataSourceInfoDto.getDbType()));
        dataSourceProperty.setUrl(dataSourceInfoDto.getConnectionUrl());
        dataSourceProperty.setUsername(dataSourceInfoDto.getUserName());
        dataSourceProperty.setPassword(dataSourceInfoDto.getPassword());
        dataSourceProperty.setPoolName(dataSourceInfoDto.getDataSourceId().toString());

        // 获取数据源对象
        DynamicRoutingDataSource dynamicRoutingDataSource =
                (DynamicRoutingDataSource) SpringUtil.getBean(DataSource.class);
        // 获取druid数据源创建对象
        DruidDataSourceCreator druidDataSourceCreator = SpringUtil.getBean(DruidDataSourceCreator.class);
        DataSource dataSource = druidDataSourceCreator.createDataSource(dataSourceProperty);
        dynamicRoutingDataSource.addDataSource(dataSourceInfoDto.getDataSourceId().toString(), dataSource);
    }

    /**
     * 修改一个数据源配置
     *
     * @param dataSourceInfoDto 数据源配置
     */
    public static void updateDataSource(DataSourceInfoDto dataSourceInfoDto) {
        // 首先移除该数据源
        DataSourceUtil.deleteDataSource(dataSourceInfoDto.getDataSourceId());

        // 新增
        DataSourceUtil.addDataSource(dataSourceInfoDto);
    }

    /**
     * 移除一个数据源配置
     *
     * @param dataSourceId 数据源ids
     */
    public static void deleteDataSource(Long dataSourceId) {
        // 获取数据源对象
        DynamicRoutingDataSource dynamicRoutingDataSource =
                (DynamicRoutingDataSource) SpringUtil.getBean(DataSource.class);
        dynamicRoutingDataSource.removeDataSource(dataSourceId.toString());
    }

    /**
     * 判断当前数据源是否已存在
     *
     * @param dataSourceId 数据源id
     * @return 当前数据源是否已存在(true : 存在 ; false : 不存在)
     */
    public static boolean existsDataSource(Long dataSourceId) {
        // 获取数据源对象
        DynamicRoutingDataSource dynamicRoutingDataSource =
                (DynamicRoutingDataSource) SpringUtil.getBean(DataSource.class);
        DataSource dataSource = dynamicRoutingDataSource.getDataSource(dataSourceId.toString());
        return dataSource != null;
    }

    /**
     * 手动切换数据源
     *
     * @param dataSourceId 数据源id
     */
    public static void openDataSource(Long dataSourceId) {
        DynamicDataSourceContextHolder.push(String.valueOf(dataSourceId));
    }

    /**
     * 手动关闭数据源
     */
    public static void closeDataSource() {
        DynamicDataSourceContextHolder.poll();
    }

    /**
     * 手动切换数据源
     *
     * @param dataSourceId 数据源id
     * @param supplier     生产者
     * @param <T>          返回结果泛型
     * @return 返回结果
     */
    public static <T> T handleDataSource(Long dataSourceId, Supplier<T> supplier) {
        try {
            // 手动切换数据源
            DataSourceUtil.openDataSource(dataSourceId);

            // 执行
            return supplier.get();
        } finally {
            // 回退数据源
            DataSourceUtil.closeDataSource();
        }
    }

    /**
     * 查询对应的数据源类型
     *
     * @param dbType 数据源类型
     * @return 数据源类型
     */
    private static Class<? extends DataSource> findDataSourceByDbType(int dbType) {
        // MySQL
        if (dbType == DBTypeEnum.MYSQL.getType()) {
            return DruidDataSource.class;
        }
        return null;
    }

}
