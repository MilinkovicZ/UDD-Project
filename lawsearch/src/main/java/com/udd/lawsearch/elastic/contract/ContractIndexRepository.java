package com.udd.lawsearch.elastic.contract;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractIndexRepository extends ElasticsearchRepository<ContractIndex, String> {
}