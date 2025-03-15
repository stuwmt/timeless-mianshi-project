package com.timeless.mianshi.manager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class ESTest {
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    private final String INDEX_NAME = "test_index";

    @Test
    public void indexDocument() {
        Map<String, Object> doc = new HashMap<>();
        doc.put("title", "Elasticsearch Introduction");
        doc.put("content", "Learn Elasticsearch basics and advanced usage.");
        doc.put("tags", "elasticsearch,search");
        doc.put("answer", "Yes");
        doc.put("userId", 1L);
        doc.put("editTime", "2023-09-01 10:00:00");
        doc.put("createTime", "2023-09-01 09:00:00");
        doc.put("updateTime", "2023-09-01 09:10:00");
        doc.put("isDelete", false);

        IndexQuery build = new IndexQueryBuilder().withId("1").withObject(doc).build();
        String documentId = elasticsearchRestTemplate.index(build, IndexCoordinates.of(INDEX_NAME));
        System.out.println("Document ID: " + documentId);
    }

    @Test
    public void getDocument() {
        Map<String, Object> doc = elasticsearchRestTemplate.get("1", Map.class, IndexCoordinates.of(INDEX_NAME));
        System.out.println(doc.get("title"));
    }

    @Test
    public void updateDocument() {
        Map<String, Object> map = new HashMap<>();
        map.put("title", "new Elasticsearch Introduction");
        UpdateQuery build = UpdateQuery.builder("1").withDocument(Document.from(map)).build();
        elasticsearchRestTemplate.update(build, IndexCoordinates.of(INDEX_NAME));
        Map map1 = elasticsearchRestTemplate.get("1", Map.class, IndexCoordinates.of(INDEX_NAME));
        System.out.println(map1.get("title"));
    }


    @Test
    public void deleteDocument() {
        elasticsearchRestTemplate.delete("1", IndexCoordinates.of(INDEX_NAME));
    }
    @Test
    public void deleteIndex() {
        elasticsearchRestTemplate.indexOps(IndexCoordinates.of(INDEX_NAME)).delete();
    }


}
