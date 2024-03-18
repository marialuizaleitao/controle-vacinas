package service;

import java.time.LocalDate;
import java.util.List;
import model.entity.Aplicacao;
import model.entity.enums.Avaliacao;
import model.repository.AplicacaoRepository;

public class AplicacaoService {

	private AplicacaoRepository repository = new AplicacaoRepository();

	public Aplicacao salvar(Aplicacao novaAplicacao) {
		return repository.salvar(novaAplicacao);
	}

	public boolean atualizar(Aplicacao aplicacaoEditada) {
		return repository.alterar(aplicacaoEditada);
	}

	public boolean excluir(int id) {
		return repository.excluir(id);
	}

	public Aplicacao consultarPorId(int id) {
		return repository.consultarPorId(id);
	}

	public List<Aplicacao> consultarTodas() {
		return repository.consultarTodos();
	}

	public boolean aplicarVacina(int idPessoa, int idVacina, LocalDate data, Avaliacao avaliacao) {
		return repository.aplicarVacina(idPessoa, idVacina, data, avaliacao);
	}
}
