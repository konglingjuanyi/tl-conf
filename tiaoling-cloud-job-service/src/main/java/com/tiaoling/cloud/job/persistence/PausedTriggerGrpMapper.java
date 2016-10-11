package com.tiaoling.cloud.job.persistence;

import com.tiaoling.cloud.job.domain.PausedTriggerGrpKey;

public interface PausedTriggerGrpMapper {
    int deleteByPrimaryKey(PausedTriggerGrpKey key);

    int insert(PausedTriggerGrpKey record);

    int insertSelective(PausedTriggerGrpKey record);
}