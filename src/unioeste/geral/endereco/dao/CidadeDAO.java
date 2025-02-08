package unioeste.geral.endereco.dao;

import unioeste.apoio.bd.ConexaoBD;
import unioeste.geral.endereco.bo.cidade.Cidade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CidadeDAO {

	public static Cidade selectCidadePorId(Long id) throws Exception {
		String sql = "SELECT nome, sigla_uf FROM cidade WHERE id_cidade = ?";

		try (Connection conexao = new ConexaoBD().getConexaoComBD();
			 PreparedStatement cmd = conexao.prepareStatement(sql)) {

			cmd.setLong(1, id);
			try (ResultSet result = cmd.executeQuery()) {
				if (result.next()) {
					Cidade cidade = new Cidade();
					cidade.setId(id);
					cidade.setNome(result.getString("nome"));
					cidade.setUnidadeFederativa(UnidadeFederativaDAO.selectUnidadeFederativaPorSigla(result.getString("sigla_uf")));
					return cidade;
				}
			}
		} catch (SQLException e) {
			throw new Exception("Erro ao buscar cidade pelo ID: " + id, e);
		}

		return null;
	}

	public static List<Cidade> selectTodasCidades() throws Exception {
		List<Cidade> cidadeList = new ArrayList<>();
		String sql = "SELECT * FROM cidade;";

		try (Connection conn = new ConexaoBD().getConexaoComBD();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Cidade cidade = new Cidade(rs.getLong("id_cidade"), rs.getString("nome"), UnidadeFederativaDAO.selectUnidadeFederativaPorSigla(rs.getString("sigla_uf")));
				cidadeList.add(cidade);
			}

		} catch (Exception e) {
			throw new Exception("Erro ao buscar Cidades", e);
		}

		return cidadeList;
	}
}
