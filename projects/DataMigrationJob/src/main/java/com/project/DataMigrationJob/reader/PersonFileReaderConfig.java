package com.project.DataMigrationJob.reader;

import com.project.DataMigrationJob.domain.Person;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.util.Date;

@Configuration
public class PersonFileReaderConfig {

    @Bean
    public FlatFileItemReader<Person> personFileReader() {
        return new FlatFileItemReaderBuilder<Person>()
                .name("personFileReader")
                .resource(new FileSystemResource("files/pessoas.csv"))
                .delimited()
                .names("name", "email", "birthday", "age", "id")
                .addComment("--")
                .fieldSetMapper(fieldSetMapper())
                .build();
    }

    private FieldSetMapper<Person> fieldSetMapper() {
        return fieldSet -> Person.builder()
                .name(fieldSet.readString("name"))
                .email(fieldSet.readString("email"))
                .birthday(new Date(fieldSet.readDate("birthday", "yyyy-MM-dd HH:mm:ss").getTime()))
                .age(fieldSet.readInt("age"))
                .id(fieldSet.readInt("id"))
                .build();
    }

}
