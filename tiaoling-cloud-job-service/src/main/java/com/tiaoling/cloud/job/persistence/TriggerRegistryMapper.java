package com.tiaoling.cloud.job.persistence;

import com.tiaoling.cloud.job.domain.TriggerRegistry;

public interface TriggerRegistryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TriggerRegistry record);

    int insertSelective(TriggerRegistry record);

    TriggerRegistry selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TriggerRegistry record);

    int updateByPrimaryKey(TriggerRegistry record);
}