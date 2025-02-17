package unioeste.geral.endereco.dao;

import unioeste.apoio.bd.ConexaoBD;
import unioeste.geral.endereco.bo.logradouro.Logradouro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LogradouroDAO {

	public static Logradouro selectLogradouroPorId(Long id) throws Exception {
		String sql = "SELECT nome, sigla_tipo_logradouro FROM logradouro WHERE id_logradouro = ?";

		try (Connection conexao = new ConexaoBD().getConexaoComBD();
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

    public static List<Logradouro> selectTodosLogradouros() throws Exception {
		List<Logradouro> logradouroList = new ArrayList<>();
		String sql = "SELECT * FROM logradouro;";

		try (Connection conn = new ConexaoBD().getConexaoComBD();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Logradouro logradouro = new Logradouro(rs.getLong("id_logradouro"), rs.getString("nome"), TipoLogradouroDAO.selectTipoLogradouroPorSigla(rs.getString("sigla_tipo_logradouro")));
				logradouroList.add(logradouro);
			}

		} catch (Exception e) {
			throw new Exception("Erro ao buscar Tipos de Logradouro", e);
		}

		return logradouroList;
    }

	public static Logradouro insertLogradouro(Logradouro logradouro) throws Exception {
		String sql = "INSERT INTO logradouro (nome, sigla_tipo_logradouro) VALUES (?, ?) RETURNING id_logradouro";

		try (Connection conexao = new ConexaoBD().getConexaoComBD();
			 PreparedStatement cmd = conexao.prepareStatement(sql)) {

			cmd.setString(1, logradouro.getNome());
			cmd.setString(2, logradouro.getTipoLogradouro().getSigla());

			try (ResultSet generatedKeys = cmd.executeQuery()) {
				if (generatedKeys.next()) {
					logradouro.setId(generatedKeys.getLong(1));
				} else {
					throw new SQLException("Falha ao obter o ID do logradouro inserido.");
				}
			}
		} catch (SQLException e) {
			throw new Exception("Erro ao inserir logradouro: " + logradouro, e);
		}

		return logradouro;
	}

	public static Logradouro selectLogradouroPorNome(String nome) throws Exception {
		String sql = "SELECT id_logradouro, sigla_tipo_logradouro FROM logradouro WHERE nome = ?";

		try (Connection conexao = new ConexaoBD().getConexaoComBD();
			 PreparedStatement cmd = conexao.prepareStatement(sql)) {

			cmd.setString(1, nome);
			try (ResultSet result = cmd.executeQuery()) {
				if (result.next()) {
					return new Logradouro(result.getLong("id_logradouro"), nome,
							TipoLogradouroDAO.selectTipoLogradouroPorSigla(result.getString("sigla_tipo_logradouro")));
				}
			}
		} catch (SQLException e) {
			throw new Exception("Erro ao buscar logradouro pelo nome: " + nome, e);
		}

		return null;
	}

}
