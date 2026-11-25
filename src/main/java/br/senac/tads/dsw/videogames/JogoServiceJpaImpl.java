package br.senac.tads.dsw.videogames;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.senac.tads.dsw.videogames.entidade.JogoEntity;
import br.senac.tads.dsw.videogames.repositorio.JogoRepository;

@Service
public class JogoServiceJpaImpl implements JogoService {

	private final JogoRepository jogoRepository;

	public JogoServiceJpaImpl(JogoRepository jogoRepository) {
		this.jogoRepository = jogoRepository;
	}

	// Converte a entidade (tabela) para o DTO (JSON)
	private JogoDto toDto(JogoEntity entity) {
		JogoDto dto = new JogoDto();
		dto.setId(entity.getId());
		dto.setTitulo(entity.getTitulo());
		dto.setAno(entity.getAno());
		dto.setPlataforma(entity.getPlataforma());
		dto.setDisponivel(entity.getDisponivel());
		dto.setObservacoes(entity.getObservacoes());
		return dto;
	}

	// Copia os dados do DTO para a entidade (usado na inclusão e na alteração)
	private void copiarParaEntity(JogoDto dto, JogoEntity entity) {
		entity.setTitulo(dto.getTitulo());
		entity.setAno(dto.getAno());
		entity.setPlataforma(dto.getPlataforma());
		entity.setDisponivel(dto.getDisponivel());
		entity.setObservacoes(dto.getObservacoes());
	}

	@Override
	@Transactional(readOnly = true)
	public List<JogoDto> listar() {
		List<JogoDto> resultado = new ArrayList<>();
		for (JogoEntity entity : jogoRepository.findAll()) {
			resultado.add(toDto(entity));
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<JogoDto> obter(Integer id) {
		Optional<JogoEntity> optEntity = jogoRepository.findById(id);
		if (optEntity.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(toDto(optEntity.get()));
	}

	@Override
	@Transactional
	public JogoDto incluir(JogoDto dto) {
		JogoEntity entity = new JogoEntity();
		copiarParaEntity(dto, entity);
		JogoEntity salvo = jogoRepository.save(entity);
		return toDto(salvo);
	}

	@Override
	@Transactional
	public JogoDto alterar(Integer id, JogoDto dto) {
		Optional<JogoEntity> optEntity = jogoRepository.findById(id);
		if (optEntity.isEmpty()) {
			throw new NaoEncontradoException("Jogo " + id + " não encontrado");
		}
		JogoEntity entity = optEntity.get();
		copiarParaEntity(dto, entity);
		JogoEntity salvo = jogoRepository.save(entity);
		return toDto(salvo);
	}

	@Override
	@Transactional
	public void excluir(Integer id) {
		Optional<JogoEntity> optEntity = jogoRepository.findById(id);
		if (optEntity.isEmpty()) {
			throw new NaoEncontradoException("Jogo " + id + " não encontrado");
		}
		jogoRepository.delete(optEntity.get());
	}
}