package unioeste.geral.endereco.col;

import unioeste.geral.endereco.bo.cidade.Cidade;
import unioeste.geral.endereco.dao.CidadeDAO;

public class CidadeCOL {

	public static boolean idValido(Long id) {
		return id != null && id > 0;
	}

	public static boolean cidadeValida(Cidade cidade) throws Exception {
		return cidade != null &&
				idValido(cidade.getId()) &&
				cidade.getNome() != null &&
				!cidade.getNome().trim().isEmpty() &&
				UnidadeFederativaCOL.unidadeFederativaValida(cidade.getUnidadeFederativa()) &&
				UnidadeFederativaCOL.unidadeFederativaExiste(cidade.getUnidadeFederativa());
	}

	public static boolean cidadeExiste(Cidade cidade) throws Exception {
		if (cidade == null || !idValido(cidade.getId()) || cidade.getUnidadeFederativa() == null) {
			return false;
		}

		Cidade cidadeAux = CidadeDAO.selectCidadePorId(cidade.getId());
		return cidadeAux != null &&
				cidadeAux.getUnidadeFederativa() != null &&
				cidadeAux.getUnidadeFederativa().getSigla().equals(cidade.getUnidadeFederativa().getSigla());
	}

}
