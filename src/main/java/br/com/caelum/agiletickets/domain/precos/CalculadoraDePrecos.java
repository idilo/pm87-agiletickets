package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {

	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		BigDecimal preco;

		if (sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.CINEMA)
				|| sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.SHOW)) {
			preco = calculaPrecoCinemaOuShow(sessao);
		} else if (sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.BALLET)
				|| sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.ORQUESTRA)) {
			preco = calculaPreccoBalletOuOrquestra(sessao);
		} else {
			preco = sessao.getPreco();
		}

		return preco.multiply(BigDecimal.valueOf(quantidade));
	}

	private static BigDecimal calculaPreccoBalletOuOrquestra(Sessao sessao) {
		BigDecimal preco;
		if (ingressoNoFim(sessao)) {
			preco = sessao.getPreco().add(desconto(sessao, 0.20));
		} else {
			preco = sessao.getPreco();
		}

		if (sessao.getDuracaoEmMinutos() > 60) {
			preco = preco.add(desconto(sessao, 0.10));
		}
		return preco;
	}

	private static BigDecimal calculaPrecoCinemaOuShow(Sessao sessao) {
		BigDecimal preco;
		if (ingressoNoFim(sessao)) {
			preco = sessao.getPreco().add(desconto(sessao, 0.10));
		} else {
			preco = sessao.getPreco();
		}
		return preco;
	}

	private static boolean ingressoNoFim(Sessao sessao) {
		return (sessao.getTotalIngressos() - sessao.getIngressosReservados())
				/ sessao.getTotalIngressos().doubleValue() <= 0.50;
	}
	
	private static BigDecimal desconto(Sessao sessao, double percentagem) {
		return sessao.getPreco().multiply(BigDecimal.valueOf(percentagem));
	}

}