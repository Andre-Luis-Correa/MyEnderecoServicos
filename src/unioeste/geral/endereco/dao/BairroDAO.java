package unioeste.geral.endereco.dao;

import unioeste.apoio.bd.ConexaoBD;
import unioeste.geral.endereco.bo.bairro.Bairro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BairroDAO {

	public static Bairro selectBairroPorId(Long id) throws Exception {
		String sql = "SELECT nome_bairro FROM bairro WHERE id_bairro = ?";

		try (Connection conexaoBD = new ConexaoBD().getConexaoBD();
			 PreparedStatement cmd = conexaoBD.prepareStatement(sql)) {

			cmd.setLong(1, id);
			try (ResultSet result = cmd.executeQuery()) {
				if (result.next()) {
					return new Bairro(id, result.getString("nome_bairro"));
				}
			}
		} catch (SQLException e) {
			throw new Exception("Erro ao buscar bairro pelo ID: " + id, e);
		}

		return null;
	}
}
