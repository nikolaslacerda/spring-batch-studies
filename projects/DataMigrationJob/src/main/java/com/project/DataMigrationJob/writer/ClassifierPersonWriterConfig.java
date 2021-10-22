package com.project.DataMigrationJob.writer;

import com.project.DataMigrationJob.domain.Person;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemWriterBuilder;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClassifierPersonWriterConfig {

    @Bean
    public ClassifierCompositeItemWriter<Person> personClassifierWriter(
            JdbcBatchItemWriter<Person> personDatabaseWriter,
            FlatFileItemWriter<Person> invalidPersonWriter) {
        return new ClassifierCompositeItemWriterBuilder<Person>()
                .classifier(classifier(personDatabaseWriter, invalidPersonWriter))
                .build();
    }

    private Classifier<Person, ItemWriter<? super Person>> classifier(
            JdbcBatchItemWriter<Person> personDatabaseWriter,
            FlatFileItemWriter<Person> invalidPersonWriter) {
        return (Classifier<Person, ItemWriter<? super Person>>) person -> {
            if (person.isValid())
                return personDatabaseWriter;
            else
                return invalidPersonWriter;
        };
    }
}
