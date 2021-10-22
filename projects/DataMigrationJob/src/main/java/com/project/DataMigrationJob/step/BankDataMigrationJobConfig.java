package com.project.DataMigrationJob.step;

import com.project.DataMigrationJob.domain.BankData;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class BankDataMigrationJobConfig {

    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step bankDataMigrationStep(ItemReader<BankData> bankDataFileReader, ItemWriter<BankData> bankDataDatabaseWriter) {
        return stepBuilderFactory
                .get("bankDataMigrationStep")
                .<BankData, BankData>chunk(1)
                .reader(bankDataFileReader)
                .writer(bankDataDatabaseWriter)
                .build();
    }

}
