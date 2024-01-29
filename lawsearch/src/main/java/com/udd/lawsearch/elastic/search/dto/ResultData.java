package com.udd.lawsearch.elastic.search.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResultData {
    private String governmentName;
    private String governmentLevel;
    private String signatoryPersonName;
    private String signatoryPersonSurname;
    private String highlight;
    private String contractId;
}