package com.udd.lawsearch.elastic.search;

import com.udd.lawsearch.elastic.search.dto.BasicSearchDTO;
import com.udd.lawsearch.elastic.search.dto.SearchConditionWrapper;
import com.udd.lawsearch.elastic.search.result.LawSearchResult;
import com.udd.lawsearch.elastic.search.dto.LocationRequestDTO;
import com.udd.lawsearch.elastic.search.result.ContractSearchResult;
import com.udd.lawsearch.shared.QueryParser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.directory.SearchResult;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/search")
public class SearchController {
    private final SearchService searchService;
    private final QueryParser queryParser;

    @PostMapping("/contract/basic")
    public ResponseEntity<ContractSearchResult> basicSearch(@RequestBody BasicSearchDTO dto) throws IOException {
        ContractSearchResult response = searchService.basicSearch(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/contract/location")
    public ResponseEntity<ContractSearchResult> locationSearch(@RequestBody LocationRequestDTO dto) throws IOException {
        ContractSearchResult response = searchService.locationSearch(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/contract/advanced")
    public ResponseEntity<ContractSearchResult> advancedSearch(@RequestBody String query) throws IOException {

        SearchConditionWrapper wrapper = queryParser.parse(query);
        ContractSearchResult response = searchService.advancedSearch(wrapper.getCondition());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/law")
    public ResponseEntity<LawSearchResult> lawSearch(@RequestBody String value) throws  IOException {
        LawSearchResult response = searchService.lawSearch(value);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
