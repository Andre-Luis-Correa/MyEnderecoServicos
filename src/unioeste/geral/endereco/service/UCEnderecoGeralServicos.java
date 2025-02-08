package unioeste.geral.endereco.service;

import unioeste.geral.endereco.bo.bairro.Bairro;
import unioeste.geral.endereco.bo.cidade.Cidade;
import unioeste.geral.endereco.bo.endereco.Endereco;
import unioeste.geral.endereco.bo.logradouro.Logradouro;
import unioeste.geral.endereco.bo.tipologradouro.TipoLogradouro;
import unioeste.geral.endereco.bo.unidadefederativa.UnidadeFederativa;
import unioeste.geral.endereco.col.CidadeCOL;
import unioeste.geral.endereco.col.EnderecoCOL;
import unioeste.geral.endereco.dao.CidadeDAO;
import unioeste.geral.endereco.dao.EnderecoDAO;
import unioeste.geral.endereco.exception.EnderecoException;
import unioeste.geral.endereco.infra.CepAPI;

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
        try {
            // Criando objetos necessários
            UnidadeFederativa uf = new UnidadeFederativa("PR", "Paraná");
            Cidade cidade = new Cidade(1L, "Cascavel", uf);
            TipoLogradouro tipoLogradouro = new TipoLogradouro("R", "Rua");
            Logradouro logradouro = new Logradouro(1L, "Sete de Setembro", tipoLogradouro);
            Bairro bairro = new Bairro(1L, "Centro");

            Endereco endereco = new Endereco(1L, "85875000", cidade, logradouro, bairro);

//            // Teste cadastrarEndereco
//            System.out.println("Testando cadastrarEndereco...");
//            cadastrarEndereco(endereco);

            // Teste obterEnderecoPorCep
            System.out.println("Testando obterEnderecoPorCep...");
            Endereco e1 = obterEnderecoPorCep("80000000");
            System.out.println("Endereço obtido: " + e1.getCep());

            // Teste obterEnderecoPorId
            System.out.println("Testando obterEnderecoPorId...");
            Endereco e2 = obterEnderecoPorId(1L);
            System.out.println("Endereço obtido: " + e2.getId());

            // Teste obterEnderecoExterno
            System.out.println("Testando obterEnderecoExterno...");
            Endereco e3 = obterEnderecoExterno("85810220");
            System.out.println("Endereço externo obtido: " + e3.getCep() + " - " + e3.getLogradouro().getNome() + " - " + e3.getCidade().getNome());

            // Teste obterCidade
            System.out.println("Testando obterCidade...");
            Cidade c1 = obterCidade(1L);
            System.out.println("Cidade obtida: " + c1.getNome());

        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}
