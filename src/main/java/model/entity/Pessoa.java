package model.entity;

import java.time.LocalDate;
import java.util.List;

import model.entity.enums.TipoPessoa;

public class Pessoa {

	private int id;
	private String nome;
	private LocalDate dataNascimento;
	private String sexo;
	private String cpf;
	private Pais nacionalidade;
	private TipoPessoa tipoPessoa;
	private List<Aplicacao> vacinacoes;

	public Pessoa() {
	}

	public Pessoa(int id, String nome, LocalDate dataNascimento, String sexo, String cpf, Pais nacionalidade,
			TipoPessoa tipoPessoa, List<Aplicacao> vacinacoes) {
		super();
		this.id = id;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.sexo = sexo;
		this.cpf = cpf;
		this.nacionalidade = nacionalidade;
		this.tipoPessoa = tipoPessoa;
		this.vacinacoes = vacinacoes;
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

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Pais getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(Pais nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public List<Aplicacao> getVacinacoes() {
		return vacinacoes;
	}

	public void setVacinacoes(List<Aplicacao> vacinacoes) {
		this.vacinacoes = vacinacoes;
	}

}
