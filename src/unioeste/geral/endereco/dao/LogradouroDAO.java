package unioeste.geral.endereco.dao;

import unioeste.geral.endereco.bo.logradouro.Logradouro;
import unioeste.geral.endereco.bo.tipologradouro.TipoLogradouro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LogradouroDAO {

	public Logradouro selecionarLogradouroPorId(Long id, Connection conexao) throws SQLException {
		String sql = """
        SELECT l.id_logradouro AS logradouro_id, l.nome AS logradouro_nome,
               tl.sigla_tipo_logradouro AS tipo_logradouro_sigla,
               tl.nome_tipo_logradouro AS tipo_logradouro_nome
        FROM logradouro l
        JOIN tipo_logradouro tl ON l.sigla_tipo_logradouro = tl.sigla_tipo_logradouro
        WHERE l.id_logradouro = ?;
    """;

		try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
			preparedStatement.setLong(1, id);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {

					TipoLogradouro tipoLogradouro = new TipoLogradouro();
					tipoLogradouro.setSigla(resultSet.getString("tipo_logradouro_sigla"));
					tipoLogradouro.setNome(resultSet.getString("tipo_logradouro_nome"));

					Logradouro logradouro = new Logradouro();
					logradouro.setId(resultSet.getLong("logradouro_id"));
					logradouro.setNome(resultSet.getString("logradouro_nome"));
					logradouro.setTipoLogradouro(tipoLogradouro);

					return logradouro;
				}
			}
		}
		return null;
	}

	public List<Logradouro> selecionarTodosLogradouros(Connection conexao) throws SQLException {
		String sql = """
        SELECT l.id_logradouro, l.nome AS logradouro_nome,
               tl.sigla_tipo_logradouro, tl.nome_tipo_logradouro
        FROM logradouro l
        JOIN tipo_logradouro tl ON l.sigla_tipo_logradouro = tl.sigla_tipo_logradouro
        ORDER BY l.nome;
    """;

		List<Logradouro> logradouros = new ArrayList<>();

		try (PreparedStatement preparedStatement = conexao.prepareStatement(sql);
			 ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {

				TipoLogradouro tipoLogradouro = new TipoLogradouro();
				tipoLogradouro.setSigla(resultSet.getString("sigla_tipo_logradouro"));
				tipoLogradouro.setNome(resultSet.getString("nome_tipo_logradouro"));

				Logradouro logradouro = new Logradouro();
				logradouro.setId(resultSet.getLong("id_logradouro"));
				logradouro.setNome(resultSet.getString("logradouro_nome"));
				logradouro.setTipoLogradouro(tipoLogradouro);

				logradouros.add(logradouro);
			}
		}

		return logradouros;
	}
}
