package com.spinner.www.study.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import com.spinner.www.study.dto.StudyDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyElasticsearchService {

    private final ElasticsearchClient elasticsearchClient;

    public void saveAllToElasticsearch(List<StudyDocument> documents) throws IOException {
        List<BulkOperation> operations = documents.stream()
                .map(doc -> BulkOperation.of(op -> op
                        .index(idx -> idx
                                .index("study")
                                .id(String.valueOf(doc.studyIdx()))
                                .document(doc)
                        )
                ))
                .toList();

        BulkRequest request = new BulkRequest.Builder()
                .index("study")
                .operations(operations)
                .build();

        BulkResponse response = elasticsearchClient.bulk(request);

        if (response.errors()) {
            System.out.println("⚠️ 일부 색인 실패:");
            response.items().forEach(item -> {
                if (item.error() != null) {
                    System.out.println("❌ 실패한 문서 ID: " + item.id());
                    System.out.println("   이유: " + item.error().reason());
                }
            });
        } else {
            System.out.println("✅ 색인 성공: " + response.items().size() + "건");
        }
    }
}
