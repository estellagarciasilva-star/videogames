package br.senac.tads.dsw.videogames;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// DTO: objeto que trafega em JSON entre o navegador e a API. As validações ficam aqui.
public class JogoDto {

	private Integer id;

	@NotBlank(message = "O título é obrigatório")
	@Size(max = 100, message = "O título pode ter no máximo 100 caracteres")
	private String titulo;

	@NotNull(message = "O ano é obrigatório")
	@Min(value = 1950, message = "O ano deve ser 1950 ou maior")
	private Integer ano;

	@NotBlank(message = "A plataforma é obrigatória")
	@Size(max = 50, message = "A plataforma pode ter no máximo 50 caracteres")
	private String plataforma;

	@NotNull(message = "Informe se o jogo está disponível")
	private Boolean disponivel;

	@Size(max = 500, message = "As observações podem ter no máximo 500 caracteres")
	private String observacoes;

	public JogoDto() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public String getPlataforma() {
		return plataforma;
	}

	public void setPlataforma(String plataforma) {
		this.plataforma = plataforma;
	}

	public Boolean getDisponivel() {
		return disponivel;
	}

	public void setDisponivel(Boolean disponivel) {
		this.disponivel = disponivel;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
}