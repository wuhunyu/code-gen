package com.wuhunyu.code_gen.system.data_source.dynamic.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.wuhunyu.code_gen.common.domain.SelectData;
import com.wuhunyu.code_gen.common.utils.Assert;
import com.wuhunyu.code_gen.system.data_source.basic.constant.DataSourceInfoConstant;
import com.wuhunyu.code_gen.system.data_source.basic.domain.dto.DataSourceInfoDto;
import com.wuhunyu.code_gen.system.data_source.basic.service.DataSourceInfoService;
import com.wuhunyu.code_gen.system.data_source.dynamic.domain.TableData;
import com.wuhunyu.code_gen.system.data_source.dynamic.domain.query.TableQuery;
import com.wuhunyu.code_gen.system.data_source.dynamic.domain.vo.TableDataVo;
import com.wuhunyu.code_gen.system.data_source.dynamic.mapper.DynamicDataSourceMapper;
import com.wuhunyu.code_gen.system.data_source.dynamic.service.DynamicDataSourceService;
import com.wuhunyu.code_gen.system.data_source.dynamic.utils.DataSourceUtil;
import com.wuhunyu.code_gen.system.environment.domain.dto.UserEnvironmentDto;
import com.wuhunyu.code_gen.system.environment.service.UserEnvironmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 动态数据源 处理实现
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/23 22:51
 */

@Service("dynamicDataSourceService")
@Primary
@RequiredArgsConstructor
@Slf4j
public class DynamicDataSourceServiceImpl implements DynamicDataSourceService {

    private final DynamicDataSourceMapper dynamicDataSourceMapper;

    private final DataSourceInfoService dataSourceInfoService;

    private final UserEnvironmentService userEnvironmentService;

    @Override
    public List<SelectData> listNewTables(Long useEnvironmentId, Long userId) {
        // 查询数据源信息
        DataSourceInfoDto dataSourceInfoDto = this.findDataSourceInfoDtoByUserEnvironmentId(useEnvironmentId, userId);
        Long dataSourceId = dataSourceInfoDto.getDataSourceId();

        // 创建数据源连接
        if (!DataSourceUtil.existsDataSource(dataSourceId)) {
            DataSourceUtil.addDataSource(dataSourceInfoDto);
        }

        return DataSourceUtil.handleDataSource(dataSourceId, () -> {
            // 查询当前数据库名称
            String curDataBaseName = dynamicDataSourceMapper.findCurDataBaseName();
            // 查询当前数据库下的所有表信息
            List<TableData> tableData = dynamicDataSourceMapper.listTables(curDataBaseName);
            if (CollUtil.isEmpty(tableData)) {
                return Collections.emptyList();
            }
            // 返回表信息
            return tableData.stream()
                    .map(table -> new SelectData(
                            table.getTableName(),
                            table.getTableId().toString()))
                    .collect(Collectors.toList());
        });
    }

    @Override
    public void importTableData(Long useEnvironmentId, Long userId) {
        // 查询数据源信息
        DataSourceInfoDto dataSourceInfoDto = this.findDataSourceInfoDtoByUserEnvironmentId(useEnvironmentId, userId);
        Long dataSourceId = dataSourceInfoDto.getDataSourceId();

        // 创建数据源连接
        if (!DataSourceUtil.existsDataSource(dataSourceId)) {
            DataSourceUtil.addDataSource(dataSourceInfoDto);
        }

        DataSourceUtil.handleDataSource(dataSourceId, () -> {
            // 查询当前数据库名称
            String curDataBaseName = dynamicDataSourceMapper.findCurDataBaseName();
            // 查询当前数据库下的所有表信息
            List<TableData> tableData = dynamicDataSourceMapper.listTables(curDataBaseName);
            if (CollUtil.isEmpty(tableData)) {
                return Collections.emptyList();
            }
            // 返回表信息
            return tableData.stream()
                    .map(table -> new SelectData(
                            table.getTableName(),
                            table.getTableId().toString()))
                    .collect(Collectors.toList());
        });
    }

    @Override
    public List<TableDataVo> pageTableDataVos(TableQuery tableQuery, Long useEnvironmentId) {
        return null;
    }

    @Override
    public Long countTableData(TableQuery tableQuery, Long useEnvironmentId) {
        return null;
    }

    /**
     * 获取当前操作的数据源信息
     *
     * @param useEnvironmentId 环境id
     * @param userId           用户id
     * @return 数据源信息
     */
    private DataSourceInfoDto findDataSourceInfoDtoByUserEnvironmentId(Long useEnvironmentId, Long userId) {
        // 获取环境信息
        UserEnvironmentDto userEnvironmentDto =
                userEnvironmentService.findUserEnvironmentDtoByUserEnvironmentId(useEnvironmentId, userId);
        Assert.isTrue(userEnvironmentDto == null ||
                        userEnvironmentDto.getDataSourceId() == DataSourceInfoConstant.NON_DATA_SOURCE_VALUE,
                "数据源配置不能为默认配置");

        Long dataSourceId = userEnvironmentDto.getDataSourceId();

        // 查询数据源信息
        DataSourceInfoDto dataSourceInfoDto =
                dataSourceInfoService.findDataSourceInfoDtoByDataSourceId(dataSourceId, useEnvironmentId);
        Assert.isTrue(dataSourceInfoDto == null, "数据源信息不存在");
        return dataSourceInfoDto;
    }
}
