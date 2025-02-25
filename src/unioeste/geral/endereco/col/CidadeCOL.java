package unioeste.geral.endereco.col;

import unioeste.geral.endereco.bo.cidade.Cidade;

public class CidadeCOL {

	public boolean idValido(Long id) {
		return id != null && id > 0;
	}

	public boolean cidadeValida(Cidade cidade) {
		return cidade != null &&
				idValido(cidade.getId()) &&
				cidade.getNome() != null &&
				!cidade.getNome().trim().isEmpty() &&
				cidade.getUnidadeFederativa() != null;
	}

}
