package unioeste.geral.endereco.service;

import unioeste.geral.endereco.bo.cidade.Cidade;
import unioeste.geral.endereco.bo.endereco.Endereco;
import unioeste.geral.endereco.col.CidadeCOL;
import unioeste.geral.endereco.col.EnderecoCOL;
import unioeste.geral.endereco.dao.CidadeDAO;
import unioeste.geral.endereco.dao.EnderecoDAO;
import unioeste.geral.endereco.exception.EnderecoException;
import unioeste.geral.endereco.infra.CepAPI;

public class UCEnderecoGeralServicos {

    public static void cadastrarEndereco (Endereco endereco) throws Exception {
        if(!EnderecoCOL.EnderecoValido(endereco)) throw new EnderecoException("Endereço inválido");
        if(EnderecoCOL.EnderecoCadastrado(endereco)) throw new EnderecoException("CEP já cadastrado");

        EnderecoDAO.insertEndereco(endereco);

        System.out.println("Endereço cadastrado");
    }


    public static Endereco obterEnderecoPorCEP(String cep) throws Exception {
        if(!EnderecoCOL.CEPValido(cep)) throw new EnderecoException("Formato de CEP inválido (" + cep + ")");

        Endereco endereco = EnderecoDAO.selectEnderecoCEP(cep);

        if(endereco == null) throw new EnderecoException("Endereço não cadastrado");

        return endereco;
    }

    public static Endereco obterEnderecoPorID(Long id) throws Exception {
        if(!EnderecoCOL.idValido(id)) throw new EnderecoException("Id do endereço inválido (" + id + ")");

        Endereco endereco = EnderecoDAO.selectEnderecoId(id);

        if(endereco==null) throw new EnderecoException("Endereço não cadastrado");

        return endereco;
    }

    public static Endereco obterEnderecoExterno (String CEP) throws Exception {
        if(!EnderecoCOL.CEPValido(CEP)) throw new EnderecoException("Formato de CEP inválido (" + CEP + ")");

        Endereco endereco = CepAPI.getCEP(CEP);

        if(endereco==null) throw new EnderecoException("CEP inexistente (" + CEP + ")");


        return endereco;
    }

    public static Cidade obterCidade(Long id) throws Exception {
        if(!CidadeCOL.idValido(id)) throw new EnderecoException("ID da cidade inválido (" + id + ")");

        Cidade cidade = CidadeDAO.selectCidade(id);

        if(cidade==null) throw new EnderecoException("Cidade não cadastrada");

        connection.close();

        return cidade;
    }


    public static void main(String[] args) {

    }
}
