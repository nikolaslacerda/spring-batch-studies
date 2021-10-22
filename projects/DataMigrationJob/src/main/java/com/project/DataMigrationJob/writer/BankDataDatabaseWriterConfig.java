package com.project.DataMigrationJob.writer;

import com.project.DataMigrationJob.domain.BankData;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class BankDataDatabaseWriterConfig {

    @Bean
    public JdbcBatchItemWriter<BankData> bankDataDatabaseWriter(@Qualifier("appDataSource") DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<BankData>()
                .dataSource(dataSource)
                .sql("INSERT INTO bank_data (id, person_id, agency, account, bank) VALUES (:id, :personId, :agency, :account, :bank)")
                .beanMapped()
                .build();
    }


}
