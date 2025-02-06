package unioeste.geral.endereco.col;

import unioeste.geral.endereco.bo.endereco.Endereco;
import unioeste.geral.endereco.dao.EnderecoDAO;

public class EnderecoCOL {

	public static boolean idValido(int id) {
		return id>0;
	}
	
	public static boolean EnderecoCadastrado(Endereco endereco) throws Exception {
		return EnderecoDAO.selectEnderecoCEP(endereco.getCep())!=null;
	}
	
	public static boolean CEPValido(String cep) {
		if(cep == null) return false;
		return cep.matches("[0-9]+") && cep.length() == 8;
	}
	

	public static boolean EnderecoValido(Endereco endereco) throws Exception {
		if(endereco == null) return false;
		if(!CEPValido(endereco.getCep())) return false;
		
		if(!CidadeCOL.cidadeValida(endereco.getCidade())) return false;
		if(!CidadeCOL.cidadeCadastrada(endereco.getCidade())) return false;
		
		if(!BairroCOL.bairroValido(endereco.getBairro())) return false;
		if(!BairroCOL.bairroCadastrado(endereco.getBairro())) return false;
		
		if(!LogradouroCOL.logradouroValido(endereco.getLogradouro())) return false;
		if(!LogradouroCOL.logradouroCadastrado(endereco.getLogradouro())) return false;
		
		return true;
	}
}
