package unioeste.geral.endereco.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import irrf.geral.bo.endereco.Logradouro;

public class LogradouroDAO {
	public static Logradouro selectLogradouro(int id, Connection conexao) throws Exception{
		StringBuffer sql = new StringBuffer("SELECT logradouro.nome_logradouro, logradouro.id_tipo_logradouro ");
		sql.append("FROM logradouro WHERE logradouro.id_logradouro =").append(id).append(";");
		PreparedStatement cmd = conexao.prepareStatement(sql.toString());
		cmd.setInt(1, id);
		ResultSet result = cmd.executeQuery();
		
		
		if(result.next()) {
			Logradouro logradouro = new Logradouro();
			
			logradouro.setNome(result.getString("nome_logradouro"));
			logradouro.setId(id);
			logradouro.setTipo_logradouro(TipoLogradouroDAO.selectTipoLogradouro(result.getInt("id_tipo_logradouro"), conexao));
			
			return logradouro;
		}
		
		return null;

	}
}
