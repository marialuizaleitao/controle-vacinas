package model.entity;

import java.time.LocalDate;

import model.entity.enums.EstagioPesquisa;

public class Vacina {

	private int id;
	private String nome;
	private Pessoa pesquisadorResponsavel;
	private Pais pais;
	private EstagioPesquisa estagio;
	private LocalDate dataInicioPesquisa;

	public Vacina() {
		super();
	}

	public Vacina(int id, String nome, Pessoa pesquisadorResponsavel, Pais pais, EstagioPesquisa estagio,
			LocalDate dataInicioPesquisa) {
		super();
		this.id = id;
		this.nome = nome;
		this.pesquisadorResponsavel = pesquisadorResponsavel;
		this.pais = pais;
		this.estagio = estagio;
		this.dataInicioPesquisa = dataInicioPesquisa;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Pessoa getPesquisadorResponsavel() {
		return pesquisadorResponsavel;
	}

	public void setPesquisadorResponsavel(Pessoa pesquisadorResponsavel) {
		this.pesquisadorResponsavel = pesquisadorResponsavel;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public EstagioPesquisa getEstagio() {
		return estagio;
	}

	public void setEstagio(EstagioPesquisa estagio) {
		this.estagio = estagio;
	}

	public LocalDate getDataInicioPesquisa() {
		return dataInicioPesquisa;
	}

	public void setDataInicioPesquisa(LocalDate dataInicioPesquisa) {
		this.dataInicioPesquisa = dataInicioPesquisa;
	}

}
