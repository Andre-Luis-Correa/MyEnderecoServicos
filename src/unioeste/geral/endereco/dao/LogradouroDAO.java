package unioeste.geral.endereco.dao;

import unioeste.apoio.bd.ConexaoBD;
import unioeste.geral.endereco.bo.logradouro.Logradouro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogradouroDAO {

	public static Logradouro selectLogradouroPorId(Long id) throws Exception {
		String sql = "SELECT nome, sigla_tipo_logradouro FROM logradouro WHERE id_logradouro = ?";

		try (Connection conexao = new ConexaoBD().getConexaoBD();
			 PreparedStatement cmd = conexao.prepareStatement(sql)) {

			cmd.setLong(1, id);
			try (ResultSet result = cmd.executeQuery()) {
				if (result.next()) {
					Logradouro logradouro = new Logradouro();
					logradouro.setNome(result.getString("nome"));
					logradouro.setId(id);
					logradouro.setTipoLogradouro(TipoLogradouroDAO.selectTipoLogradouroPorSigla(result.getString("sigla_tipo_logradouro")));
					return logradouro;
				}
			}
		} catch (SQLException e) {
			throw new Exception("Erro ao buscar logradouro pelo ID: " + id, e);
		}

		return null;
	}
}
