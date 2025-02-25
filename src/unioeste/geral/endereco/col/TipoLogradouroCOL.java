package unioeste.geral.endereco.col;

import unioeste.geral.endereco.bo.tipologradouro.TipoLogradouro;

public class TipoLogradouroCOL {

	public static boolean siglaValida(String sigla) {
		return sigla != null && !sigla.trim().isEmpty();
	}

	public static boolean tipoLogradouroValido(TipoLogradouro tipoLogradouro) {
		return tipoLogradouro != null &&
				siglaValida(tipoLogradouro.getSigla()) &&
				tipoLogradouro.getNome() != null &&
				!tipoLogradouro.getNome().trim().isEmpty();
	}

}
