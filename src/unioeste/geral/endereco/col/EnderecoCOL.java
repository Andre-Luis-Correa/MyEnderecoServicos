package unioeste.geral.endereco.col;

import irrf.geral.endereco.dao.EnderecoDAO;

import java.sql.Connection;

import irrf.geral.bo.endereco.Endereco;

public class EnderecoCOL {
	public static boolean idValido(int id) {
		return id>0;
	}
	
	public static boolean EnderecoCadastrado(Endereco endereco, Connection conexao) throws Exception {
		if(EnderecoDAO.selectEnderecoCEP(endereco.getCEP(), conexao)!=null) return true;
		return false;
	}
	
	public static boolean CEPValido(String CEP) {
		if(CEP==null) return false;
		return CEP.matches("[0-9]+") && CEP.length() ==8;
	}
	

	public static boolean EnderecoValido(Endereco endereco, Connection conexao) throws Exception {
		if(endereco==null) return false;
		if(!CEPValido(endereco.getCEP())) return false;
		
		if(!CidadeCOL.cidadeValida(endereco.getCidade())) return false;
		
		if(!CidadeCOL.cidadeCadastrada(endereco.getCidade(), conexao)) return false;
		
		if(!BairroCOL.bairroValido(endereco.getBairro())) return false;
		if(!BairroCOL.bairroCadastrado(endereco.getBairro(), conexao)) return false;
		
		if(!LogradouroCOL.logradouroValido(endereco.getLogradouro())) return false;
		if(!LogradouroCOL.logradouroCadastrado(endereco.getLogradouro(), conexao)) return false;
		
		return true;
	}
}
