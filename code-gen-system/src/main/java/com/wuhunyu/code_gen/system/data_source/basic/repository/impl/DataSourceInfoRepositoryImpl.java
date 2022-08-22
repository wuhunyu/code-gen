package com.wuhunyu.code_gen.system.data_source.basic.repository.impl;

import cn.hutool.core.collection.CollUtil;
import com.wuhunyu.code_gen.system.data_source.basic.domain.DataSourceInfo;
import com.wuhunyu.code_gen.system.data_source.basic.domain.query.DataSourceInfoQuery;
import com.wuhunyu.code_gen.system.data_source.basic.repository.DataSourceInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.wuhunyu.code_gen.system.constants.RedisKeyConstant.DataSourceKeys.*;
import static com.wuhunyu.code_gen.common.utils.RedisUtil.*;
import static com.wuhunyu.code_gen.common.constants.CommonConstant.*;

/**
 * 数据源 数据访问实现
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/19 13:39
 */

@Repository("dataSourceInfoRepository")
@Primary
@Slf4j
@RequiredArgsConstructor
public class DataSourceInfoRepositoryImpl implements DataSourceInfoRepository {

    @Override
    public List<DataSourceInfo> pageDataSourceInfo(Long userId, DataSourceInfoQuery dataSourceInfoQuery) {
        // 日期时间查询条件
        long startDatetime = dataSourceInfoQuery.getStartDatetime() == null ?
                MIN_IN_VALID_DATE_TIME :
                dataSourceInfoQuery.getStartDatetime()
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
                        .toEpochMilli();

        long endDatetime = dataSourceInfoQuery.getEndDatetime() == null ?
                MAX_IN_VALID_DATE_TIME :
                dataSourceInfoQuery.getEndDatetime()
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
                        .toEpochMilli();

        // 获取数据源id集合
        Set<ZSetOperations.TypedTuple<String>> typedTuples =
                zRangeByScore(this.findDataSourceInfoSetKey(userId),
                        startDatetime,
                        endDatetime,
                        dataSourceInfoQuery.getStartPage() - 1L,
                        dataSourceInfoQuery.getSize());
        if (CollUtil.isEmpty(typedTuples)) {
            return Collections.emptyList();
        }

        List<String> dataSourceIds = typedTuples.stream()
                .map(ZSetOperations.TypedTuple::getValue)
                .collect(Collectors.toList());
        // 循环查询具体的数据源信息
        List<DataSourceInfo> list = new ArrayList<>(dataSourceIds.size());
        for (String dataSourceId : dataSourceIds) {
            list.add(hGetAll(this.findDataSourceInfoMapKey(dataSourceId), DataSourceInfo.class));
        }
        return list;
    }

    @Override
    public Long countDataSourceInfo(Long userId, LocalDateTime startDatetime, LocalDateTime endDatetime) {
        // 日期时间查询条件
        long startDatetimeVal = startDatetime == null ?
                MIN_IN_VALID_DATE_TIME :
                startDatetime.atZone(ZoneId.systemDefault())
                        .toInstant()
                        .toEpochMilli();
        long endDatetimeVal = endDatetime == null ?
                MAX_IN_VALID_DATE_TIME :
                endDatetime.atZone(ZoneId.systemDefault())
                        .toInstant()
                        .toEpochMilli();

        return countZSetByScore(this.findDataSourceInfoSetKey(userId), startDatetimeVal, endDatetimeVal);
    }

    @Override
    public List<DataSourceInfo> listDataSourceInfos(Long userId) {
        Set<String> dataSourceIds = zRange(this.findDataSourceInfoSetKey(userId));
        if (CollUtil.isEmpty(dataSourceIds)) {
            return Collections.emptyList();
        }

        return dataSourceIds.stream()
                .map(dataSourceId -> hGetAll(this.findDataSourceInfoMapKey(dataSourceId), DataSourceInfo.class))
                .collect(Collectors.toList());
    }

    @Override
    public DataSourceInfo findDataSourceInfoByDataSourceId(Long dataSourceId, Long userId) {
        boolean exists = zExists(this.findDataSourceInfoSetKey(userId), dataSourceId.toString());
        if (!exists) {
            return null;
        }
        return hGetAll(this.findDataSourceInfoMapKey(dataSourceId), DataSourceInfo.class);
    }

    @Override
    public void insertDataSourceInfo(DataSourceInfo dataSourceInfo, Long userId) {
        // 新增 数据源信息
        hSet(this.findDataSourceInfoMapKey(dataSourceInfo.getDataSourceId()), dataSourceInfo);
        // 新增 用户&数据源关联
        zAdd(this.findDataSourceInfoSetKey(userId), dataSourceInfo.getDataSourceId().toString());
    }

    @Override
    public void updateDataSourceInfo(DataSourceInfo dataSourceInfo, Long userId) {
        // 更新 用户&数据源关联 的更新日期时间
        zAdd(this.findDataSourceInfoSetKey(userId), dataSourceInfo.getDataSourceId().toString());
        // 数据源信息
        hSet(this.findDataSourceInfoMapKey(dataSourceInfo.getDataSourceId()), dataSourceInfo);
    }

    @Override
    public void deleteDataSourceInfoByDataSourceId(Long dataSourceId, Long userId) {
        // 删除 用户&数据源关联
        zRemove(this.findDataSourceInfoSetKey(userId), dataSourceId.toString());
        // 删除 数据源信息
        delete(this.findDataSourceInfoMapKey(dataSourceId));
    }

    /**
     * 查询 用户&数据源 关系key
     *
     * @param userId 用户id
     * @return 用户&数据源 关系key
     */
    private String findDataSourceInfoSetKey(Long userId) {
        return DATA_SOURCE_SET + userId;
    }

    /**
     * 查询 数据源 信息key
     *
     * @param dataSourceId 数据源id
     * @return 数据源 信息key
     */
    private String findDataSourceInfoMapKey(Long dataSourceId) {
        return DATA_SOURCE_MAP + dataSourceId;
    }

    /**
     * 查询 数据源 信息key
     *
     * @param dataSourceId 数据源id
     * @return 数据源 信息key
     */
    private String findDataSourceInfoMapKey(String dataSourceId) {
        return DATA_SOURCE_MAP + dataSourceId;
    }

}
