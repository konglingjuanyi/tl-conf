package com.tiaoling.cloud.job.persistence;

import com.tiaoling.cloud.job.domain.SimpropTrigger;
import com.tiaoling.cloud.job.domain.SimpropTriggerKey;

public interface SimpropTriggerMapper {
    int deleteByPrimaryKey(SimpropTriggerKey key);

    int insert(SimpropTrigger record);

    int insertSelective(SimpropTrigger record);

    SimpropTrigger selectByPrimaryKey(SimpropTriggerKey key);

    int updateByPrimaryKeySelective(SimpropTrigger record);

    int updateByPrimaryKey(SimpropTrigger record);
}