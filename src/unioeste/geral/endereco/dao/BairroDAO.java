package unioeste.geral.endereco.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import irrf.geral.bo.endereco.Bairro;

public class BairroDAO {
	public static Bairro selectBairro(int id, Connection conexao) throws Exception{
		
		
		StringBuffer sql = new StringBuffer("SELECT bairro.nome_bairro ");
		sql.append("FROM bairro WHERE bairro.id_bairro = ?;");
		PreparedStatement cmd = conexao.prepareStatement(sql.toString());
		cmd.setInt(1, id);
		ResultSet result = cmd.executeQuery();
		
		if(result.next()) {
			Bairro bairro = new Bairro();
			
			bairro.setNome(result.getString("nome_bairro"));
			bairro.setId(id);
			
			return bairro;
		}
		
		return null;

	}
}
