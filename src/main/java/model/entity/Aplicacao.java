package model.entity;

import java.time.LocalDate;
import model.entity.enums.Avaliacao;

public class Aplicacao {

	private int id;
	private int idPessoa;
	private Vacina vacina;
	private LocalDate data;
	private Avaliacao avaliacao;

	public Aplicacao() {
		super();
	}

	public Aplicacao(int id, int idPessoa, Vacina vacina, LocalDate data, Avaliacao avaliacao) {
		super();
		this.id = id;
		this.idPessoa = idPessoa;
		this.vacina = vacina;
		this.data = data;
		this.avaliacao = avaliacao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(int idPessoa) {
		this.idPessoa = idPessoa;
	}

	public Vacina getVacina() {
		return vacina;
	}

	public void setVacina(Vacina vacina) {
		this.vacina = vacina;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Avaliacao getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}

}
