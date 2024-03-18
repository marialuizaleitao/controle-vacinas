package model.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.entity.Pessoa;
import model.entity.Vacina;
import model.entity.enums.EstagioPesquisa;
import model.entity.enums.TipoPessoa;

public class VacinaRepository implements BaseRepository<Vacina> {

	@Override
	public Vacina salvar(Vacina novaVacina) {
		Pessoa responsavel = novaVacina.getPesquisadorResponsavel();
		if (responsavel == null || responsavel.getTipoPessoa() != TipoPessoa.PESQUISADOR) {
			System.out.println("Erro: A pessoa responsável deve ser um Pesquisador.");
			return null;
		}

		String query = "INSERT INTO vacina (nome, pais_origem, id_pesquisador, estagio, data_inicio_pesquisa) VALUES (?, ?, ?, ?, ?)";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		try {
			preencherParametrosParaInsertOuUpdate(pstmt, novaVacina);
			pstmt.execute();
			ResultSet resultado = pstmt.getGeneratedKeys();
			if (resultado.next()) {
				novaVacina.setId(resultado.getInt(1));
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao salvar nova vacina.");
			System.out.println("Erro: " + erro.getMessage());
			return null;
		} finally {
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return novaVacina;
	}

	private void preencherParametrosParaInsertOuUpdate(PreparedStatement pstmt, Vacina novaVacina) throws SQLException {
		pstmt.setString(1, novaVacina.getNome());
		pstmt.setString(2, novaVacina.getPaisOrigem());
		pstmt.setInt(3, novaVacina.getPesquisadorResponsavel().getId());
		pstmt.setString(4, novaVacina.getEstagio().toString());
		pstmt.setDate(5, Date.valueOf(novaVacina.getDataInicioPesquisa()));
	}

	@Override
	public boolean excluir(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean excluiu = false;
		String query = "DELETE FROM vacina WHERE id = " + id;
		try {
			if (stmt.executeUpdate(query) == 1) {
				excluiu = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao excluir vacina.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return excluiu;
	}

	@Override
	public boolean alterar(Vacina novaVacina) {
		Pessoa responsavel = novaVacina.getPesquisadorResponsavel();
		if (responsavel == null || responsavel.getTipoPessoa() != TipoPessoa.PESQUISADOR) {
			System.out.println("Erro: A pessoa responsável deve ser um Pesquisador.");
			return false;
		}

		boolean alterou = false;
		String query = " UPDATE vacina " + " SET nome=?, pais_origem=?, "
				+ " id_pesquisador=?, estagio=?, data_inicio_pesquisa=? " + " WHERE id=?";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);
		try {
			pstmt.setString(1, novaVacina.getNome());
			pstmt.setString(2, novaVacina.getPaisOrigem());
			pstmt.setInt(3, novaVacina.getPesquisadorResponsavel().getId());
			pstmt.setString(4, novaVacina.getEstagio().toString());
			pstmt.setDate(5, Date.valueOf(novaVacina.getDataInicioPesquisa()));
			pstmt.setInt(6, novaVacina.getId());

			alterou = pstmt.executeUpdate() > 0;
		} catch (SQLException erro) {
			System.out.println("Erro ao atualizar vacina.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return alterou;
	}

	@Override
	public Vacina consultarPorId(int id) {
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = null;
		ResultSet resultado = null;
		Vacina vacina = null;

		String query = "SELECT * FROM vacina WHERE id = ?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			resultado = pstmt.executeQuery();

			if (resultado.next()) {
				vacina = new Vacina();
				vacina.setId(resultado.getInt("id"));
				vacina.setNome(resultado.getString("nome"));
				vacina.setPaisOrigem(resultado.getString("pais_origem"));

				// Criar uma instância de PessoaRepository já que o método não é static
				PessoaRepository pessoaRepository = new PessoaRepository();

				// Consultar o pesquisador responsável pelo ID na tabela pessoa
				int idPesquisador = resultado.getInt("id_pesquisador");
				Pessoa pesquisador = pessoaRepository.consultarPorId(idPesquisador);
				vacina.setPesquisadorResponsavel(pesquisador);

				vacina.setEstagio(EstagioPesquisa.valueOf(resultado.getString("estagio")));
				vacina.setDataInicioPesquisa(resultado.getDate("data_inicio_pesquisa").toLocalDate());
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao consultar vacina por id (" + id + ").");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return vacina;
	}

	@Override
	public ArrayList<Vacina> consultarTodos() {
		ArrayList<Vacina> vacinas = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;

		String query = "SELECT * FROM vacina";

		try {
			resultado = stmt.executeQuery(query);

			while (resultado.next()) {
				Vacina vacina = new Vacina();
				vacina.setId(resultado.getInt("id"));
				vacina.setNome(resultado.getString("nome"));
				vacina.setPaisOrigem(resultado.getString("pais_origem"));

				PessoaRepository pessoaRepository = new PessoaRepository();

				int idPesquisador = resultado.getInt("id_pesquisador");
				Pessoa pesquisador = pessoaRepository.consultarPorId(idPesquisador);
				vacina.setPesquisadorResponsavel(pesquisador);

				vacina.setEstagio(EstagioPesquisa.valueOf(resultado.getString("estagio")));
				vacina.setDataInicioPesquisa(resultado.getDate("data_inicio_pesquisa").toLocalDate());

				vacinas.add(vacina);
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao consultar todas as vacinas.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return vacinas;
	}

}