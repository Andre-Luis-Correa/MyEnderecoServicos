package unioeste.geral.endereco.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import irrf.geral.bo.endereco.TipoLogradouro;

public class TipoLogradouroDAO {
	public static TipoLogradouro selectTipoLogradouro(int id, Connection conexao) throws Exception{
		
		StringBuffer sql = new StringBuffer("SELECT tipo_logradouro.nome_tipo_logradouro ");
		sql.append("FROM tipo_logradouro WHERE tipo_logradouro.id_tipo_logradouro =").append(id).append(";");
		PreparedStatement cmd = conexao.prepareStatement(sql.toString());
		cmd.setInt(1, id);
		ResultSet result = cmd.executeQuery();
		
		if(result.next()) {
			TipoLogradouro tipo_logradouro = new TipoLogradouro();
			
			tipo_logradouro.setNome(result.getString("nome_tipo_logradouro"));
			tipo_logradouro.setId(id);
			
			return tipo_logradouro;
		}
		
		return null;

	}
}
