package unioeste.geral.endereco.dao;

import unioeste.apoio.bd.ConexaoBD;
import unioeste.geral.endereco.bo.endereco.Endereco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

	public static Endereco selectEnderecoPorCep(String cep) throws Exception {
		String sql = "SELECT id_endereco, id_cidade, id_bairro, id_logradouro FROM endereco WHERE cep = ?";

		try (Connection conexao = new ConexaoBD().getConexaoComBD();
			 PreparedStatement cmd = conexao.prepareStatement(sql)) {

			cmd.setString(1, cep);
			try (ResultSet result = cmd.executeQuery()) {
				if (result.next()) {
					Endereco endereco = new Endereco();
					endereco.setId(result.getLong("id_endereco"));
					endereco.setCidade(CidadeDAO.selectCidadePorId(result.getLong("id_cidade")));
					endereco.setBairro(BairroDAO.selectBairroPorId(result.getLong("id_bairro")));
					endereco.setLogradouro(LogradouroDAO.selectLogradouroPorId(result.getLong("id_logradouro")));
					endereco.setCep(cep);
					return endereco;
				}
			}
		} catch (SQLException e) {
			throw new Exception("Erro ao buscar endereço pelo CEP: " + cep, e);
		}

		return null;
	}

	public static void insertEndereco(Endereco endereco) throws Exception {
		String sql = "INSERT INTO endereco (cep, id_cidade, id_bairro, id_logradouro) VALUES (?, ?, ?, ?)";

		try (Connection conexao = new ConexaoBD().getConexaoComBD();
			 PreparedStatement cmd = conexao.prepareStatement(sql)) {

			cmd.setString(1, endereco.getCep());
			cmd.setLong(2, endereco.getCidade().getId());
			cmd.setLong(3, endereco.getBairro().getId());
			cmd.setLong(4, endereco.getLogradouro().getId());
			cmd.executeUpdate();
		} catch (SQLException e) {
			throw new Exception("Erro ao inserir endereço: " + endereco, e);
		}
	}
}
