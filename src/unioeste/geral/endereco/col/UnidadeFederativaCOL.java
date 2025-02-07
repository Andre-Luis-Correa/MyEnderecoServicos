package unioeste.geral.endereco.col;

import unioeste.geral.endereco.bo.unidadefederativa.UnidadeFederativa;
import unioeste.geral.endereco.dao.UnidadeFederativaDAO;

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
	
	public static boolean unidadeFederativaExiste(UnidadeFederativa unidadeFederativa) throws Exception {
		if (unidadeFederativa == null || unidadeFederativa.getSigla() == null || unidadeFederativa.getSigla().trim().isEmpty()) return false;
		return UnidadeFederativaDAO.selectUnidadeFederativaPorSigla(unidadeFederativa.getSigla()) != null;
	}
}
