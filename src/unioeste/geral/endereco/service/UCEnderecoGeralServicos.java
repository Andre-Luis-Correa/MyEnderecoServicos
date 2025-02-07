package unioeste.geral.endereco.service;

import unioeste.geral.endereco.bo.cidade.Cidade;
import unioeste.geral.endereco.bo.endereco.Endereco;
import unioeste.geral.endereco.col.CidadeCOL;
import unioeste.geral.endereco.col.EnderecoCOL;
import unioeste.geral.endereco.dao.CidadeDAO;
import unioeste.geral.endereco.dao.EnderecoDAO;
import unioeste.geral.endereco.exception.EnderecoException;
import unioeste.geral.endereco.infra.CepAPI;

import java.util.Objects;

public class UCEnderecoGeralServicos {

    public static void cadastrarEndereco(Endereco endereco) throws Exception {
        if (!EnderecoCOL.enderecoValido(endereco)) {
            throw new EnderecoException("Endereço inválido");
        }

        if (EnderecoCOL.enderecoExistePorCep(endereco)) {
            throw new EnderecoException("CEP já cadastrado");
        }

        EnderecoDAO.insertEndereco(endereco);
        System.out.println("Endereço cadastrado");
    }

    public static Endereco obterEnderecoPorCep(String cep) throws Exception {
        if (!EnderecoCOL.cepValido(cep)) {
            throw new EnderecoException("Formato de CEP inválido (" + cep + ")");
        }

        Endereco endereco = EnderecoDAO.selectEnderecoPorCep(cep);

        if (endereco == null) {
            throw new EnderecoException("Endereço não cadastrado");
        }

        return endereco;
    }

    public static Endereco obterEnderecoPorId(Long id) throws Exception {
        if (!EnderecoCOL.idValido(id)) {
            throw new EnderecoException("Id do endereço inválido (" + id + ")");
        }

        Endereco endereco = EnderecoDAO.selectEnderecoPorId(id);

        if (endereco == null) {
            throw new EnderecoException("Endereço não cadastrado");
        }

        return endereco;
    }

    public static Endereco obterEnderecoExterno(String cep) throws Exception {
        if (!EnderecoCOL.cepValido(cep)) {
            throw new EnderecoException("Formato de CEP inválido (" + cep + ")");
        }

        Endereco endereco = CepAPI.getCep(cep);

        if (endereco == null) {
            throw new EnderecoException("CEP inexistente (" + cep + ")");
        }

        return endereco;
    }

    public static Cidade obterCidade(Long id) throws Exception {
        if (!CidadeCOL.idValido(id)) {
            throw new EnderecoException("ID da cidade inválido (" + id + ")");
        }

        Cidade cidade = CidadeDAO.selectCidadePorId(id);

        if (cidade == null) {
            throw new EnderecoException("Cidade não cadastrada");
        }

        return cidade;
    }

    public static void main(String[] args) {
        // Método principal vazio
    }
}
