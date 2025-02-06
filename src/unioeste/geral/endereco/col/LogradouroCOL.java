package unioeste.geral.endereco.col;

import unioeste.geral.endereco.bo.logradouro.Logradouro;
import unioeste.geral.endereco.dao.LogradouroDAO;

import java.sql.Connection;

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
	
	public static boolean logradouroCadastrado(Logradouro logradouro) throws Exception {
		Logradouro aux = LogradouroDAO.selectLogradouro(logradouro.getId());
		if(aux==null) return false;
		return true;
	}
}
