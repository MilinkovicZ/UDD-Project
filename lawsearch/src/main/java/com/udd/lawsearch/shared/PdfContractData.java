package com.udd.lawsearch.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PdfContractData {
    private String name;
    private String lastName;
    private String content;
}
