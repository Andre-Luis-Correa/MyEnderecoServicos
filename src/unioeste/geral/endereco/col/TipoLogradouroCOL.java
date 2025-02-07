package unioeste.geral.endereco.col;

import unioeste.geral.endereco.bo.tipologradouro.TipoLogradouro;
import unioeste.geral.endereco.dao.TipoLogradouroDAO;

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

	public static boolean tipoLogradouroExiste(TipoLogradouro tipoLogradouro) throws Exception {
		return tipoLogradouro != null &&
				tipoLogradouro.getSigla() != null &&
				TipoLogradouroDAO.selectTipoLogradouroPorSigla(tipoLogradouro.getSigla()) != null;
	}

}
