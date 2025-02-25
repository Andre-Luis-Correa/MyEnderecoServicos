package unioeste.geral.endereco.col;

import unioeste.geral.endereco.bo.endereco.Endereco;

public class EnderecoCOL {

	public boolean idValido(Long id) {
		return id!= null && id > 0;
	}

	public boolean cepValido(String cep) {
		return cep != null && cep.matches("\\d{8}");
	}

	public boolean enderecoValido(Endereco endereco) {
		return endereco != null &&
				cepValido(endereco.getCep());
	}

}
