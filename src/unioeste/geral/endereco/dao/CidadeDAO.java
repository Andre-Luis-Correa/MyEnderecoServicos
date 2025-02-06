package unioeste.geral.endereco.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import irrf.geral.bo.endereco.Cidade;

public class CidadeDAO {
	public static Cidade selectCidade(int id, Connection conexao) throws Exception{
		StringBuffer sql = new StringBuffer("SELECT cidade.nome_cidade, cidade.sigla_estado ");
		sql.append("FROM cidade WHERE cidade.id_cidade = ?;");
		PreparedStatement cmd = conexao.prepareStatement(sql.toString());
		cmd.setInt(1, id);
		ResultSet result = cmd.executeQuery();
		
		if(result.next()) {
			Cidade cidade = new Cidade();
			
			cidade.setId(id);
			cidade.setNome(result.getString("nome_cidade"));
			cidade.setEstado(EstadoDAO.selectEstado(result.getString("sigla_estado"), conexao));
			
			return cidade;
		}
		
		return null;

	}
}
