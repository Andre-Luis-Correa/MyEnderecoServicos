package unioeste.geral.endereco.col;

import unioeste.geral.endereco.bo.cidade.Cidade;

import java.sql.Connection;

public class CidadeCOL {
	public static boolean idValido(Long id) {
		return id>0;
	}
	
	public static boolean cidadeValida(Cidade cidade) {
		if(cidade==null) return false;
		if(!idValido(cidade.getId())) return false;
		if(cidade.getNome()==null) return false;
		if(!EstadoCOL.estadoValido(cidade.getUnidadeFederativa())) return false;
		
		return true;
	}
	
	public static boolean cidadeCadastrada(Cidade cidade, Connection conexao) throws Exception {
		Cidade aux = CidadeDAO.selectCidade(cidade.getId(), conexao);
		if(aux==null) return false;
		if(!aux.getEstado().getSigla().equals(cidade.getEstado().getSigla())) return false;
		
		return true;
	}
}
