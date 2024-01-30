package com.udd.lawsearch.elastic.law;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LawIndexService {
    void create(List<MultipartFile> laws) throws Exception;
}
