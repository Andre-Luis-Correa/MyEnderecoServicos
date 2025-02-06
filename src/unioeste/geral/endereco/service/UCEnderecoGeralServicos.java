package unioeste.geral.endereco.service;

import unioeste.geral.endereco.bo.bairro.Bairro;

public class UCEnderecoGeralServicos {

    private Bairro bairro = new Bairro(1L, "bairro");

    public Bairro getBairro() {
        return bairro;
    }
    public static void main(String[] args) {

        UCEnderecoGeralServicos ucEnderecoGeralServicos = new UCEnderecoGeralServicos();
        System.out.println(ucEnderecoGeralServicos.getBairro().getNome());
    }
}
