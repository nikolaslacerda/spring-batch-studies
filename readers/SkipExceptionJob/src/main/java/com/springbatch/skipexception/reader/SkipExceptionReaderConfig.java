package com.springbatch.skipexception.reader;

import com.springbatch.skipexception.dominio.Cliente;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SkipExceptionReaderConfig {
    @Bean
    public ItemReader<Cliente> skipExceptionReader(@Qualifier("appDataSource") DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<Cliente>()
                .name("skipExceptionReader")
                .dataSource(dataSource)
                .sql("select * from cliente")
                .beanRowMapper(Cliente.class)
                .build();
    }
}
