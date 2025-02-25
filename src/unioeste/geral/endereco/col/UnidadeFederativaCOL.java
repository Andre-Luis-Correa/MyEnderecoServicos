package unioeste.geral.endereco.col;

import unioeste.geral.endereco.bo.unidadefederativa.UnidadeFederativa;

public class UnidadeFederativaCOL {

	public static boolean siglaValida(String sigla) {
		return sigla != null && sigla.matches("[A-Z]{2}");
	}
	
	public static boolean unidadeFederativaValida(UnidadeFederativa unidadeFederativa) {
		return unidadeFederativa != null &&
				siglaValida(unidadeFederativa.getSigla()) &&
				unidadeFederativa.getNome() != null &&
				!unidadeFederativa.getNome().trim().isEmpty();
	}
}
