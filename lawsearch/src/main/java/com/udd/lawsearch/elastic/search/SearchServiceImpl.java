package com.udd.lawsearch.elastic.search;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.udd.lawsearch.elastic.search.dto.BasicSearchDTO;
import com.udd.lawsearch.elastic.search.dto.ResultData;
import com.udd.lawsearch.elastic.search.dto.SearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService{
    private final ElasticsearchClient esClient;

    @Override
    public SearchResult basicSearch(BasicSearchDTO dto) throws IOException, RuntimeException {
        SearchResponse<ObjectNode> response = esClient.search(
                SearchRequest.of(s -> s
                        .index("contracts")
                        .query(q -> q
                                .match(t -> t
                                        .field(dto.getField())
                                        .query(dto.getValue())
                                )
                        )
                        .highlight(h -> h
                                .fields("content", f -> f
                                        .highlightQuery(hq -> hq
                                                .match(mq -> mq
                                                        .field("content")
                                                        .query(dto.getValue())
                                                )
                                        )
                                )
                        )
                ),
                ObjectNode.class
        );

        HitsMetadata<ObjectNode> hitsMetadata = response.hits();
        return createResponse(hitsMetadata);
    }

    private SearchResult createResponse(HitsMetadata<ObjectNode> hitsMetadata) {
        List<ResultData> responses = new ArrayList<>();
        List<Hit<ObjectNode>> searchHits = hitsMetadata.hits();

        for (Hit<ObjectNode> h: searchHits) {
            ResultData searchResponse = new ResultData();
            ObjectNode json = h.source();
            String name = Objects.requireNonNull(json).get("governmentName").asText();
            searchResponse.setGovernmentName(name);
            searchResponse.setGovernmentLevel(json.get("governmentLevel").asText());
            searchResponse.setSignatoryPersonName(json.get("signatoryPersonName").asText());
            searchResponse.setSignatoryPersonSurname(json.get("signatoryPersonSurname").asText());

            if (h.highlight().isEmpty()) {
                searchResponse.setHighlight(json.get("content").asText().substring(0, 150) + "...");
            } else {
                searchResponse.setHighlight("..." + h.highlight().get("content").get(0) + "...");
            }
            searchResponse.setContractId(h.id());

            responses.add(searchResponse);
        }

        return new SearchResult(responses, Objects.requireNonNull(hitsMetadata.total()).value());
    }
}