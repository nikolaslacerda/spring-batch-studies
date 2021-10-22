package com.project.DataMigrationJob.step;

import com.project.DataMigrationJob.domain.Person;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class PersonMigrationStepConfig {

    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step personMigrationStep(
            ItemReader<Person> personFileReader,
            ClassifierCompositeItemWriter<Person> classifierPersonWriter,
            FlatFileItemWriter<Person> invalidPersonWriter
    ) {
        return stepBuilderFactory
                .get("personMigrationStep")
                .<Person, Person>chunk(1000)
                .reader(personFileReader)
                .writer(classifierPersonWriter)
                .stream(invalidPersonWriter)
                .build();
    }
}
