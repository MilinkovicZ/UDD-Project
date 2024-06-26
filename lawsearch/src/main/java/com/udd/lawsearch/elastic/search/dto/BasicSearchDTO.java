package com.udd.lawsearch.elastic.search.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicSearchDTO implements SearchCondition {
    private String field;
    private String value;
    private Boolean isPhrase;
}
