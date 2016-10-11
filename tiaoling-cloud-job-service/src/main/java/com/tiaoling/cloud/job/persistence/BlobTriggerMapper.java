package com.tiaoling.cloud.job.persistence;

import com.tiaoling.cloud.job.domain.BlobTrigger;
import com.tiaoling.cloud.job.domain.BlobTriggerKey;

public interface BlobTriggerMapper {
    int deleteByPrimaryKey(BlobTriggerKey key);

    int insert(BlobTrigger record);

    int insertSelective(BlobTrigger record);

    BlobTrigger selectByPrimaryKey(BlobTriggerKey key);

    int updateByPrimaryKeySelective(BlobTrigger record);

    int updateByPrimaryKeyWithBLOBs(BlobTrigger record);
}