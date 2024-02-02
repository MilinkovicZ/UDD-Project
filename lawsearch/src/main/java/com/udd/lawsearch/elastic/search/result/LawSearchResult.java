package com.udd.lawsearch.elastic.search.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LawSearchResult {
    private List<LawResultData> results;
    private Long numberOfResults;
}
