package unioeste.geral.endereco.dao;

import unioeste.apoio.bd.ConexaoBD;
import unioeste.geral.endereco.bo.unidadefederativa.UnidadeFederativa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UnidadeFederativaDAO {

	public static UnidadeFederativa selectUnidadeFederativaPorSigla(String sigla) throws Exception {
		String sql = "SELECT nome_uf FROM unidade_federativa WHERE sigla_uf = ?";

		try (Connection conexaoBD = new ConexaoBD().getConexaoComBD();
			 PreparedStatement cmd = conexaoBD.prepareStatement(sql)) {

			cmd.setString(1, sigla);
			try (ResultSet result = cmd.executeQuery()) {
				if (result.next()) {
					return new UnidadeFederativa(sigla, result.getString("nome_uf"));
				}
			}
		} catch (SQLException e) {
			throw new Exception("Erro ao buscar unidade federativa pela sigla: " + sigla, e);
		}

		return null;
	}

    public static List<UnidadeFederativa> selectTodasUnidadesFederativas() throws Exception {
		List<UnidadeFederativa> unidadeFederativaList = new ArrayList<>();
		String sql = "SELECT * FROM unidade_federativa;";

		try (Connection conn = new ConexaoBD().getConexaoComBD();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				UnidadeFederativa unidadeFederativa = new UnidadeFederativa(rs.getString("sigla_uf"), rs.getString("nome_uf"));
				unidadeFederativaList.add(unidadeFederativa);
			}

		} catch (Exception e) {
			throw new Exception("Erro ao buscar Unidades Federativas", e);
		}

		return unidadeFederativaList;
    }
}
