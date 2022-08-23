package com.wuhunyu.code_gen.system.data_source.dynamic.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.wuhunyu.code_gen.common.domain.SelectData;
import com.wuhunyu.code_gen.common.utils.Assert;
import com.wuhunyu.code_gen.system.data_source.basic.domain.dto.DataSourceInfoDto;
import com.wuhunyu.code_gen.system.data_source.basic.service.DataSourceInfoService;
import com.wuhunyu.code_gen.system.data_source.dynamic.domain.TableData;
import com.wuhunyu.code_gen.system.data_source.dynamic.domain.query.TableQuery;
import com.wuhunyu.code_gen.system.data_source.dynamic.domain.vo.TableDataVo;
import com.wuhunyu.code_gen.system.data_source.dynamic.mapper.DynamicDataSourceMapper;
import com.wuhunyu.code_gen.system.data_source.dynamic.service.DynamicDataSourceService;
import com.wuhunyu.code_gen.system.data_source.dynamic.utils.DataSourceUtil;
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

    @Override
    public List<SelectData> listNewTables(Long dataSourceId, Long userId) {
        // 查询数据源信息
        DataSourceInfoDto dataSourceInfoDto =
                dataSourceInfoService.findDataSourceInfoDtoByDataSourceId(dataSourceId, userId);
        Assert.isTrue(dataSourceInfoDto == null, "数据源信息不存在");

        // 创建数据源连接
        if (!DataSourceUtil.existsDataSource(dataSourceId)) {
            DataSourceUtil.addDataSource(dataSourceInfoDto);
        }

        // TODO 修改为 lambda 表达式
        try {
            // 手动切换数据源
            DataSourceUtil.openDataSource(dataSourceId);

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
        } finally {
            DataSourceUtil.closeDataSource(dataSourceId);
        }
    }

    @Override
    public void importTableData(Long dataSourceId, Long userId) {

    }

    @Override
    public List<TableDataVo> pageTableDataVos(TableQuery tableQuery, Long userId) {
        return null;
    }

    @Override
    public Long countTableData(TableQuery tableQuery, Long userId) {
        return null;
    }
}
