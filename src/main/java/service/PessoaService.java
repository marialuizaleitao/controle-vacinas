package service;

import java.util.List;

import exception.ControleVacinasException;
import model.entity.Pessoa;
import model.repository.PessoaRepository;

public class PessoaService {

	private PessoaRepository repository = new PessoaRepository();

	public Pessoa salvar(Pessoa novaPessoa) throws ControleVacinasException {
		validarCamposObrigatorios(novaPessoa);
		validarCpf(novaPessoa);
		return repository.salvar(novaPessoa);
	}

	private void validarCpf(Pessoa novaPessoa) throws ControleVacinasException {
		if (repository.cpfExiste(novaPessoa.getCpf())) {
			throw new ControleVacinasException("CPF " + novaPessoa.getCpf() + " já cadastrado.");
		}
	}

	private void validarCamposObrigatorios(Pessoa p) throws ControleVacinasException {
		String mensagemValidacao = "";
		if (p.getNome() == null || p.getNome().isEmpty()) {
			mensagemValidacao += " - informe o nome \n";
		}
		if (p.getDataNascimento() == null) {
			mensagemValidacao += " - informe a data de nascimento \n";
		}
		if (p.getCpf() == null || p.getCpf().isEmpty() || p.getCpf().length() != 11) {
			mensagemValidacao += " - informe o CPF";
		}
		if (p.getSexo() == " ") {
			mensagemValidacao += " - informe o sexo";
		}
		if (p.getTipoPessoa().toString() != "PESQUISADOR" || p.getTipoPessoa().toString() != "VOLUNTARIO"
				|| p.getTipoPessoa().toString() != "PUBLICO_GERAL") {
			mensagemValidacao += " - informe o tipo (entre 1 - PESQUISADOR, 2 - VOLUNTARIO e 3 - PUBLICO_GERAL)";
		}

		if (!mensagemValidacao.isEmpty()) {
			throw new ControleVacinasException("Preencha o(s) seguinte(s) campo(s) \n " + mensagemValidacao);
		}

	}

	public boolean atualizar(Pessoa pessoaEditada) throws ControleVacinasException {
		if (repository.cpfExiste(pessoaEditada.getCpf())) {
			throw new ControleVacinasException("CPF " + pessoaEditada.getCpf() + " já cadastrado.");
		}
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
