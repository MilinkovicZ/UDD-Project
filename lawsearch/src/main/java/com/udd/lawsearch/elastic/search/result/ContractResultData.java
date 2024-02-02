package com.udd.lawsearch.elastic.search.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContractResultData {
    private String governmentName;
    private String governmentLevel;
    private String signatoryPersonName;
    private String signatoryPersonSurname;
    private String highlight;
    private String contractId;
}

