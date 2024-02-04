package com.udd.lawsearch.elastic.contract;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "contracts")
public class ContractIndex {
    @Id
    private String id;
    @MultiField(
            mainField = @Field(type = FieldType.Text, searchAnalyzer = "serbian", analyzer = "serbian"),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword, ignoreAbove = 256)
            }
    )
    private String signatoryPersonName;
    @MultiField(
            mainField = @Field(type = FieldType.Text, searchAnalyzer = "serbian", analyzer = "serbian"),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword, ignoreAbove = 256)
            }
    )
    private String signatoryPersonSurname;
    @MultiField(
            mainField = @Field(type = FieldType.Text, searchAnalyzer = "serbian", analyzer = "serbian"),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword, ignoreAbove = 256)
            }
    )
    private String governmentName;
    @MultiField(
            mainField = @Field(type = FieldType.Text, searchAnalyzer = "serbian", analyzer = "serbian"),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword, ignoreAbove = 256)
            }
    )
    private String governmentLevel;
    @GeoPointField
    private GeoPoint governmentAddress;
    @Field(type = FieldType.Text, searchAnalyzer = "serbian", analyzer = "serbian")
    private String content;
    @Field(type = FieldType.Text, searchAnalyzer = "serbian", analyzer = "serbian")
    private String filename;

    public ContractIndex(String signatoryPersonName, String signatoryPersonSurname, String governmentName, String governmentLevel, String content, double lat, double lon, String filename) {
        this.signatoryPersonName = signatoryPersonName;
        this.signatoryPersonSurname = signatoryPersonSurname;
        this.governmentName = governmentName;
        this.governmentLevel = governmentLevel;
        this.governmentAddress = new GeoPoint(lat, lon);
        this.content = content;
        this.filename = filename;
    }
}
