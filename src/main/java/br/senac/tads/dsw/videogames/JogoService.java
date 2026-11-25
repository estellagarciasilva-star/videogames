package br.senac.tads.dsw.videogames;

import java.util.List;
import java.util.Optional;

public interface JogoService {

	List<JogoDto> listar();

	Optional<JogoDto> obter(Integer id);

	JogoDto incluir(JogoDto dto);

	JogoDto alterar(Integer id, JogoDto dto);

	void excluir(Integer id);
}