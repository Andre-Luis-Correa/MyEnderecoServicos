package unioeste.geral.endereco.dao;

import unioeste.geral.endereco.bo.cidade.Cidade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CidadeDAO {

	public static Cidade selectCidade(Long id, Connection conexao) throws Exception{
		StringBuffer sql = new StringBuffer("SELECT cidade.nome_cidade, cidade.sigla_estado ");
		sql.append("FROM cidade WHERE cidade.id_cidade = ?;");
		PreparedStatement cmd = conexao.prepareStatement(sql.toString());
		cmd.setInt(1, id);
		ResultSet result = cmd.executeQuery();
		
		if(result.next()) {
			Cidade cidade = new Cidade();
			
			cidade.setId(id);
			cidade.setNome(result.getString("nome_cidade"));
			cidade.setUnidadeFederativa(EstadoDAO.selectEstado(result.getString("sigla_estado"), conexao));
			
			return cidade;
		}
		
		return null;

	}
}
