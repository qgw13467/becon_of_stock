package com.ssafy.beconofstock.spark.service;

import com.ssafy.beconofstock.spark.dto.TestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@PropertySource("classpath:application.yml")
public class SparkServiceImpl implements SparkService {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String userName;
    @Value("${spring.datasource.password}")
    private String password;

    public TestDto getConnectionTest() {

        SparkSession spark = SparkSession.builder()
                .appName("simple app")
                .config("spark.master", "local")
                .getOrCreate();

        Dataset<Row> df = spark
                .read()
                .format("jdbc")
                .option("driver", "com.mysql.cj.jdbc.Driver")
                .option("url", url)
                .option("user", userName)
                .option("password", password)
                .option("dbtable", "trade")
                .load();

        df.show();

        TestDto testDto = new TestDto();
        testDto.setCount(df.count());
        return testDto;
    }
}
