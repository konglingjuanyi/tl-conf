package com.tiaoling.cloud.job.persistence;

import com.tiaoling.cloud.job.domain.FiredTrigger;
import com.tiaoling.cloud.job.domain.FiredTriggerKey;

public interface FiredTriggerMapper {
    int deleteByPrimaryKey(FiredTriggerKey key);

    int insert(FiredTrigger record);

    int insertSelective(FiredTrigger record);

    FiredTrigger selectByPrimaryKey(FiredTriggerKey key);

    int updateByPrimaryKeySelective(FiredTrigger record);

    int updateByPrimaryKey(FiredTrigger record);
}