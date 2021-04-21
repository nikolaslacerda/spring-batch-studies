package br.com.exercice;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    private Step imprimeOlaStep() {
        return stepBuilderFactory.get("imprimeOlaStep")
                .tasklet((contribution, chunkContext) -> {
                            System.out.println("Olá, mundo!");
                            return RepeatStatus.FINISHED;
                        }
                ).build();
    }

    @Bean
    public Job imprimeOlaJob() {
        return jobBuilderFactory.get("imprimeOlaJob")
                .start(imprimeOlaStep())
                .build();
    }
}
