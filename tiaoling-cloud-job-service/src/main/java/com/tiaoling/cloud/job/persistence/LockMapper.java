package com.tiaoling.cloud.job.persistence;

import com.tiaoling.cloud.job.domain.LockKey;

public interface LockMapper {
    int deleteByPrimaryKey(LockKey key);

    int insert(LockKey record);

    int insertSelective(LockKey record);
}