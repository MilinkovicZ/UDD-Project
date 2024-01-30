package com.udd.lawsearch.elastic.search;

import com.udd.lawsearch.elastic.search.dto.BasicSearchDTO;
import com.udd.lawsearch.elastic.search.dto.LocationRequestDTO;
import com.udd.lawsearch.elastic.search.dto.SearchResult;

import java.io.IOException;

public interface SearchService {
    SearchResult basicSearch(BasicSearchDTO dto) throws IOException, RuntimeException;
    SearchResult locationSearch(LocationRequestDTO dto) throws IOException;
}
