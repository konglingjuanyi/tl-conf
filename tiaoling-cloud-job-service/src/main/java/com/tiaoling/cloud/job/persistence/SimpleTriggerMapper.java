package com.tiaoling.cloud.job.persistence;

import com.tiaoling.cloud.job.domain.SimpleTrigger;
import com.tiaoling.cloud.job.domain.SimpleTriggerKey;

public interface SimpleTriggerMapper {
    int deleteByPrimaryKey(SimpleTriggerKey key);

    int insert(SimpleTrigger record);

    int insertSelective(SimpleTrigger record);

    SimpleTrigger selectByPrimaryKey(SimpleTriggerKey key);

    int updateByPrimaryKeySelective(SimpleTrigger record);

    int updateByPrimaryKey(SimpleTrigger record);
}