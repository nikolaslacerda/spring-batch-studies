package com.project.DataMigrationJob.job;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
@AllArgsConstructor
@EnableBatchProcessing
public class DataMigrationJobConfig {

    private JobBuilderFactory jobBuilderFactory;

    @Bean
    public Job dataMigrationJob(
            @Qualifier("personMigrationStep") Step personMigrationStep,
            @Qualifier("bankDataMigrationStep") Step bankDataMigrationStep) {
        return jobBuilderFactory
                .get("dataMigrationJob")
                .start(parallelSteps(personMigrationStep, bankDataMigrationStep))
                .end()
                .incrementer(new RunIdIncrementer())
                .build();
    }

    private Flow parallelSteps(Step personMigrationStep, Step bankDataMigrationStep) {
        Flow bankDataMigrationFlow = new FlowBuilder<Flow>("BankDataMigrationFlow")
                .start(bankDataMigrationStep)
                .build();

        return new FlowBuilder<Flow>("parallelStepsFlow")
                .start(personMigrationStep)
                .split(new SimpleAsyncTaskExecutor())
                .add(bankDataMigrationFlow)
                .build();
    }
}

