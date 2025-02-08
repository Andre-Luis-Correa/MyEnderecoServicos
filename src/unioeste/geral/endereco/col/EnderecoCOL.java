package unioeste.geral.endereco.col;

import unioeste.geral.endereco.bo.endereco.Endereco;
import unioeste.geral.endereco.dao.EnderecoDAO;

public class EnderecoCOL {

	public static boolean idValido(Long id) {
		return id!= null && id > 0;
	}

	public static boolean enderecoExistePorCep(Endereco endereco) throws Exception {
		return endereco != null && EnderecoDAO.selectEnderecoPorCep(endereco.getCep()) != null;
	}

	public static boolean enderecoExiste(Endereco endereco) throws Exception {
		return endereco != null && endereco.getId() != null && EnderecoDAO.selectEnderecoPorId(endereco.getId()) != null;
	}

	public static boolean cepValido(String cep) {
		return cep != null && cep.matches("\\d{8}");
	}

	public static boolean enderecoValido(Endereco endereco) throws Exception {
		return endereco != null &&
				cepValido(endereco.getCep()) &&
				CidadeCOL.cidadeValida(endereco.getCidade()) &&
				CidadeCOL.cidadeExiste(endereco.getCidade()) &&
				BairroCOL.bairroValido(endereco.getBairro()) &&
				BairroCOL.bairroExiste(endereco.getBairro()) &&
				LogradouroCOL.logradouroValido(endereco.getLogradouro()) &&
				LogradouroCOL.logradouroExiste(endereco.getLogradouro());
	}

}
