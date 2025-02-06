package unioeste.geral.endereco.col;

import java.sql.Connection;

import irrf.geral.bo.endereco.Logradouro;
import irrf.geral.endereco.dao.LogradouroDAO;

public class LogradouroCOL {
	public static boolean idValido(int id) {
		return id>0;
	}
	
	public static boolean logradouroValido(Logradouro logradouro) {
		if(logradouro==null) return false;
		if(!idValido(logradouro.getId())) return false;
		if(logradouro.getNome()==null) return false;
		if(!TipoLogradouroCOL.tipoLogradouroValido(logradouro.getTipo_logradouro())) return false;
		return true;
	}
	
	public static boolean logradouroCadastrado(Logradouro logradouro, Connection conexao) throws Exception {
		Logradouro aux = LogradouroDAO.selectLogradouro(logradouro.getId(), conexao);
		if(aux==null) return false;
		return true;
	}
}
