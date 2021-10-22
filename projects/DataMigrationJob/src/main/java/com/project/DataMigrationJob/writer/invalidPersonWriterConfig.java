package com.project.DataMigrationJob.writer;

import com.project.DataMigrationJob.domain.Person;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class invalidPersonWriterConfig {

    @Bean
    public FlatFileItemWriter<Person> invalidPersonWriter() {
        return new FlatFileItemWriterBuilder<Person>()
                .name("invalidPersonWriter")
                .resource(new FileSystemResource("files/pessoas_invalidas.csv"))
                .delimited()
                .names("id")
                .build();
    }
}
