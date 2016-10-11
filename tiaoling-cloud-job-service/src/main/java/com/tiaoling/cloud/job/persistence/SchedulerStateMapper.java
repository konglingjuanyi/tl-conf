package com.tiaoling.cloud.job.persistence;

import com.tiaoling.cloud.job.domain.SchedulerState;
import com.tiaoling.cloud.job.domain.SchedulerStateKey;

public interface SchedulerStateMapper {
    int deleteByPrimaryKey(SchedulerStateKey key);

    int insert(SchedulerState record);

    int insertSelective(SchedulerState record);

    SchedulerState selectByPrimaryKey(SchedulerStateKey key);

    int updateByPrimaryKeySelective(SchedulerState record);

    int updateByPrimaryKey(SchedulerState record);
}