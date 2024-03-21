package service;

import java.util.List;

import model.entity.Pais;
import model.repository.PaisRepository;

public class PaisService {

	private PaisRepository repository = new PaisRepository();

	public Pais salvar(Pais novaPais) {
		return repository.salvar(novaPais);
	}

	public boolean atualizar(Pais pessoaEditada) {
		return repository.alterar(pessoaEditada);
	}

	public boolean excluir(int id) {
		return repository.excluir(id);
	}

	public Pais consultarPorId(int id) {
		return repository.consultarPorId(id);
	}

	public List<Pais> consultarTodos() {
		return repository.consultarTodos();
	}
}
