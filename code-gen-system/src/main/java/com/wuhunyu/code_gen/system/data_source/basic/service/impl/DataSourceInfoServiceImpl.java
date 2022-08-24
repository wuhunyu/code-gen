package com.wuhunyu.code_gen.system.data_source.basic.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.wuhunyu.code_gen.common.domain.SelectData;
import com.wuhunyu.code_gen.common.sequence.SequenceInstance;
import com.wuhunyu.code_gen.system.data_source.basic.domain.DataSourceInfo;
import com.wuhunyu.code_gen.system.data_source.basic.domain.dto.DataSourceInfoDto;
import com.wuhunyu.code_gen.system.data_source.basic.domain.query.DataSourceInfoQuery;
import com.wuhunyu.code_gen.system.data_source.basic.domain.vo.DataSourceInfoVo;
import com.wuhunyu.code_gen.system.data_source.basic.enums.DBTypeEnum;
import com.wuhunyu.code_gen.system.data_source.basic.repository.DataSourceInfoRepository;
import com.wuhunyu.code_gen.system.data_source.basic.service.DataSourceInfoService;
import com.wuhunyu.code_gen.system.data_source.dynamic.utils.DataSourceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 数据源 处理实现
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/19 17:30
 */

@Service("dataSourceInfoService")
@Primary
@RequiredArgsConstructor
@Slf4j
public class DataSourceInfoServiceImpl implements DataSourceInfoService {

    private final DataSourceInfoRepository dataSourceInfoRepository;

    @Override
    public List<DataSourceInfoVo> pageDataSourceInfo(DataSourceInfoQuery dataSourceInfoQuery, Long useEnvironmentId) {
        List<DataSourceInfo> dataSourceInfos = dataSourceInfoRepository.pageDataSourceInfo(useEnvironmentId, dataSourceInfoQuery);
        if (CollUtil.isEmpty(dataSourceInfos)) {
            return Collections.emptyList();
        }
        // 类型转换
        return dataSourceInfos.stream()
                .map(dataSourceInfo -> new DataSourceInfoVo(
                        dataSourceInfo.getDataSourceId(),
                        DBTypeEnum.findTypeNameByType(dataSourceInfo.getDbType()),
                        dataSourceInfo.getConnectionName(),
                        dataSourceInfo.getConnectionUrl(),
                        dataSourceInfo.getUserName(),
                        dataSourceInfo.getPassword(),
                        dataSourceInfo.getUpdateDatetime()))
                .collect(Collectors.toList());
    }

    @Override
    public Long countDataSourceInfo(DataSourceInfoQuery dataSourceInfoQuery, Long useEnvironmentId) {
        Long size = dataSourceInfoRepository.countDataSourceInfo(useEnvironmentId,
                dataSourceInfoQuery.getStartDatetime(), dataSourceInfoQuery.getEndDatetime());
        return size == null ? 0L : size;
    }

    @Override
    public List<SelectData> listDataSourceInfoForSelect(Long useEnvironmentId) {
        List<DataSourceInfo> dataSourceInfos = dataSourceInfoRepository.listDataSourceInfos(useEnvironmentId);
        if (CollUtil.isEmpty(dataSourceInfos)) {
            return Collections.emptyList();
        }

        return dataSourceInfos.stream()
                .map(dataSourceInfo -> new SelectData(
                        dataSourceInfo.getConnectionName(),
                        dataSourceInfo.getDataSourceId().toString()))
                .collect(Collectors.toList());
    }

    @Override
    public DataSourceInfoDto findDataSourceInfoDtoByDataSourceId(Long dataSourceId, Long useEnvironmentId) {
        DataSourceInfo dataSourceInfo = dataSourceInfoRepository.findDataSourceInfoByDataSourceId(dataSourceId, useEnvironmentId);
        if (dataSourceInfo == null) {
            return null;
        }
        return new DataSourceInfoDto(
                dataSourceInfo.getDataSourceId(),
                dataSourceInfo.getDbType(),
                dataSourceInfo.getConnectionName(),
                dataSourceInfo.getConnectionUrl(),
                dataSourceInfo.getUserName(),
                dataSourceInfo.getPassword());
    }

    @Override
    public void insertDataSourceInfo(DataSourceInfoDto dataSourceInfoDto, Long useEnvironmentId) {
        // 构建 数据源记录
        DataSourceInfo dataSourceInfo = new DataSourceInfo(
                SequenceInstance.INSTANCE.nextId(),
                dataSourceInfoDto.getDbType(),
                dataSourceInfoDto.getConnectionName(),
                dataSourceInfoDto.getConnectionUrl(),
                dataSourceInfoDto.getUserName(),
                dataSourceInfoDto.getPassword(),
                LocalDateTime.now());

        // 新增 数据源
        dataSourceInfoRepository.insertDataSourceInfo(dataSourceInfo, useEnvironmentId);
    }

    @Override
    public void updateDataSourceInfo(DataSourceInfoDto dataSourceInfoDto, Long useEnvironmentId) {
        // 查询是否存在
        DataSourceInfo sourceInfoByDataSourceInfo =
                dataSourceInfoRepository.findDataSourceInfoByDataSourceId(dataSourceInfoDto.getDataSourceId(), useEnvironmentId);

        // 构建 修改数据记录
        if (sourceInfoByDataSourceInfo == null) {
            return;
        }
        DataSourceInfo newDataSourceInfo = new DataSourceInfo(
                dataSourceInfoDto.getDataSourceId(),
                Objects.equals(dataSourceInfoDto.getDbType(), sourceInfoByDataSourceInfo.getDbType()) ?
                        null : dataSourceInfoDto.getDbType(),
                Objects.equals(dataSourceInfoDto.getConnectionName(), sourceInfoByDataSourceInfo.getConnectionName()) ?
                        null : dataSourceInfoDto.getConnectionName(),
                Objects.equals(dataSourceInfoDto.getConnectionUrl(), sourceInfoByDataSourceInfo.getConnectionUrl()) ?
                        null : dataSourceInfoDto.getConnectionUrl(),
                Objects.equals(dataSourceInfoDto.getUserName(), sourceInfoByDataSourceInfo.getUserName()) ?
                        null : dataSourceInfoDto.getUserName(),
                Objects.equals(dataSourceInfoDto.getPassword(), sourceInfoByDataSourceInfo.getPassword()) ?
                        null : dataSourceInfoDto.getPassword(),
                LocalDateTime.now());
        // 执行修改
        dataSourceInfoRepository.updateDataSourceInfo(newDataSourceInfo, useEnvironmentId);
    }

    @Override
    public void deleteDataSourceInfoByDataSourceId(Long dataSourceId, Long useEnvironmentId) {
        // 查询是否存在
        DataSourceInfo sourceInfo = dataSourceInfoRepository.findDataSourceInfoByDataSourceId(dataSourceId, useEnvironmentId);
        // 执行删除
        if (sourceInfo == null) {
            return;
        }
        dataSourceInfoRepository.deleteDataSourceInfoByDataSourceId(dataSourceId, useEnvironmentId);
    }

    @Override
    public boolean checkConnection(DataSourceInfoDto dataSourceInfoDto) {
        return DataSourceUtil.checkDataSourceConnection(
                dataSourceInfoDto.getConnectionUrl(),
                dataSourceInfoDto.getUserName(),
                dataSourceInfoDto.getPassword());
    }
}
