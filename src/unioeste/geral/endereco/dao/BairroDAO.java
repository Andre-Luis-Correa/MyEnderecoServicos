package unioeste.geral.endereco.dao;

import unioeste.apoio.bd.ConexaoBD;
import unioeste.geral.endereco.bo.bairro.Bairro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BairroDAO {

	public static Bairro selectBairroPorId(Long id) throws Exception {
		String sql = "SELECT nome FROM bairro WHERE id_bairro = ?";

		try (Connection conexaoBD = new ConexaoBD().getConexaoComBD();
			 PreparedStatement cmd = conexaoBD.prepareStatement(sql)) {

			cmd.setLong(1, id);
			try (ResultSet result = cmd.executeQuery()) {
				if (result.next()) {
					return new Bairro(id, result.getString("nome"));
				}
			}
		} catch (SQLException e) {
			throw new Exception("Erro ao buscar bairro pelo ID: " + id, e);
		}

		return null;
	}

	public static List<Bairro> selectTodosBairros() throws Exception {
		List<Bairro> bairroList = new ArrayList<>();
		String sql = "SELECT * FROM bairro;";

		try (Connection conn = new ConexaoBD().getConexaoComBD();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Bairro bairro = new Bairro(rs.getLong("id_bairro"), rs.getString("nome"));
				bairroList.add(bairro);
			}

		} catch (Exception e) {
			throw new Exception("Erro ao buscar Bairros", e);
		}

		return bairroList;
	}

	public static Bairro insertBairro(Bairro bairro) throws Exception {
		String sql = "INSERT INTO bairro (nome) VALUES (?) RETURNING id_bairro";

		try (Connection conexao = new ConexaoBD().getConexaoComBD();
			 PreparedStatement cmd = conexao.prepareStatement(sql)) {

			cmd.setString(1, bairro.getNome());

			try (ResultSet generatedKeys = cmd.executeQuery()) {
				if (generatedKeys.next()) {
					bairro.setId(generatedKeys.getLong(1));
				} else {
					throw new SQLException("Falha ao obter o ID do bairro inserido.");
				}
			}
		} catch (SQLException e) {
			throw new Exception("Erro ao inserir bairro: " + bairro, e);
		}

		return bairro;
	}


	public static Bairro selectBairroPorNome(String nome) throws Exception {
		String sql = "SELECT id_bairro FROM bairro WHERE nome = ?";

		try (Connection conexao = new ConexaoBD().getConexaoComBD();
			 PreparedStatement cmd = conexao.prepareStatement(sql)) {

			cmd.setString(1, nome);
			try (ResultSet result = cmd.executeQuery()) {
				if (result.next()) {
					return new Bairro(result.getLong("id_bairro"), nome);
				}
			}
		} catch (SQLException e) {
			throw new Exception("Erro ao buscar bairro pelo nome: " + nome, e);
		}

		return null;
	}

}
