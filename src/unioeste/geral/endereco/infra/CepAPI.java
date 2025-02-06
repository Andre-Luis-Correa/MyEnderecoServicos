package unioeste.geral.endereco.infra;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONTokener;
import org.json.JSONObject;

import irrf.geral.bo.endereco.Bairro;
import irrf.geral.bo.endereco.Cidade;
import irrf.geral.bo.endereco.Endereco;
import irrf.geral.bo.endereco.Estado;
import irrf.geral.bo.endereco.Logradouro;

public class CepAPI {
	static String web_service = "http://viacep.com.br/ws/";
	
	
	public static Endereco getCEP (String CEP) throws Exception {
		String str_url = web_service + CEP + "/json";
		
		URL url = new URL(str_url);
		HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
		
		if(conexao.getResponseCode()!=200) throw new Exception("Não foi possível conectar à API");
		
		BufferedReader resposta = new BufferedReader(new InputStreamReader((conexao.getInputStream())));
		JSONTokener tokener = new JSONTokener(resposta);
		JSONObject jobject = new JSONObject(tokener);
		
		if(jobject.has("erro")) throw new Exception("CEP inexistente");
		
		Endereco endereco = new Endereco();
		Estado estado = new Estado();
		Cidade cidade = new Cidade();
		Bairro bairro = new Bairro();
		Logradouro logradouro = new Logradouro();
		
		estado.setSigla(jobject.getString("uf"));
		cidade.setId(-1);
		cidade.setNome(jobject.getString("localidade"));
		cidade.setEstado(estado);
		bairro.setId(-1);
		bairro.setNome(jobject.getString("bairro"));
		logradouro.setId(-1);
		logradouro.setNome(jobject.getString("logradouro"));
		
		endereco.setId(-1);
		endereco.setCEP(CEP);
		endereco.setCidade(cidade);
		endereco.setBairro(bairro);
		endereco.setLogradouro(logradouro);
	
		return endereco;
		
	}
	
	public static void main (String[] args) throws Exception {
		getCEP("85851290");
	}
}
