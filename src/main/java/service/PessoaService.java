package service;

import java.util.List;

import model.entity.Pessoa;
import model.repository.PessoaRepository;

public class PessoaService {

	private PessoaRepository repository = new PessoaRepository();

	public Pessoa salvar(Pessoa novaPessoa) {
		return repository.salvar(novaPessoa);
	}

	public boolean atualizar(Pessoa pessoaEditada) {
		return repository.alterar(pessoaEditada);
	}

	public boolean excluir(int id) {
		return repository.excluir(id);
	}

	public Pessoa consultarPorId(int id) {
		return repository.consultarPorId(id);
	}

	public List<Pessoa> consultarTodas() {
		return repository.consultarTodos();
	}
}
