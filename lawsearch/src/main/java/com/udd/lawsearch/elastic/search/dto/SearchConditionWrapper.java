package com.udd.lawsearch.elastic.search.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchConditionWrapper {
    private BasicSearchDTO basicSearchDto;
    private BoolQueryDTO booleanQueryDto;

    public SearchCondition getCondition() {
        if (basicSearchDto != null) {
            return basicSearchDto;
        } else if (booleanQueryDto != null) {
            return booleanQueryDto;
        }
        throw new IllegalStateException("No search condition is set");
    }
}
