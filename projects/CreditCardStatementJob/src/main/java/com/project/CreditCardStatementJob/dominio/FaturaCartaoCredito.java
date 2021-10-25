package com.project.CreditCardStatementJob.dominio;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FaturaCartaoCredito {

	private Cliente cliente;
	private CartaoCredito cartaoCredito;
	private List<Transacao> transacoes = new ArrayList<>();

	public Double getTotal() {
		return transacoes
				.stream()
				.mapToDouble(Transacao::getValor)
				.reduce(0.0, Double::sum);
	}
}
