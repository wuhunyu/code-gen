package com.wuhunyu.code_gen.system.data_source.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.wuhunyu.code_gen.common.domain.SelectData;
import com.wuhunyu.code_gen.common.sequence.SequenceInstance;
import com.wuhunyu.code_gen.system.data_source.domain.DataSourceInfo;
import com.wuhunyu.code_gen.system.data_source.domain.dto.DataSourceInfoDto;
import com.wuhunyu.code_gen.system.data_source.domain.query.DataSourceInfoQuery;
import com.wuhunyu.code_gen.system.data_source.domain.vo.DataSourceInfoVo;
import com.wuhunyu.code_gen.system.data_source.enums.DBTypeEnum;
import com.wuhunyu.code_gen.system.data_source.repository.DataSourceInfoRepository;
import com.wuhunyu.code_gen.system.data_source.service.DataSourceInfoService;
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
    public List<DataSourceInfoVo> pageDataSourceInfo(DataSourceInfoQuery dataSourceInfoQuery, Long userId) {
        List<DataSourceInfo> dataSourceInfos = dataSourceInfoRepository.pageDataSourceInfo(userId, dataSourceInfoQuery);
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
    public Long countDataSourceInfo(DataSourceInfoQuery dataSourceInfoQuery, Long userId) {
        Long size = dataSourceInfoRepository.countDataSourceInfo(userId,
                dataSourceInfoQuery.getStartDatetime(), dataSourceInfoQuery.getEndDatetime());
        return size == null ? 0L : size;
    }

    @Override
    public List<SelectData> listDataSourceInfoForSelect(Long userId) {
        List<DataSourceInfo> dataSourceInfos = dataSourceInfoRepository.listDataSourceInfos(userId);
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
    public void insertDataSourceInfo(DataSourceInfoDto dataSourceInfoDto, Long userId) {
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
        dataSourceInfoRepository.insertDataSourceInfo(dataSourceInfo, userId);
    }

    @Override
    public void updateDataSourceInfo(DataSourceInfoDto dataSourceInfoDto, Long userId) {
        // 查询是否存在
        DataSourceInfo sourceInfoByDataSourceInfo =
                dataSourceInfoRepository.findDataSourceInfoByDataSourceId(dataSourceInfoDto.getDataSourceId(), userId);

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
        dataSourceInfoRepository.updateDataSourceInfo(newDataSourceInfo, userId);
    }

    @Override
    public void deleteDataSourceInfoByDataSourceId(Long dataSourceId, Long userId) {
        // 查询是否存在
        DataSourceInfo sourceInfo = dataSourceInfoRepository.findDataSourceInfoByDataSourceId(dataSourceId, userId);
        // 执行删除
        if (sourceInfo == null) {
            return;
        }
        dataSourceInfoRepository.deleteDataSourceInfoByDataSourceId(dataSourceId, userId);
    }
}
