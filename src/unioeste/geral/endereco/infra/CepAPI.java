package unioeste.geral.endereco.infra;

import unioeste.geral.endereco.bo.endereco.Endereco;
import unioeste.geral.endereco.bo.cidade.Cidade;
import unioeste.geral.endereco.bo.bairro.Bairro;
import unioeste.geral.endereco.bo.logradouro.Logradouro;
import unioeste.geral.endereco.bo.unidadefederativa.UnidadeFederativa;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CepAPI {
	private static final String WEB_SERVICE = "http://viacep.com.br/ws/";

	public static Endereco getCep(String cep) throws Exception {
		String strUrl = WEB_SERVICE + cep + "/json";

		URL url = new URL(strUrl);
		HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
		conexao.setRequestMethod("GET");

		if (conexao.getResponseCode() != 200) {
			throw new Exception("Erro ao conectar à API. Código HTTP: " + conexao.getResponseCode());
		}

		StringBuilder resposta = new StringBuilder();
		try (BufferedReader leitor = new BufferedReader(new InputStreamReader(conexao.getInputStream()))) {
			String linha;
			while ((linha = leitor.readLine()) != null) {
				resposta.append(linha);
			}
		}

		// Convertendo JSON manualmente para Map<String, String>
		Map<String, String> jsonMap = jsonToMap(resposta.toString());

		if (jsonMap.containsKey("erro")) {
			throw new Exception("CEP inexistente.");
		}

		Endereco endereco = new Endereco();
		UnidadeFederativa unidadeFederativa = new UnidadeFederativa();
		Cidade cidade = new Cidade();
		Bairro bairro = new Bairro();
		Logradouro logradouro = new Logradouro();

		unidadeFederativa.setSigla(jsonMap.getOrDefault("uf", ""));
		unidadeFederativa.setNome(jsonMap.getOrDefault("estado", ""));

		cidade.setId(-1L);
		cidade.setNome(jsonMap.getOrDefault("localidade", ""));
		cidade.setUnidadeFederativa(unidadeFederativa);

		bairro.setId(-1L);
		bairro.setNome(jsonMap.getOrDefault("bairro", ""));

		logradouro.setId(-1L);
		logradouro.setNome(jsonMap.getOrDefault("logradouro", ""));

		endereco.setId(-1L);
		endereco.setCep(cep);
		endereco.setCidade(cidade);
		endereco.setBairro(bairro);
		endereco.setLogradouro(logradouro);

		return endereco;
	}

	private static Map<String, String> jsonToMap(String json) {
		Map<String, String> map = new HashMap<>();
		json = json.replaceAll("[{}\"]", ""); // Remover chaves e aspas
		String[] pares = json.split(",");

		for (String par : pares) {
			String[] chaveValor = par.split(":");
			if (chaveValor.length == 2) {
				map.put(chaveValor[0].trim(), chaveValor[1].trim());
			}
		}
		return map;
	}

	public static void main(String[] args) {
		try {
			Endereco endereco = getCep("85875000");

			System.out.println("\n===== Endereço Encontrado =====");
			System.out.println("CEP: " + endereco.getCep());
			System.out.println("Logradouro: " + (endereco.getLogradouro().getNome().isEmpty() ? "Não informado" : endereco.getLogradouro().getNome()));
			System.out.println("Bairro: " + (endereco.getBairro().getNome().isEmpty() ? "Não informado" : endereco.getBairro().getNome()));
			System.out.println("Cidade: " + endereco.getCidade().getNome());
			System.out.println("Estado: " + endereco.getCidade().getUnidadeFederativa().getSigla());
			System.out.println("UF: " + (endereco.getCidade().getUnidadeFederativa().getNome().isEmpty() ? "Não informado" :endereco.getCidade().getUnidadeFederativa().getNome()));

			System.out.println("==============================\n");

		} catch (Exception e) {
			System.err.println("Erro: " + e.getMessage());
		}
	}
}