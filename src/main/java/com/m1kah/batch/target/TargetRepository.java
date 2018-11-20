package com.m1kah.batch.target;

import reactor.core.publisher.Mono;

import java.util.List;

public interface TargetRepository {
    Mono<Integer> batchInsert(List<TargetData> data);
}
