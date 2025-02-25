package unioeste.geral.endereco.col;

import unioeste.geral.endereco.bo.logradouro.Logradouro;

public class LogradouroCOL {

	public boolean idValido(Long id) {
		return id != null && id > 0;
	}

	public boolean logradouroValido(Logradouro logradouro) {
		return logradouro != null &&
				idValido(logradouro.getId()) &&
				logradouro.getNome() != null &&
				!logradouro.getNome().trim().isEmpty() &&
				logradouro.getTipoLogradouro() != null;
	}
}
