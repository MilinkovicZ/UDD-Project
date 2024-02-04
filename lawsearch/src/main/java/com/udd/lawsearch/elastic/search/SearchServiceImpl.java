package com.udd.lawsearch.elastic.search;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.GeoLocation;
import co.elastic.clients.elasticsearch._types.query_dsl.GeoDistanceQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.udd.lawsearch.elastic.search.dto.*;
import com.udd.lawsearch.elastic.search.result.ContractResultData;
import com.udd.lawsearch.elastic.search.result.ContractSearchResult;
import com.udd.lawsearch.elastic.search.result.LawResultData;
import com.udd.lawsearch.elastic.search.result.LawSearchResult;
import com.udd.lawsearch.shared.GeoLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.naming.directory.SearchResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService{
    private final ElasticsearchClient esClient;

    //region Contracts
    public ContractSearchResult basicSearch(BasicSearchDTO dto) throws IOException {
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

    public ContractSearchResult locationSearch(LocationRequestDTO dto) throws IOException {
        var coordinates = GeoLocationService.getCoordinates(dto.getAddress());

        GeoDistanceQuery geoDistanceQuery = GeoDistanceQuery.of(g -> g
                .field("governmentAddress")
                .distance(dto.getRadius().intValue() + "km")
                .location(GeoLocation.of(gl -> gl
                        .latlon(ll -> {
                                    assert coordinates != null;
                                    return ll
                                            .lat(coordinates.get(0))
                                            .lon(coordinates.get(1));
                                }
                        )
                ))
        );

        SearchRequest searchRequest = SearchRequest.of(s -> s
                .index("contracts")
                .query(Query.of(q -> q
                        .bool(b -> b
                                .filter(f -> f
                                        .geoDistance(geoDistanceQuery)
                                )
                        )
                ))
        );

        SearchResponse<ObjectNode> searchResponse = esClient.search(searchRequest, ObjectNode.class);

        HitsMetadata<ObjectNode> hitsMetadata = searchResponse.hits();
        return createResponse(hitsMetadata);
    }

    public ContractSearchResult advancedSearch(SearchCondition condition) throws IOException {
        Query query = buildQuery(condition);

        SearchResponse<ObjectNode> response = esClient.search(
                SearchRequest.of(s -> s
                        .index("contracts")
                        .query(query)
                        .highlight(h -> h
                                .fields("content", f -> f)
                        )
                ),
                ObjectNode.class
        );

        HitsMetadata<ObjectNode> hitsMetadata = response.hits();
        return createResponse(hitsMetadata);
    }

    private Query buildQuery(SearchCondition condition) {
        if (condition instanceof BasicSearchDTO simpleCondition) {
            if (((BasicSearchDTO) condition).getIsPhrase())
            {
                return Query.of(q -> q
                        .matchPhrase(m -> m
                                .field(simpleCondition.getField())
                                .query(simpleCondition.getValue())
                        )
                );
            }
            return Query.of(q -> q
                    .match(m -> m
                            .field(simpleCondition.getField())
                            .query(simpleCondition.getValue())
                    )
            );
        } else if (condition instanceof BoolQueryDTO booleanCondition) {
            List<Query> innerQueries = new ArrayList<>();
            for (SearchConditionWrapper innerConditionWrapper : booleanCondition.getBooleanQueryFields()) {
                innerQueries.add(buildQuery(innerConditionWrapper.getCondition()));
            }

            switch (booleanCondition.getOperator().toUpperCase()) {
                case "AND":
                    return Query.of(q -> q.bool(b -> b.must(innerQueries)));
                case "OR":
                    return Query.of(q -> q.bool(b -> b.should(innerQueries).minimumShouldMatch("1")));
                case "NOT":
                    if (innerQueries.size() == 1) {
                        return Query.of(q -> q.bool(b -> b.mustNot(innerQueries.get(0))));
                    } else {
                        throw new IllegalArgumentException("NOT operator should have only one condition");
                    }
                default:
                    throw new IllegalArgumentException("Unsupported operator: " + booleanCondition.getOperator());
            }
        } else {
            throw new IllegalArgumentException("Unknown condition type: " + condition.getClass().getName());
        }
    }

    private ContractSearchResult createResponse(HitsMetadata<ObjectNode> hitsMetadata) {
        List<ContractResultData> responses = new ArrayList<>();
        List<Hit<ObjectNode>> searchHits = hitsMetadata.hits();

        for (Hit<ObjectNode> h: searchHits) {
            ContractResultData searchResponse = new ContractResultData();
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

        return new ContractSearchResult(responses, Objects.requireNonNull(hitsMetadata.total()).value());
    }
    //endregion
    //region Laws
    public LawSearchResult lawSearch(String value) throws IOException {
        SearchResponse<ObjectNode> response = esClient.search(
                SearchRequest.of(s -> s
                        .index("laws")
                        .query(q -> q
                                .match(t -> t
                                        .field("content")
                                        .query(value)
                                )
                        )
                        .highlight(h -> h
                                .fields("content", f -> f
                                        .highlightQuery(hq -> hq
                                                .match(mq -> mq
                                                        .field("content")
                                                        .query(value)
                                                )
                                        )
                                )
                        )
                ),
                ObjectNode.class
        );

        HitsMetadata<ObjectNode> hitsMetadata = response.hits();
        List<Hit<ObjectNode>> searchHits = hitsMetadata.hits();
        List<LawResultData> responses = new ArrayList<>();

        for (Hit<ObjectNode> h: searchHits) {
            LawResultData searchResponse = new LawResultData();
            ObjectNode json = h.source();
            assert json != null;
            searchResponse.setContent(json.get("content").asText());

            if (h.highlight().isEmpty()) {
                searchResponse.setHighlight(json.get("content").asText().substring(0, 150) + "...");
            } else {
                searchResponse.setHighlight("..." + h.highlight().get("content").get(0) + "...");
            }
            searchResponse.setLawId(h.id());

            responses.add(searchResponse);
        }

        return new LawSearchResult(responses, Objects.requireNonNull(hitsMetadata.total()).value());
    }
    //endregion

}