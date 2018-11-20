package com.m1kah.batch.target;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DbTargetRepository implements TargetRepository {
    static final String INSERT =
            "insert into target_data " +
            "  (text1, text2, text3, text4, amount1, amount2) " +
            "values " +
            "  (:text1, :text2, :text3, :text4, :amount1, :amount2) ";
    final NamedParameterJdbcTemplate jdbc;

    @Override
    public Mono<Integer> batchInsert(List<TargetData> data) {
        List<SqlParameterSource> parameterList = new ArrayList<>();
        for (TargetData target : data) {
            MapSqlParameterSource rowParameters = new MapSqlParameterSource();
            rowParameters.addValue("text1", target.getText1());
            rowParameters.addValue("text2", target.getText2());
            rowParameters.addValue("text3", target.getText3());
            rowParameters.addValue("text4", target.getText4());
            rowParameters.addValue("amount1", target.getAmount1());
            rowParameters.addValue("amount2", target.getAmount2());
            parameterList.add(rowParameters);
        }
        // MySQL with rewrite configuration returns negative values in updated
        // rows. So we are simply assuming that all rows are written if there
        // is no exception thrown.
        return Mono
                .fromCallable(() -> jdbc.batchUpdate(INSERT, parameterList.toArray(new SqlParameterSource[0])))
                .map(changedRows -> data.size());
    }
}
