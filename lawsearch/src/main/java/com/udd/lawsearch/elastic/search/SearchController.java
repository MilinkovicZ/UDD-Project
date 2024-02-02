package com.udd.lawsearch.elastic.search;

import com.udd.lawsearch.elastic.search.dto.BasicSearchDTO;
import com.udd.lawsearch.elastic.search.result.LawSearchResult;
import com.udd.lawsearch.elastic.search.dto.LocationRequestDTO;
import com.udd.lawsearch.elastic.search.result.ContractSearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/search")
public class SearchController {
    private final SearchService searchService;

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

    @PostMapping("/law")
    public ResponseEntity<LawSearchResult> lawSearch(@RequestBody String value) throws  IOException {
        LawSearchResult response = searchService.lawSearch(value);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
