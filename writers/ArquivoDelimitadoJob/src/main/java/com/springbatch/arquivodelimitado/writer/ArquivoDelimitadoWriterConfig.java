package com.springbatch.arquivodelimitado.writer;

import com.springbatch.arquivodelimitado.dominio.Cliente;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class ArquivoDelimitadoWriterConfig {

    @Bean
    @StepScope
    public FlatFileItemWriter<Cliente> escritaArquivoDelimitadoWriter(@Value("#{jobParameters['arquivoClientesSaida']}") Resource arquivoSaida) {
        return new FlatFileItemWriterBuilder<Cliente>()
                .name("escritaArquivoDelimitadoWriter")
                .resource(arquivoSaida)
                .delimited()
                .delimiter(";")
                .names("nome", "sobrenome", "idade", "email")
                .build();
    }
}
