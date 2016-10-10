package com.tiaoling.cloud.conf.persistence;

import com.tiaoling.cloud.conf.domain.ConfGroup;

import java.util.List;

public interface ConfGroupMapper {
    int deleteByPrimaryKey(String groupKey);

    int insert(ConfGroup record);

    int insertSelective(ConfGroup record);

    ConfGroup selectByPrimaryKey(String groupKey);

    int updateByPrimaryKeySelective(ConfGroup record);

    int updateByPrimaryKey(ConfGroup record);

    List<ConfGroup> findAll();
}