package com.tiaoling.cloud.job.persistence;

import com.tiaoling.cloud.job.domain.TriggerLog;

public interface TriggerLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TriggerLog record);

    int insertSelective(TriggerLog record);

    TriggerLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TriggerLog record);

    int updateByPrimaryKey(TriggerLog record);
}