package com.project.DataMigrationJob.writer;

import com.project.DataMigrationJob.domain.Person;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Date;


@Configuration
public class PersonDatabaseWriterConfig {

    @Bean
    public JdbcBatchItemWriter<Person> personDatabaseWriter(@Qualifier("appDataSource") DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Person>()
                .dataSource(dataSource)
                .sql("INSERT INTO person (id, name, email, birthday, age) VALUES (?, ?, ?, ?, ?)")
                .itemPreparedStatementSetter(itemPreparedStatementSetter())
                .build();
    }

    private ItemPreparedStatementSetter<Person> itemPreparedStatementSetter() {
        return (person, ps) -> {
            ps.setInt(1, person.getId());
            ps.setString(2, person.getName());
            ps.setString(3, person.getEmail());
            ps.setDate(4, new Date(person.getBirthday().getTime()));
            ps.setInt(5, person.getAge());
        };
    }
}
