package com.udd.lawsearch.elastic.search;

import com.udd.lawsearch.elastic.search.dto.BasicSearchDTO;
import com.udd.lawsearch.elastic.search.dto.LocationRequestDTO;
import com.udd.lawsearch.elastic.search.dto.SearchResult;
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

    @PostMapping("/basic")
    public ResponseEntity<SearchResult> basicSearch(@RequestBody BasicSearchDTO dto) throws IOException {
        SearchResult response = searchService.basicSearch(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/location")
    public ResponseEntity<SearchResult> locationSearch(@RequestBody LocationRequestDTO dto) throws IOException {
        SearchResult response = searchService.locationSearch(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
