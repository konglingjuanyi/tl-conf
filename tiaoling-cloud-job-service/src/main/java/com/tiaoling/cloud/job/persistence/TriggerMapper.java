package com.tiaoling.cloud.job.persistence;

import com.tiaoling.cloud.job.domain.Trigger;
import com.tiaoling.cloud.job.domain.TriggerKey;

public interface TriggerMapper {
    int deleteByPrimaryKey(TriggerKey key);

    int insert(Trigger record);

    int insertSelective(Trigger record);

    Trigger selectByPrimaryKey(TriggerKey key);

    int updateByPrimaryKeySelective(Trigger record);

    int updateByPrimaryKeyWithBLOBs(Trigger record);

    int updateByPrimaryKey(Trigger record);
}