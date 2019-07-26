package com.cn.miao.storage.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.BucketInfo;
import com.aliyun.oss.model.OSSObject;
import com.cn.miao.storage.FileStorage;

import javax.annotation.PreDestroy;
import java.io.*;

/**
 * @title: AliyunFileStorage
 * @description: 阿里云文件上传
 * @author: dengmiao
 * @create: 2019-07-26 13:50
 **/
public class AliyunFileStorage implements FileStorage {

    /**
     * oss客户端
     */
    private OSSClient ossClient;

    /**
     * oss的bucket名称
     */
    private String bucketName;

    public AliyunFileStorage(OSSClient ossClient) {
        this.ossClient = ossClient;
    }

    @Override
    public void store(byte[] fileBytes, String key) {
        ossClient.putObject(bucketName, key, new ByteArrayInputStream(fileBytes));
    }

    @Override
    public void store(InputStream input, String key) {
        ossClient.putObject(bucketName, key, input);
    }

    @Override
    public byte[] getBytes(String key) {
        OSSObject ossObject = ossClient.getObject(bucketName, key);
        BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
        byte[] buf = new byte[1024];
        InputStream in = ossObject.getObjectContent();
        for (int n = 0; n != -1; ) {
            try {
                n = in.read(buf, 0, buf.length);
            } catch (IOException e) {
                throw new RuntimeException("download file error!");
            }
        }
        try {
            in.close();
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException("download file error!");
        }
        return buf;
    }

    @Override
    public void remove(String key) {
        ossClient.deleteObject(bucketName, key);
    }

    @Override
    public InputStream getInputStream(String key) {
        return ossClient.getObject(bucketName, key).getObjectContent();
    }

    @Override
    public String getDownloadUrl(String key) {
        BucketInfo bucketInfo = ossClient.getBucketInfo(this.getBucketName());
        Bucket bucket = bucketInfo.getBucket();
        return  "http://" + bucket.getName() + "." + bucket.getExtranetEndpoint() + "/" + key;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    @PreDestroy
    public void destroy() {
        ossClient.shutdown();
    }
}
