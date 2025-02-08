package unioeste.geral.endereco.dao;

import unioeste.apoio.bd.ConexaoBD;
import unioeste.geral.endereco.bo.tipologradouro.TipoLogradouro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TipoLogradouroDAO {

	public static TipoLogradouro selectTipoLogradouroPorSigla(String sigla) throws Exception {
		String sql = "SELECT nome_tipo_logradouro FROM tipo_logradouro WHERE sigla_tipo_logradouro = ?";

		try (Connection conexao = new ConexaoBD().getConexaoComBD();
			 PreparedStatement cmd = conexao.prepareStatement(sql)) {

			cmd.setString(1, sigla);

			try (ResultSet result = cmd.executeQuery()) {
				if (result.next()) {
					return new TipoLogradouro(sigla, result.getString("nome_tipo_logradouro"));
				}
			}
		} catch (SQLException e) {
			throw new Exception("Erro ao buscar tipo de logradouro pela sigla: " + sigla, e);
		}

		return null;
	}

    public static List<TipoLogradouro> selectTodosTiposLogradouro() throws Exception {
		List<TipoLogradouro> tipoLogradouroList = new ArrayList<>();
		String sql = "SELECT * FROM tipo_logradouro;";

		try (Connection conn = new ConexaoBD().getConexaoComBD();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				TipoLogradouro tipoLogradouro = new TipoLogradouro(rs.getString("sigla_tipo_logradouro"), rs.getString("nome_tipo_logradouro"));
				tipoLogradouroList.add(tipoLogradouro);
			}

		} catch (Exception e) {
			throw new Exception("Erro ao buscar Tipos de Logradouro", e);
		}

		return tipoLogradouroList;
    }
}
