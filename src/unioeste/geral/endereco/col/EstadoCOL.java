package unioeste.geral.endereco.col;

import java.sql.Connection;

import irrf.geral.bo.endereco.Estado;
import irrf.geral.endereco.dao.EstadoDAO;
import unioeste.geral.endereco.bo.unidadefederativa.UnidadeFederativa;

public class EstadoCOL {
	public static boolean siglaValida(String sigla) {
		return sigla.matches("[A-Z]+") && sigla.length()==2;
	}
	
	public static boolean estadoValido(UnidadeFederativa unidadeFederativaado) {
		if(unidadeFederativaado==null) return false;
		if(!siglaValida(unidadeFederativaado.getSigla())) return false;
		if(unidadeFederativaado.getNome()==null) return false;
		
		return true;
	}
	
	public static boolean estadoCadastrado(UnidadeFederativa estado, Connection conexao) throws Exception {
		Estado aux = EstadoDAO.selectEstado(estado.getSigla(), conexao);
		if(aux==null) return false;
		return true;
	}
}
