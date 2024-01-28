package com.udd.lawsearch.elastic.contract;

import com.udd.lawsearch.shared.Address;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "contracts")
public class ContractIndex {
    @Id
    private String id;
    @Field(type = FieldType.Text, searchAnalyzer = "serbian", analyzer = "serbian", store = true)
    private String signatoryName;
    @Field(type = FieldType.Text, searchAnalyzer = "serbian", analyzer = "serbian", store = true)
    private String signatoryLastName;
    @Field(type = FieldType.Text, searchAnalyzer = "serbian", analyzer = "serbian", store = true)
    private String governmentName;
    @Field(type = FieldType.Text, searchAnalyzer = "serbian", analyzer = "serbian", store = true)
    private String governmentLevel;
    @GeoPointField
    private GeoPoint governmentAddress;
    @Field(type = FieldType.Text, searchAnalyzer = "serbian", analyzer = "serbian")
    private String content;

    public ContractIndex(String signatoryName, String signatoryLastName, String governmentName, String governmentLevel, String content) {
        this.signatoryName = signatoryName;
        this.signatoryLastName = signatoryLastName;
        this.governmentName = governmentName;
        this.governmentLevel = governmentLevel;
        this.governmentAddress = new GeoPoint(30, 30);
        this.content = content;
    }
}
