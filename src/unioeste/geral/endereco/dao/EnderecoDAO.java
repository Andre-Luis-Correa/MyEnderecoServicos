package unioeste.geral.endereco.dao;

import unioeste.apoio.bd.ConexaoBD;
import unioeste.geral.endereco.bo.endereco.Endereco;
import unioeste.geral.endereco.bo.unidadefederativa.UnidadeFederativa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnderecoDAO {

	public static Endereco selectEnderecoPorId(Long id) throws Exception {
		String sql = "SELECT cep, id_cidade, id_bairro, id_logradouro FROM endereco WHERE id_endereco = ?";

		try (Connection conexao = new ConexaoBD().getConexaoComBD();
			 PreparedStatement cmd = conexao.prepareStatement(sql)) {

			cmd.setLong(1, id);
			try (ResultSet result = cmd.executeQuery()) {
				if (result.next()) {
					Endereco endereco = new Endereco();
					endereco.setId(id);
					endereco.setCidade(CidadeDAO.selectCidadePorId(result.getLong("id_cidade")));
					endereco.setBairro(BairroDAO.selectBairroPorId(result.getLong("id_bairro")));
					endereco.setLogradouro(LogradouroDAO.selectLogradouroPorId(result.getLong("id_logradouro")));
					endereco.setCep(result.getString("cep"));
					return endereco;
				}
			}
		} catch (SQLException e) {
			throw new Exception("Erro ao buscar endereço pelo ID: " + id, e);
		}

		return null;
	}

	public static List<Endereco> selectEnderecoPorCep(String cep) throws Exception {
		String sql = "SELECT id_endereco, id_cidade, id_bairro, id_logradouro FROM endereco WHERE cep = ?";
		List<Endereco> enderecos = new ArrayList<>();

		try (Connection conexao = new ConexaoBD().getConexaoComBD();
			 PreparedStatement cmd = conexao.prepareStatement(sql)) {

			cmd.setString(1, cep);
			try (ResultSet result = cmd.executeQuery()) {
				while (result.next()) {
					Endereco endereco = new Endereco();
					endereco.setId(result.getLong("id_endereco"));
					endereco.setCidade(CidadeDAO.selectCidadePorId(result.getLong("id_cidade")));
					endereco.setBairro(BairroDAO.selectBairroPorId(result.getLong("id_bairro")));
					endereco.setLogradouro(LogradouroDAO.selectLogradouroPorId(result.getLong("id_logradouro")));
					endereco.setCep(cep);

					enderecos.add(endereco);
				}
			}
		} catch (SQLException e) {
			throw new Exception("Erro ao buscar endereços pelo CEP: " + cep, e);
		}

		return enderecos;
	}

	public static Endereco insertEndereco(Endereco endereco) throws Exception {
		String sql = "INSERT INTO endereco (cep, id_cidade, id_bairro, id_logradouro) VALUES (?, ?, ?, ?)";
		String generatedColumns[] = {"id_endereco"};

		try (Connection conexao = new ConexaoBD().getConexaoComBD();
			 PreparedStatement cmd = conexao.prepareStatement(sql, generatedColumns)) {

			cmd.setString(1, endereco.getCep());
			cmd.setLong(2, endereco.getCidade().getId());
			cmd.setLong(3, endereco.getBairro().getId());
			cmd.setLong(4, endereco.getLogradouro().getId());
			cmd.executeUpdate();

			try (ResultSet generatedKeys = cmd.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					endereco.setId(generatedKeys.getLong(1));
				} else {
					throw new SQLException("Falha ao obter o ID do endereço inserido.");
				}
			}
		} catch (SQLException e) {
			throw new Exception("Erro ao inserir endereço: " + endereco, e);
		}

		return endereco;
	}

	public static List<Endereco> selectTodosEnderecos() throws Exception {
		List<Endereco> enderecoList = new ArrayList<>();
		String sql = "SELECT * FROM endereco;";

		try (Connection conn = new ConexaoBD().getConexaoComBD();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {

			ResultSet result = stmt.executeQuery();

			while (result.next()) {
				Endereco endereco = new Endereco();
				endereco.setId(result.getLong("id_endereco"));
				endereco.setCidade(CidadeDAO.selectCidadePorId(result.getLong("id_cidade")));
				endereco.setBairro(BairroDAO.selectBairroPorId(result.getLong("id_bairro")));
				endereco.setLogradouro(LogradouroDAO.selectLogradouroPorId(result.getLong("id_logradouro")));
				endereco.setCep(result.getString("cep"));
				enderecoList.add(endereco);
			}

		} catch (Exception e) {
			throw new Exception("Erro ao buscar Enderecos", e);
		}

		return enderecoList;
	}
}
