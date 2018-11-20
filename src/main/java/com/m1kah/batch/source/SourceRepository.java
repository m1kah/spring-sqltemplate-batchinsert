package com.m1kah.batch.source;

import reactor.core.publisher.Flux;

public interface SourceRepository {
    Flux<SourceData> read();
}
