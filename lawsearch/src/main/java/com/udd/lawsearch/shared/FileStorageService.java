package com.udd.lawsearch.shared;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface FileStorageService  {
    void uploadFile(MultipartFile file);
    InputStream getFileFromMilio(String bucketName, String objectName) throws Exception;
}
