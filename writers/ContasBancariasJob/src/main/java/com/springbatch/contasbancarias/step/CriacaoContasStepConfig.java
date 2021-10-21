package com.springbatch.contasbancarias.step;

import com.springbatch.contasbancarias.dominio.Cliente;
import com.springbatch.contasbancarias.dominio.Conta;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CriacaoContasStepConfig {
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step criacaoContasStep(
            ItemReader<Cliente> leituraClientesReader,
            ItemProcessor<Cliente, Conta> geracaoContaProcessor,
            ClassifierCompositeItemWriter<Conta> classifierContaWriter,
            @Qualifier("fileContaWriter") FlatFileItemWriter<Conta> clienteValidoWriter,
            @Qualifier("clienteInvalidoWriter") FlatFileItemWriter<Conta> clienteInvalidoWriter) {
        return stepBuilderFactory
                .get("criacaoContasStep")
                .<Cliente, Conta>chunk(100)
                .reader(leituraClientesReader)
                .processor(geracaoContaProcessor)
                .writer(classifierContaWriter)
                .stream(clienteInvalidoWriter)
                .stream(clienteValidoWriter)
                .build();
    }
}
