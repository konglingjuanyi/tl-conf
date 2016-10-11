package com.tiaoling.cloud.job.domain;

public class BlobTrigger extends BlobTriggerKey {
    private byte[] blobData;

    public byte[] getBlobData() {
        return blobData;
    }

    public void setBlobData(byte[] blobData) {
        this.blobData = blobData;
    }
}