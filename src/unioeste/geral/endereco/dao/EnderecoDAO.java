package unioeste.geral.endereco.dao;

import unioeste.geral.endereco.bo.bairro.Bairro;
import unioeste.geral.endereco.bo.cidade.Cidade;
import unioeste.geral.endereco.bo.endereco.Endereco;
import unioeste.geral.endereco.bo.logradouro.Logradouro;
import unioeste.geral.endereco.bo.tipologradouro.TipoLogradouro;
import unioeste.geral.endereco.bo.unidadefederativa.UnidadeFederativa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnderecoDAO {

    public Endereco inserirEndereco(Endereco endereco, Connection conexao) {
		System.out.println("Chegou até aqui -1");
        String sql = "INSERT INTO endereco (cep, id_cidade, id_bairro, id_logradouro) VALUES (?, ?, ?, ?);";
		System.out.println("Chegou até aqui 0");
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, endereco.getCep());
            preparedStatement.setLong(2, endereco.getCidade().getId());
            preparedStatement.setLong(3, endereco.getBairro().getId());
			preparedStatement.setLong(4, endereco.getLogradouro().getId());

			System.out.println("Chegou até aqui 1");
            preparedStatement.executeUpdate();
			System.out.println("Chegou até aqui 2");
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
			System.out.println("Chegou até aqui 3");
            if (resultSet.next()) {
				System.out.println("Chegou até aqui 4");
                endereco.setId(resultSet.getLong("id_endereco"));
                return endereco;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

public List<Endereco> selecionarEnderecosPorCep(String cep, Connection conexao) throws SQLException {
    String sql = """
                SELECT e.id_endereco, e.cep,
                       c.id_cidade, c.nome AS cidade_nome,
                       uf.sigla_uf, uf.nome_uf,
                       l.id_logradouro, l.nome AS logradouro_nome,
                       tl.sigla_tipo_logradouro, tl.nome_tipo_logradouro,
                       b.id_bairro, b.nome AS bairro_nome
                FROM endereco e
                JOIN cidade c ON e.id_cidade = c.id_cidade
                JOIN unidade_federativa uf ON c.sigla_uf = uf.sigla_uf
                JOIN logradouro l ON e.id_logradouro = l.id_logradouro
                JOIN tipo_logradouro tl ON l.sigla_tipo_logradouro = tl.sigla_tipo_logradouro
                JOIN bairro b ON e.id_bairro = b.id_bairro
                WHERE e.cep = ?;
            """;

    List<Endereco> enderecos = new ArrayList<>();

    try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
        preparedStatement.setString(1, cep);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                UnidadeFederativa uf = new UnidadeFederativa();
                uf.setSigla(resultSet.getString("sigla_uf"));
                uf.setNome(resultSet.getString("nome_uf"));

                Cidade cidade = new Cidade();
                cidade.setId(resultSet.getLong("id_cidade"));
                cidade.setNome(resultSet.getString("cidade_nome"));
                cidade.setUnidadeFederativa(uf);

                TipoLogradouro tipoLogradouro = new TipoLogradouro();
                tipoLogradouro.setSigla(resultSet.getString("sigla_tipo_logradouro"));
                tipoLogradouro.setNome(resultSet.getString("nome_tipo_logradouro"));

                Logradouro logradouro = new Logradouro();
                logradouro.setId(resultSet.getLong("id_logradouro"));
                logradouro.setNome(resultSet.getString("logradouro_nome"));
                logradouro.setTipoLogradouro(tipoLogradouro);

                Bairro bairro = new Bairro();
                bairro.setId(resultSet.getLong("id_bairro"));
                bairro.setNome(resultSet.getString("bairro_nome"));

                Endereco endereco = new Endereco();
                endereco.setId(resultSet.getLong("id_endereco"));
                endereco.setCep(resultSet.getString("cep"));
                endereco.setCidade(cidade);
                endereco.setLogradouro(logradouro);
                endereco.setBairro(bairro);

                enderecos.add(endereco);
            }
        }
    }

    return enderecos;
}

public Endereco selecionarEnderecoPorId(Long id, Connection conexao) throws SQLException {
    String sql = """
                SELECT e.id_endereco, e.cep,
                       c.id_cidade, c.nome AS cidade_nome,
                       uf.sigla_uf, uf.nome_uf,
                       l.id_logradouro, l.nome AS logradouro_nome,
                       tl.sigla_tipo_logradouro, tl.nome_tipo_logradouro,
                       b.id_bairro, b.nome AS bairro_nome
                FROM endereco e
                JOIN cidade c ON e.id_cidade = c.id_cidade
                JOIN unidade_federativa uf ON c.sigla_uf = uf.sigla_uf
                JOIN logradouro l ON e.id_logradouro = l.id_logradouro
                JOIN tipo_logradouro tl ON l.sigla_tipo_logradouro = tl.sigla_tipo_logradouro
                JOIN bairro b ON e.id_bairro = b.id_bairro
                WHERE e.id_endereco = ?;
            """;

    try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
        preparedStatement.setLong(1, id);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {

                UnidadeFederativa uf = new UnidadeFederativa();
                uf.setSigla(resultSet.getString("sigla_uf"));
                uf.setNome(resultSet.getString("nome_uf"));

                Cidade cidade = new Cidade();
                cidade.setId(resultSet.getLong("id_cidade"));
                cidade.setNome(resultSet.getString("cidade_nome"));
                cidade.setUnidadeFederativa(uf);

                TipoLogradouro tipoLogradouro = new TipoLogradouro();
                tipoLogradouro.setSigla(resultSet.getString("sigla_tipo_logradouro"));
                tipoLogradouro.setNome(resultSet.getString("nome_tipo_logradouro"));

                Logradouro logradouro = new Logradouro();
                logradouro.setId(resultSet.getLong("id_logradouro"));
                logradouro.setNome(resultSet.getString("logradouro_nome"));
                logradouro.setTipoLogradouro(tipoLogradouro);

                Bairro bairro = new Bairro();
                bairro.setId(resultSet.getLong("id_bairro"));
                bairro.setNome(resultSet.getString("bairro_nome"));

                Endereco endereco = new Endereco();
                endereco.setId(resultSet.getLong("id_endereco"));
                endereco.setCep(resultSet.getString("cep"));
                endereco.setCidade(cidade);
                endereco.setLogradouro(logradouro);
                endereco.setBairro(bairro);

                return endereco;
            }
        }
    }

    return null;
}

public List<Endereco> selecionarTodosEnderecos(Connection conexao) throws SQLException {
    String sql = """
                SELECT e.id_endereco, e.cep,
                       c.id_cidade, c.nome AS cidade_nome,
                       uf.sigla_uf, uf.nome_uf,
                       l.id_logradouro, l.nome AS logradouro_nome,
                       tl.sigla_tipo_logradouro, tl.nome_tipo_logradouro,
                       b.id_bairro, b.nome AS bairro_nome
                FROM endereco e
                JOIN cidade c ON e.id_cidade = c.id_cidade
                JOIN unidade_federativa uf ON c.sigla_uf = uf.sigla_uf
                JOIN logradouro l ON e.id_logradouro = l.id_logradouro
                JOIN tipo_logradouro tl ON l.sigla_tipo_logradouro = tl.sigla_tipo_logradouro
                JOIN bairro b ON e.id_bairro = b.id_bairro;
            """;

    List<Endereco> enderecos = new ArrayList<>();

    try (PreparedStatement preparedStatement = conexao.prepareStatement(sql);
         ResultSet resultSet = preparedStatement.executeQuery()) {

        while (resultSet.next()) {

            UnidadeFederativa uf = new UnidadeFederativa();
            uf.setSigla(resultSet.getString("uf_sigla"));
            uf.setNome(resultSet.getString("uf_nome"));

            Cidade cidade = new Cidade();
            cidade.setId(resultSet.getLong("id_cidade"));
            cidade.setNome(resultSet.getString("cidade_nome"));
            cidade.setUnidadeFederativa(uf);

            TipoLogradouro tipoLogradouro = new TipoLogradouro();
            tipoLogradouro.setSigla(resultSet.getString("sigla_tipo_logradouro"));
            tipoLogradouro.setNome(resultSet.getString("nome_tipo_logradouro"));

            Logradouro logradouro = new Logradouro();
            logradouro.setId(resultSet.getLong("id_logradouro"));
            logradouro.setNome(resultSet.getString("logradouro_nome"));
            logradouro.setTipoLogradouro(tipoLogradouro);

            Bairro bairro = new Bairro();
            bairro.setId(resultSet.getLong("id_bairro"));
            bairro.setNome(resultSet.getString("bairro_nome"));

            Endereco endereco = new Endereco();
            endereco.setId(resultSet.getLong("id_endereco"));
            endereco.setCep(resultSet.getString("cep"));
            endereco.setCidade(cidade);
            endereco.setLogradouro(logradouro);
            endereco.setBairro(bairro);

            enderecos.add(endereco);
        }
    }

    return enderecos;
}
}
