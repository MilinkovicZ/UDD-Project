package com.udd.lawsearch.elastic.search.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LawResultData {
    private String content;
    private String highlight;
    private String lawId;
}
