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

	public List<UnidadeFederativa> selecionarTodasUnidadesFederativas(Connection conexao) throws SQLException {
		String sql = """
        SELECT uf.sigla_uf, uf.nome_uf
        FROM unidade_federativa uf
        ORDER BY uf.nome_uf;
    """;

		List<UnidadeFederativa> unidadesFederativas = new ArrayList<>();

		try (PreparedStatement preparedStatement = conexao.prepareStatement(sql);
			 ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				UnidadeFederativa uf = new UnidadeFederativa();
				uf.setSigla(resultSet.getString("sigla_uf"));
				uf.setNome(resultSet.getString("nome_uf"));

				unidadesFederativas.add(uf);
			}
		}

		return unidadesFederativas;
	}

}
