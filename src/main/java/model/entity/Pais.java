package model.entity;

public class Pais {

	private int idPais;
	private String nome;
	private String sigla;

	public Pais() {
		super();
	}

	public Pais(int idPais, String nome, String sigla) {
		super();
		this.idPais = idPais;
		this.nome = nome;
		this.sigla = sigla;
	}

	public int getIdPais() {
		return idPais;
	}

	public void setIdPais(int idPais) {
		this.idPais = idPais;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

}
