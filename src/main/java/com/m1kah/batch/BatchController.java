package com.m1kah.batch;

import com.m1kah.batch.source.SourceData;
import com.m1kah.batch.source.SourceRepository;
import com.m1kah.batch.target.TargetData;
import com.m1kah.batch.target.TargetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class BatchController {
    final SourceRepository sourceRepository;
    final TargetRepository targetRepository;
    final Scheduler scheduler = Schedulers.newParallel("jdbc", 5);

    @RequestMapping("/batch")
    public Mono<BatchResponse> batch() {
        long start = System.currentTimeMillis();
        return sourceRepository.read()
                .map(this::transformData)
                .buffer(1000)
                .parallel(5)
                .runOn(scheduler)
                .doOnNext(data -> log.info("Inserting {} rows", data.size()))
                .flatMap(targetRepository::batchInsert)
                .sequential()
                .collectList()
                .map(changedRows -> makeResponse(changedRows, start));
    }

    private TargetData transformData(SourceData source) {
        return new TargetData()
                .setText1(source.getText1())
                .setText2(source.getText2())
                .setText3(source.getText3())
                .setText4(source.getText4())
                .setAmount1(source.getAmount1())
                .setAmount2(source.getAmount2());
    }

    private BatchResponse makeResponse(List<Integer> changedRows, long start) {
        BatchResponse res = new BatchResponse()
                .setRows(changedRows.stream()
                        .mapToInt(Integer::intValue).sum())
                .setDuration(System.currentTimeMillis() - start);
        res.setMsPerRow(divide(res.getDuration(), res.getRows(), 3));
        res.setRowsPerSecond(divide(res.getRows(), divide(res.getDuration(), 1_000, 5), 0));
        return res;
    }

    private BigDecimal divide(Number a, Number b, int scale) {
        return new BigDecimal(a.doubleValue()).divide(new BigDecimal(b.doubleValue()), 5, RoundingMode.HALF_UP).setScale(scale, RoundingMode.HALF_UP);
    }
}
