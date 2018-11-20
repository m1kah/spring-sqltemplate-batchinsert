package com.m1kah.batch.source;

import lombok.val;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

@Repository
public class SampleSourceRepository implements SourceRepository{
    @Override
    public Flux<SourceData> read() {
        val nextRandom = CompletableFuture.supplyAsync(this::makeRandom);
        return Mono.fromFuture(nextRandom).repeat(10000 - 1);
    }

    private SourceData makeRandom() {
        return new SourceData()
                .setText1(RandomStringUtils.randomAlphanumeric(128))
                .setText2(RandomStringUtils.randomAlphanumeric(128))
                .setText3(RandomStringUtils.randomAlphanumeric(128))
                .setText4(RandomStringUtils.randomAlphanumeric(128))
                .setAmount1(randomNumber())
                .setAmount2(randomNumber());
    }

    private BigDecimal randomNumber() {
        return new BigDecimal(
                RandomStringUtils.randomNumeric(9) +
                        "." +
                        RandomStringUtils.randomNumeric(2));
    }
}
