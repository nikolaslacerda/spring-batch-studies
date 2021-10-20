package com.springbatch.processadorclassifier.processor;

import com.springbatch.processadorclassifier.dominio.Cliente;
import org.springframework.batch.item.ItemProcessor;

public class ClienteProcessor implements ItemProcessor<Cliente, Cliente> {

    @Override
    public Cliente process(Cliente cliente) {
        System.out.println(cliente);
        return cliente;
    }

}
