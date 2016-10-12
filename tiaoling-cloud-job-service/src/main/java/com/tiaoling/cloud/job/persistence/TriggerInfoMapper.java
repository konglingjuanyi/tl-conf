package com.tiaoling.cloud.job.persistence;

import com.tiaoling.cloud.job.domain.TriggerInfo;

import java.util.List;
import java.util.Map;

public interface TriggerInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TriggerInfo record);

    int insertSelective(TriggerInfo record);

    TriggerInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TriggerInfo record);

    int updateByPrimaryKeyWithBLOBs(TriggerInfo record);

    int updateByPrimaryKey(TriggerInfo record);

    List<TriggerInfo> selectPageList(Map<String,Object> params);

    int selectPageListCount(Map<String,Object> params);
}