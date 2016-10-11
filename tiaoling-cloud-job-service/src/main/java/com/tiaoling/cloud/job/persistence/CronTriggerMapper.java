package com.tiaoling.cloud.job.persistence;

import com.tiaoling.cloud.job.domain.CronTrigger;
import com.tiaoling.cloud.job.domain.CronTriggerKey;

public interface CronTriggerMapper {
    int deleteByPrimaryKey(CronTriggerKey key);

    int insert(CronTrigger record);

    int insertSelective(CronTrigger record);

    CronTrigger selectByPrimaryKey(CronTriggerKey key);

    int updateByPrimaryKeySelective(CronTrigger record);

    int updateByPrimaryKey(CronTrigger record);
}