package unioeste.geral.endereco.service;

import unioeste.apoio.bd.ConexaoBD;
import unioeste.geral.endereco.bo.bairro.Bairro;
import unioeste.geral.endereco.bo.cidade.Cidade;
import unioeste.geral.endereco.bo.endereco.Endereco;
import unioeste.geral.endereco.bo.logradouro.Logradouro;
import unioeste.geral.endereco.bo.tipologradouro.TipoLogradouro;
import unioeste.geral.endereco.bo.unidadefederativa.UnidadeFederativa;
import unioeste.geral.endereco.col.*;
import unioeste.geral.endereco.dao.*;
import unioeste.geral.endereco.exception.EnderecoException;
import unioeste.geral.endereco.infra.CepAPI;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class UCEnderecoGeralServicos {

    private EnderecoCOL enderecoCOL;
    private CidadeCOL cidadeCOL;

    private LogradouroCOL logradouroCOL;
    private BairroCOL bairroCOL;
    private EnderecoDAO enderecoDAO;
    private CidadeDAO cidadeDAO;

    private LogradouroDAO logradouroDAO;
    private BairroDAO bairroDAO;
    private UnidadeFederativaDAO unidadeFederativaDAO;
    private TipoLogradouroDAO tipoLogradouroDAO;
    private CepAPI cepAPI;

    public UCEnderecoGeralServicos() {
        this.enderecoCOL = new EnderecoCOL();
        this.cidadeCOL = new CidadeCOL();
        this.logradouroCOL = new LogradouroCOL();
        this.bairroCOL = new BairroCOL();
        this.enderecoDAO = new EnderecoDAO();
        this.cidadeDAO = new CidadeDAO();
        this.logradouroDAO = new LogradouroDAO();
        this.bairroDAO = new BairroDAO();
        this.unidadeFederativaDAO = new UnidadeFederativaDAO();
        this.tipoLogradouroDAO = new TipoLogradouroDAO();
        this.cepAPI = new CepAPI();
    }

    public Endereco cadastrarEndereco(Endereco endereco) throws Exception {
        if (!enderecoCOL.enderecoValido(endereco)) {
            throw new EnderecoException("Endereço inválido.");
        }

        try (Connection conexao = new ConexaoBD().getConexaoComBD()) {
            conexao.setAutoCommit(false);

            if(cidadeCOL.cidadeValida(endereco.getCidade()) || cidadeDAO.selecionarCidadePorId(endereco.getCidade().getId(), conexao) == null) {
                throw new EnderecoException("Cidade inválida.");
            }

            if(!bairroCOL.bairroValido(endereco.getBairro()) || bairroDAO.selecionarBairroPorId(endereco.getBairro().getId(), conexao) == null) {
                throw new EnderecoException("Bairro inválido.");
            }

            if(!logradouroCOL.logradouroValido(endereco.getLogradouro()) || logradouroDAO.selecionarLogradouroPorId(endereco.getLogradouro().getId(), conexao) == null) {
                throw new EnderecoException("Logradouro inválido.");
            }

            Endereco enderecoCadastrado;

            try {
                enderecoCadastrado = enderecoDAO.inserirEndereco(endereco, conexao);
                conexao.commit();
            } catch (Exception e) {
                conexao.rollback();
                throw new EnderecoException("Erro ao cadastrar endereço.");
            }

            return enderecoCadastrado;
        }
    }


    public List<Endereco> obterEnderecoPorCep(String cep) throws Exception {
        if (!enderecoCOL.cepValido(cep)) {
            throw new EnderecoException("Cep inválido " + cep + ".");
        }

        List<Endereco> enderecoList = new ArrayList<>();

        try (Connection conexao = new ConexaoBD().getConexaoComBD()) {
            conexao.setAutoCommit(false);

            try {
                enderecoList = enderecoDAO.selecionarEnderecosPorCep(cep, conexao);
                conexao.commit();
            } catch (Exception e) {
                conexao.rollback();
            }

        }

        return enderecoList;
    }

    public Endereco obterEnderecoPorId(Long id) throws Exception {
        if (!enderecoCOL.idValido(id)) {
            throw new EnderecoException("Id do endereço inválido " + id + ".");
        }

        try(Connection conexao = new ConexaoBD().getConexaoComBD()) {
            conexao.setAutoCommit(false);

            Endereco endereco;
            try {
                endereco = enderecoDAO.selecionarEnderecoPorId(id, conexao);
                conexao.commit();
            } catch (Exception e) {
                conexao.rollback();
                throw new EnderecoException("Não foi possível buscar o endereço pelo id " + id + ".");
            }

            return endereco;
        }
    }

    public Endereco obterEnderecoExterno(String cep) throws Exception {
        if (!enderecoCOL.cepValido(cep)) {
            throw new EnderecoException("Formato de CEP inválido (" + cep + ")");
        }

        Endereco endereco = cepAPI.obterEnderecoExterno(cep);

        if (endereco == null) {
            throw new EnderecoException("CEP inexistente (" + cep + ")");
        }

        return endereco;
    }

    public Cidade obterCidade(Long id) throws Exception {
        if (!cidadeCOL.idValido(id)) {
            throw new EnderecoException("ID da cidade inválido (" + id + ")");
        }

        try(Connection conexao = new ConexaoBD().getConexaoComBD()) {
            conexao.setAutoCommit(false);
            Cidade cidade;

            try {
                cidade = cidadeDAO.selecionarCidadePorId(id, conexao);
                conexao.commit();
            } catch (Exception e) {
                conexao.rollback();
                throw new EnderecoException("Não foi possível buscar a cidade pelo id " + id + ".");
            }

            return cidade;
        }
    }

    public List<Endereco> obterListaDeEnderecos() throws Exception {
        List<Endereco> enderecos =  new ArrayList<>();

        try(Connection conexao = new ConexaoBD().getConexaoComBD()) {
            conexao.setAutoCommit(false);

            try {
                enderecos = enderecoDAO.selecionarTodosEnderecos(conexao);
                conexao.commit();
            } catch (Exception e) {
                conexao.rollback();
            }
        }

        return enderecos;
    }

    public List<UnidadeFederativa> obterListaDeUnidadesFederativas() throws Exception {
        List<UnidadeFederativa> unidadesFederativas = new ArrayList<>();

        try (Connection conexao = new ConexaoBD().getConexaoComBD()) {
            conexao.setAutoCommit(false);

            try {
                unidadesFederativas = unidadeFederativaDAO.selecionarTodasUnidadesFederativas(conexao);
                conexao.commit();
            } catch (Exception e) {
                conexao.rollback();
            }
        }

        return unidadesFederativas;
    }

    public List<Cidade> obterListaDeCidades() throws Exception {
        List<Cidade> cidades = new ArrayList<>();

        try (Connection conexao = new ConexaoBD().getConexaoComBD()) {
            conexao.setAutoCommit(false);

            try {
                cidades = cidadeDAO.selecionarTodasCidades(conexao);
                conexao.commit();
            } catch (Exception e) {
                conexao.rollback();
            }
        }

        return cidades;
    }

    public List<Bairro> obterListaDeBairros() throws Exception {
        List<Bairro> bairros = new ArrayList<>();

        try (Connection conexao = new ConexaoBD().getConexaoComBD()) {
            conexao.setAutoCommit(false);

            try {
                bairros = bairroDAO.selecionarTodosBairros(conexao);
                conexao.commit();
            } catch (Exception e) {
                conexao.rollback();
            }
        }

        return bairros;
    }

    public List<Logradouro> obterListaDeLogradouros() throws Exception {
        List<Logradouro> logradouros = new ArrayList<>();

        try (Connection conexao = new ConexaoBD().getConexaoComBD()) {
            conexao.setAutoCommit(false);

            try {
                logradouros = logradouroDAO.selecionarTodosLogradouros(conexao);
                conexao.commit();
            } catch (Exception e) {
                conexao.rollback();
            }
        }

        return logradouros;
    }

    public List<TipoLogradouro> obterListaDeTipoLogradouros() throws Exception {
        List<TipoLogradouro> tiposLogradouro = new ArrayList<>();

        try (Connection conexao = new ConexaoBD().getConexaoComBD()) {
            conexao.setAutoCommit(false);

            try {
                tiposLogradouro = tipoLogradouroDAO.selecionarTodosTiposLogradouro(conexao);
                conexao.commit();
            } catch (Exception e) {
                conexao.rollback();
            }
        }

        return tiposLogradouro;
    }

    public static void main(String[] args) throws Exception {
        UCEnderecoGeralServicos servicos = new UCEnderecoGeralServicos();

        System.out.println("==== Teste: obterListaDeUnidadesFederativas ====");
        List<UnidadeFederativa> unidadesFederativas = servicos.obterListaDeUnidadesFederativas();
        unidadesFederativas.forEach(uf -> System.out.println(uf.getSigla() + " - " + uf.getNome()));

        System.out.println("\n==== Teste: obterListaDeCidades ====");
        List<Cidade> cidades = servicos.obterListaDeCidades();
        cidades.forEach(cidade -> {
            System.out.println("Cidade: " + cidade.getNome() + " | UF: " + cidade.getUnidadeFederativa().getSigla());
        });

        System.out.println("\n==== Teste: obterListaDeBairros ====");
        List<Bairro> bairros = servicos.obterListaDeBairros();
        bairros.forEach(bairro -> System.out.println("Bairro: " + bairro.getNome()));

        System.out.println("\n==== Teste: obterListaDeLogradouros ====");
        List<Logradouro> logradouros = servicos.obterListaDeLogradouros();
        logradouros.forEach(logradouro -> {
            System.out.println("Logradouro: " + logradouro.getNome() +
                    " | Tipo: " + logradouro.getTipoLogradouro().getNome());
        });

        System.out.println("\n==== Teste: obterListaDeTipoLogradouros ====");
        List<TipoLogradouro> tiposLogradouro = servicos.obterListaDeTipoLogradouros();
        tiposLogradouro.forEach(tipo -> System.out.println("Tipo: " + tipo.getSigla() + " - " + tipo.getNome()));

        System.out.println("\n==== Teste: obterListaDeEnderecos ====");
        List<Endereco> enderecos = servicos.obterListaDeEnderecos();
        enderecos.forEach(endereco -> {
            System.out.println("Endereço ID: " + endereco.getId());
            System.out.println("CEP: " + endereco.getCep());
            System.out.println("Cidade: " + endereco.getCidade().getNome() + " | UF: " + endereco.getCidade().getUnidadeFederativa().getSigla());
            System.out.println("Logradouro: " + endereco.getLogradouro().getNome() + " | Tipo: " + endereco.getLogradouro().getTipoLogradouro().getNome());
            System.out.println("Bairro: " + endereco.getBairro().getNome());
            System.out.println("-----------------------------");
        });

        System.out.println("\n==== Teste: obterCidade (ID = 1) ====");
        try {
            Cidade cidade = servicos.obterCidade(1L);
            System.out.println("Cidade: " + cidade.getNome() + " | UF: " + cidade.getUnidadeFederativa().getNome());
        } catch (Exception e) {
            System.out.println("Erro ao buscar cidade: " + e.getMessage());
        }

        System.out.println("\n==== Teste: obterEnderecoPorId (ID = 1) ====");
        try {
            Endereco endereco = servicos.obterEnderecoPorId(1L);
            System.out.println("Endereço ID: " + endereco.getId());
            System.out.println("CEP: " + endereco.getCep());
            System.out.println("Cidade: " + endereco.getCidade().getNome() + " | UF: " + endereco.getCidade().getUnidadeFederativa().getSigla());
            System.out.println("Logradouro: " + endereco.getLogradouro().getNome() + " | Tipo: " + endereco.getLogradouro().getTipoLogradouro().getNome());
            System.out.println("Bairro: " + endereco.getBairro().getNome());
        } catch (Exception e) {
            System.out.println("Erro ao buscar endereço: " + e.getMessage());
        }

        System.out.println("\n==== Teste: obterEnderecoPorCep (CEP = '80010000') ====");
        try {
            List<Endereco> enderecosPorCep = servicos.obterEnderecoPorCep("80010000");
            if (!enderecosPorCep.isEmpty()) {
                enderecosPorCep.forEach(endereco -> {
                    System.out.println("Endereço ID: " + endereco.getId());
                    System.out.println("CEP: " + endereco.getCep());
                    System.out.println("Cidade: " + endereco.getCidade().getNome() + " | UF: " + endereco.getCidade().getUnidadeFederativa().getSigla());
                    System.out.println("Logradouro: " + endereco.getLogradouro().getNome() + " | Tipo: " + endereco.getLogradouro().getTipoLogradouro().getNome());
                    System.out.println("Bairro: " + endereco.getBairro().getNome());
                    System.out.println("-----------------------------");
                });
            } else {
                System.out.println("Nenhum endereço encontrado para o CEP informado.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar endereços por CEP: " + e.getMessage());
        }

        System.out.println("\n==== Teste: obterEnderecoExterno (CEP = '80010000') ====");
        try {
            Endereco enderecoExterno = servicos.obterEnderecoExterno("80010000");
            System.out.println("CEP: " + enderecoExterno.getCep());
            System.out.println("Cidade: " + enderecoExterno.getCidade().getNome());
            System.out.println("Logradouro: " + enderecoExterno.getLogradouro().getNome());
            System.out.println("Bairro: " + enderecoExterno.getBairro().getNome());
        } catch (Exception e) {
            System.out.println("Erro ao buscar endereço externo: " + e.getMessage());
        }

        System.out.println("\n==== Teste finalizado ====");
    }



}
