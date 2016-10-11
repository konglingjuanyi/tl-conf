package com.tiaoling.cloud.job.persistence;

import com.tiaoling.cloud.job.domain.TriggerLogGlue;

public interface TriggerLogGlueMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TriggerLogGlue record);

    int insertSelective(TriggerLogGlue record);

    TriggerLogGlue selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TriggerLogGlue record);

    int updateByPrimaryKeyWithBLOBs(TriggerLogGlue record);

    int updateByPrimaryKey(TriggerLogGlue record);
}