package com.m1kah.sbsqltemplatebatchinsert;

import com.m1kah.batch.BatchApp;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.when;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = BatchApp.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
// @ActiveProfiles("db2")
// @ActiveProfiles("postgres")
@ActiveProfiles("mysql")
public class BatchAppTests {
    @Value("${local.server.port}")
    int port;

    @Before
    public void setup() {
        log.info("basePath: {}", basePath());
    }

    @Test
    public void batchInsert() {
        long start = System.currentTimeMillis();
        when().
            get(basePath() + "/batch").
        then().
            log().body().
            statusCode(200);
        log.info("Done in {} ms", (System.currentTimeMillis() - start));
    }

    private String basePath() {
        return "http://localhost:" + port;
    }

}
