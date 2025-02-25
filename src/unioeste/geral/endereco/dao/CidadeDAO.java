package unioeste.geral.endereco.dao;

import unioeste.geral.endereco.bo.cidade.Cidade;
import unioeste.geral.endereco.bo.unidadefederativa.UnidadeFederativa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CidadeDAO {

	public Cidade selecionarCidadePorId(Long id, Connection conexao) throws SQLException {
		String sql = """
        SELECT c.id_cidade AS cidade_id, c.nome AS cidade_nome,
               uf.sigla_uf AS uf_sigla, uf.nome_uf AS uf_nome
        FROM cidade c
        JOIN unidade_federativa uf ON c.sigla_uf = uf.sigla_uf
        WHERE c.id_cidade = ?;
    """;

		try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
			preparedStatement.setLong(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {

					UnidadeFederativa uf = new UnidadeFederativa();
					uf.setSigla(resultSet.getString("uf_sigla"));
					uf.setNome(resultSet.getString("uf_nome"));

					Cidade cidade = new Cidade();
					cidade.setId(resultSet.getLong("cidade_id"));
					cidade.setNome(resultSet.getString("cidade_nome"));
					cidade.setUnidadeFederativa(uf);

					return cidade;
				}
			}
		}
		return null;
	}


	public List<Cidade> selecionarTodasCidades(Connection conexao) throws SQLException {
		String sql = """
        SELECT c.id_cidade, c.nome AS cidade_nome,
               uf.sigla_uf, uf.nome_uf
        FROM cidade c
        JOIN unidade_federativa uf ON c.sigla_uf = uf.sigla_uf
        ORDER BY c.nome;
    """;

		List<Cidade> cidades = new ArrayList<>();

		try (PreparedStatement preparedStatement = conexao.prepareStatement(sql);
			 ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				UnidadeFederativa uf = new UnidadeFederativa();
				uf.setSigla(resultSet.getString("sigla_uf"));
				uf.setNome(resultSet.getString("nome_uf"));

				Cidade cidade = new Cidade();
				cidade.setId(resultSet.getLong("id_cidade"));
				cidade.setNome(resultSet.getString("cidade_nome"));
				cidade.setUnidadeFederativa(uf);

				cidades.add(cidade);
			}
		}

		return cidades;
	}
}