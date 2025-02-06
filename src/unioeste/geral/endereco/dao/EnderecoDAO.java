package unioeste.geral.endereco.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import irrf.geral.bo.endereco.Endereco;

public class EnderecoDAO {

	public static Endereco selectEnderecoId (int id, Connection conexao) throws Exception {

		StringBuffer sql = new StringBuffer("SELECT endereço.CEP, endereço.id_cidade, ");
		sql.append("endereço.id_bairro, endereço.id_logradouro FROM endereço ");
		sql.append("WHERE endereço.id_endereço = ?;");
		PreparedStatement cmd = conexao.prepareStatement(sql.toString());
		cmd.setInt(1, id);
		ResultSet result = cmd.executeQuery();
		
		if(result.next()) {
			Endereco endereco = new Endereco();
			endereco.setId(id);
			endereco.setCidade(CidadeDAO.selectCidade(result.getInt("id_cidade"), conexao));
			endereco.setBairro(BairroDAO.selectBairro(result.getInt("id_bairro"), conexao));
			endereco.setLogradouro(LogradouroDAO.selectLogradouro(result.getInt("id_logradouro"), conexao));
			endereco.setCEP(result.getString("CEP"));
			
			return endereco;
		}
		
		return null;
	}
	
	public static Endereco selectEnderecoCEP (String CEP, Connection conexao) throws Exception {
		
		StringBuffer sql = new StringBuffer("SELECT endereço.id_endereço, endereço.id_cidade, ");
		sql.append("endereço.id_bairro, endereço.id_logradouro FROM endereço ");
		sql.append("WHERE endereço.CEP = ?");
		PreparedStatement cmd = conexao.prepareStatement(sql.toString());
		cmd.setString(1, CEP);
		ResultSet result = cmd.executeQuery();
		
		if(result.next()) {
			Endereco endereco = new Endereco();
			endereco.setId(result.getInt("id_endereço"));
			endereco.setCidade(CidadeDAO.selectCidade(result.getInt("id_cidade"), conexao));
			endereco.setBairro(BairroDAO.selectBairro(result.getInt("id_bairro"), conexao));
			endereco.setLogradouro(LogradouroDAO.selectLogradouro(result.getInt("id_logradouro"), conexao));
			endereco.setCEP(CEP);
			
			return endereco;
		}
		
		return null;
	}
	
	public static void insertEndereco (Endereco endereco, Connection conexao) throws Exception {
		
		StringBuffer sql = new StringBuffer("INSERT INTO endereço (CEP, sigla_estado, id_cidade, id_bairro, id_logradouro) VALUES");
		sql.append("(?, ?, ?, ?, ?);");
		PreparedStatement cmd = conexao.prepareStatement(sql.toString());
		cmd.setString(1, endereco.getCEP());
		cmd.setString(2, endereco.getCidade().getEstado().getSigla());
		cmd.setInt(3, endereco.getCidade().getId());
		cmd.setInt(4, endereco.getBairro().getId());
		cmd.setInt(5, endereco.getLogradouro().getId());
		cmd.executeUpdate();
		
	}
}
