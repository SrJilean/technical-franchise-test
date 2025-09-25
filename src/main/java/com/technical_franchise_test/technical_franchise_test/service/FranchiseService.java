package com.technical_franchise_test.technical_franchise_test.service;

import com.technical_franchise_test.technical_franchise_test.model.Franchise;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class FranchiseService {

    private final DynamoDbAsyncClient dynamoDbClient;

    public FranchiseService(DynamoDbAsyncClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    public Mono<Franchise> save(Franchise franchise) {
        String id = (franchise.getId() != null && !franchise.getId().isBlank())
                ? franchise.getId()
                : UUID.randomUUID().toString();

        franchise.setId(id);

        Map<String, AttributeValue> item = new HashMap<>();
        item.put("id", AttributeValue.builder().s(id).build());

        if (franchise.getName() != null && !franchise.getName().isBlank()) {
            item.put("name", AttributeValue.builder().s(franchise.getName()).build());
        }

        if (franchise.getBranches() != null && !franchise.getBranches().isEmpty()) {
            List<String> cleanBranches = franchise.getBranches().stream()
                    .filter(b -> b != null && !b.isBlank())
                    .toList();

            if (!cleanBranches.isEmpty()) {
                item.put("branches", AttributeValue.builder().ss(cleanBranches).build());
            }
        }

        PutItemRequest request = PutItemRequest.builder()
                .tableName("Franchises")
                .item(item)
                .build();

        return Mono.fromFuture(dynamoDbClient.putItem(request))
                .thenReturn(franchise);
    }

    public Flux<Franchise> findAll() {
        ScanRequest request = ScanRequest.builder()
                .tableName("Franchises")
                .build();

        return Mono.fromFuture(dynamoDbClient.scan(request))
                .flatMapMany(response -> Flux.fromIterable(response.items()))
                .map(this::mapToFranchise);
    }

    private Franchise mapToFranchise(Map<String, AttributeValue> item) {
        String id = item.get("id").s();
        String name = item.containsKey("name") ? item.get("name").s() : null;
        List<String> branches = item.containsKey("branches")
                ? item.get("branches").ss()
                : List.of();

        return new Franchise(id, name, branches);
    }
}
