package com.udd.lawsearch.elastic.law;

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
@Document(indexName = "laws")
public class LawIndex {
    @Id
    private String id;
    @Field(type = FieldType.Text, searchAnalyzer = "serbian", analyzer = "serbian")
    private String content;
    @Field(type = FieldType.Text, searchAnalyzer = "serbian", analyzer = "serbian")
    private String filename;

    public LawIndex(String content, String filename) {
        this.content = content;
        this.filename = filename;
    }
}
