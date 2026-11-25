package br.senac.tads.dsw.videogames;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/jogos")
public class JogoController {

	private final JogoService jogoService;

	public JogoController(JogoService jogoService) {
		this.jogoService = jogoService;
	}

	@GetMapping
	public List<JogoDto> listar() {
		return jogoService.listar();
	}

	@GetMapping("/{id}")
	public JogoDto obter(@PathVariable("id") Integer id) {
		Optional<JogoDto> optJogo = jogoService.obter(id);
		if (optJogo.isEmpty()) {
			throw new NaoEncontradoException("Jogo " + id + " não encontrado");
		}
		return optJogo.get();
	}

	// @Valid aplica as validações do JogoDto antes de executar o método
	@PostMapping
	public ResponseEntity<JogoDto> incluir(@RequestBody @Valid JogoDto jogo) {
		JogoDto salvo = jogoService.incluir(jogo);
		return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
	}

	@PutMapping("/{id}")
	public ResponseEntity<JogoDto> alterar(@PathVariable("id") Integer id,
			@RequestBody @Valid JogoDto jogo) {
		JogoDto alterado = jogoService.alterar(id, jogo);
		return ResponseEntity.ok(alterado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable("id") Integer id) {
		jogoService.excluir(id);
		return ResponseEntity.noContent().build();
	}

	@ExceptionHandler(NaoEncontradoException.class)
	public ResponseEntity<ProblemDetail> tratarNaoEncontrado(NaoEncontradoException ex) {
		ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
		return ResponseEntity.of(pd).build();
	}

	// Quando a validação falha, devolve HTTP 400 com { "campo": "mensagem" }
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> tratarValidacao(MethodArgumentNotValidException ex) {
		Map<String, String> erros = new HashMap<>();
		for (FieldError erro : ex.getBindingResult().getFieldErrors()) {
			erros.put(erro.getField(), erro.getDefaultMessage());
		}
		return ResponseEntity.badRequest().body(erros);
	}
}