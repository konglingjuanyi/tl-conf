package com.tiaoling.cloud.job.persistence;

import com.tiaoling.cloud.job.domain.TriggerGroup;

import java.util.List;

public interface TriggerGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TriggerGroup record);

    int insertSelective(TriggerGroup record);

    TriggerGroup selectByPrimaryKey(Integer id);

    List<TriggerGroup> findAll();

    int updateByPrimaryKeySelective(TriggerGroup record);

    int updateByPrimaryKey(TriggerGroup record);
}