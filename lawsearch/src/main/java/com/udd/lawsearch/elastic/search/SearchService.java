package com.udd.lawsearch.elastic.search;

import com.udd.lawsearch.elastic.search.dto.BasicSearchDTO;
import com.udd.lawsearch.elastic.search.dto.SearchCondition;
import com.udd.lawsearch.elastic.search.result.LawSearchResult;
import com.udd.lawsearch.elastic.search.dto.LocationRequestDTO;
import com.udd.lawsearch.elastic.search.result.ContractSearchResult;

import javax.naming.directory.SearchResult;
import java.io.IOException;

public interface SearchService {
    ContractSearchResult basicSearch(BasicSearchDTO dto) throws IOException;
    ContractSearchResult locationSearch(LocationRequestDTO dto) throws IOException;
    ContractSearchResult advancedSearch(SearchCondition condition) throws IOException;
    LawSearchResult lawSearch(String value) throws IOException;
}
