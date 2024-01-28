package com.udd.lawsearch.elastic.contract;

import com.udd.lawsearch.government.dto.GovernmentDTO;

public interface ContractIndexService {
    void create(GovernmentDTO governmentDTO, String governmentTypeName) throws Exception;
}
