package unioeste.geral.endereco.col;

import unioeste.geral.endereco.bo.bairro.Bairro;

public class BairroCOL {

	public static boolean idValido(Long id) {
		return id != null && id > 0;
	}

	public static boolean bairroValido(Bairro bairro) {
		return bairro != null &&
				idValido(bairro.getId()) &&
				bairro.getNome() != null &&
				!bairro.getNome().trim().isEmpty();
	}
}
