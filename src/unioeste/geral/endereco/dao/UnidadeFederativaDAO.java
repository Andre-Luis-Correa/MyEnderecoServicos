package unioeste.geral.endereco.dao;

import unioeste.apoio.bd.ConexaoBD;
import unioeste.geral.endereco.bo.unidadefederativa.UnidadeFederativa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


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

}
