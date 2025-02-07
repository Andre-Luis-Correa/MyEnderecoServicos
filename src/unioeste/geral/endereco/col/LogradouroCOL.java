package unioeste.geral.endereco.col;

import unioeste.geral.endereco.bo.logradouro.Logradouro;
import unioeste.geral.endereco.dao.LogradouroDAO;

public class LogradouroCOL {

	public static boolean idValido(Long id) {
		return id != null && id > 0;
	}

	public static boolean logradouroValido(Logradouro logradouro) {
		return logradouro != null &&
				idValido(logradouro.getId()) &&
				logradouro.getNome() != null &&
				!logradouro.getNome().isBlank() &&
				TipoLogradouroCOL.tipoLogradouroValido(logradouro.getTipoLogradouro());
	}

	public static boolean logradouroExiste(Logradouro logradouro) throws Exception {
		return logradouro != null &&
				idValido(logradouro.getId()) &&
				LogradouroDAO.selectLogradouroPorId(logradouro.getId()) != null;
	}
}
