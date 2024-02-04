package com.udd.lawsearch.elastic.search.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoolQueryDTO implements SearchCondition {
    private String operator;
    List<SearchConditionWrapper> booleanQueryFields;
}
