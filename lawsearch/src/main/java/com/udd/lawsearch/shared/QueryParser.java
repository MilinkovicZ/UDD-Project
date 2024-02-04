package com.udd.lawsearch.shared;

import com.udd.lawsearch.elastic.search.dto.BasicSearchDTO;
import com.udd.lawsearch.elastic.search.dto.BoolQueryDTO;
import com.udd.lawsearch.elastic.search.dto.SearchConditionWrapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QueryParser {
    public SearchConditionWrapper parse(String query) {
        query = normalizeQuery(query);
        return parseCondition(query);
    }

    private SearchConditionWrapper parseCondition(String query) {
        if (query.contains(" OR ")) {
            return parseBooleanQuery("OR", query.split(" OR "));
        } else if (query.contains(" AND ")) {
            return parseBooleanQuery("AND", query.split(" AND "));
        } else if (query.startsWith(" NOT ")) {
            return parseNotQuery(query.substring(4));
        } else {
            return parseBasicSearch(query);
        }
    }

    private SearchConditionWrapper parseBooleanQuery(String operator, String[] parts) {
        BoolQueryDTO booleanQueryDto = new BoolQueryDTO();
        booleanQueryDto.setOperator(operator);
        List<SearchConditionWrapper> conditions = new ArrayList<>();

        for (String part : parts) {
            conditions.add(parseCondition(part.trim()));
        }

        booleanQueryDto.setBooleanQueryFields(conditions);
        return new SearchConditionWrapper(null, booleanQueryDto);
    }

    private SearchConditionWrapper parseNotQuery(String query) {
        BoolQueryDTO booleanQueryDto = new BoolQueryDTO();
        booleanQueryDto.setOperator("NOT");
        List<SearchConditionWrapper> conditions = new ArrayList<>();

        conditions.add(parseCondition(query.trim()));

        booleanQueryDto.setBooleanQueryFields(conditions);
        return new SearchConditionWrapper(null, booleanQueryDto);
    }

    private SearchConditionWrapper parseBasicSearch(String query) {
        String[] parts = query.split(":");
        String field = parts[0].trim();
        String value = parts[1].trim();
        boolean isPhrase = false;

        if (value.startsWith("\"") && value.endsWith("\"")) {
            isPhrase = true;
            value = value.substring(1, value.length() - 1);
        }

        BasicSearchDTO basicSearchDto = new BasicSearchDTO(field, value, isPhrase);
        return new SearchConditionWrapper(basicSearchDto, null);
    }

    private String normalizeQuery(String query) {
        return query.replaceAll("\\s{2,}", " ").trim();
    }
}
