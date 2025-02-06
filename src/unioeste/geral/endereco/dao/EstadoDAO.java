package unioeste.geral.endereco.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import irrf.geral.bo.endereco.Estado;


public class EstadoDAO {
	
	public static Estado selectEstado(String sigla_estado, Connection conexao) throws Exception{
		
		StringBuffer sql = new StringBuffer("SELECT estado.nome_estado FROM estado ");
		sql.append("WHERE estado.sigla_estado = ?;");
		PreparedStatement cmd = conexao.prepareStatement(sql.toString());
		cmd.setString(1, sigla_estado);
		ResultSet result = cmd.executeQuery();
		
		if(result.next()) {
			Estado estado = new Estado();
			
			estado.setNome(result.getString("nome_estado"));
			estado.setSigla(sigla_estado);
			
			return estado;
		}
		
		return null;

	}
}
