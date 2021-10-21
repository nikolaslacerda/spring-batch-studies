package com.springbatch.demonstrativoorcamentario.writer;

import com.springbatch.demonstrativoorcamentario.dominio.GrupoLancamento;
import com.springbatch.demonstrativoorcamentario.dominio.Lancamento;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.MultiResourceItemWriter;
import org.springframework.batch.item.file.ResourceSuffixCreator;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.builder.MultiResourceItemWriterBuilder;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class DemonstrativoOrcamentarioWriterConfig {

    @Bean
    @StepScope
    public MultiResourceItemWriter<GrupoLancamento> multiDemonstrativoOrcamentarioWriter(
            @Value("#{jobParameters['demostrativosOrcamentarios']}") Resource demostrativosOrcamentarios,
            FlatFileItemWriter<GrupoLancamento> demonstrativoOrcamentarioWriter) {
        return new MultiResourceItemWriterBuilder<GrupoLancamento>()
                .name("multiDemonstrativoOrcamentarioWriter")
                .resource(demostrativosOrcamentarios)
                .delegate(demonstrativoOrcamentarioWriter)
                .resourceSuffixCreator(suffixCreator())
                .itemCountLimitPerResource(1)
                .build();
    }

    private ResourceSuffixCreator suffixCreator() {
        return i -> i + ".txt";
    }

    @Bean
    @StepScope
    public FlatFileItemWriter<GrupoLancamento> demonstrativoOrcamentarioWriter(
            @Value("#{jobParameters['demonstrativoOrcamentario']}") Resource demonstrativoOrcamentario,
            DemonstrativoOrcamentarioRodape rodapeCallback) {
        return new FlatFileItemWriterBuilder<GrupoLancamento>()
                .name("demonstrativoOrcamentarioWriter")
                .resource(demonstrativoOrcamentario)
                .lineAggregator(lineAggregator())
                .headerCallback(cabecalhoCallback())
                .footerCallback(rodapeCallback)
                .build();
    }

    private FlatFileHeaderCallback cabecalhoCallback() {
        return writer -> {
            writer.append(String.format("SISTEMA INTEGRADO: XPTO \t\t\t\t DATA: %s", new SimpleDateFormat("dd/MM/yyyy").format(new Date())));
            writer.append(String.format("MÓDULO: ORÇAMENTO \t\t\t\t\t\t HORA: %s", new SimpleDateFormat("HH:MM").format(new Date())));
            writer.append(String.format("\t\t\tDEMONSTRATIVO ORCAMENTARIO"));
            writer.append(String.format("----------------------------------------------------------------------------"));
            writer.append(String.format("CODIGO NOME VALOR"));
            writer.append(String.format("\t Data Descricao Valor"));
            writer.append(String.format("----------------------------------------------------------------------------"));
        };
    }

    private LineAggregator<GrupoLancamento> lineAggregator() {
        return grupoLancamento -> {
            String formatGrupoLancamento = String.format("[%d] %s - %s\n", grupoLancamento.getCodigoNaturezaDespesa(),
                    grupoLancamento.getDescricaoNaturezaDespesa(),
                    NumberFormat.getCurrencyInstance().format(grupoLancamento.getTotal()));
            StringBuilder strBuilder = new StringBuilder();
            for (Lancamento lancamento : grupoLancamento.getLancamentos()) {
                strBuilder.append(String.format("\t [%s] %s - %s\n", new SimpleDateFormat("dd/MM/yyyy").format(lancamento.getData()), lancamento.getDescricao(),
                        NumberFormat.getCurrencyInstance().format(lancamento.getValor())));
            }
            String formatLancamento = strBuilder.toString();
            return formatGrupoLancamento + formatLancamento;
        };
    }
}
