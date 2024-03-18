package model.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import model.entity.Aplicacao;
import model.entity.Vacina;
import model.entity.enums.Avaliacao;

public class AplicacaoRepository implements BaseRepository<Aplicacao> {

	@Override
	public Aplicacao salvar(Aplicacao novaAplicacao) {

		String query = "INSERT INTO aplicacao (id_pessoa, id_vacina, data, avaliacao) VALUES (?, ?, ?, ?)";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		try {
			preencherParametrosParaInsertOuUpdate(pstmt, novaAplicacao);
			pstmt.execute();
			ResultSet resultado = pstmt.getGeneratedKeys();
			if (resultado.next()) {
				novaAplicacao.setId(resultado.getInt(1));
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao salvar nova vacina.");
			System.out.println("Erro: " + erro.getMessage());
			return null;
		} finally {
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return novaAplicacao;
	}

	private void preencherParametrosParaInsertOuUpdate(PreparedStatement pstmt, Aplicacao novaAplicacao)
			throws SQLException {
		pstmt.setInt(1, novaAplicacao.getIdPessoa());
		pstmt.setInt(2, novaAplicacao.getVacina().getId());
		pstmt.setDate(3, Date.valueOf(novaAplicacao.getData()));
		pstmt.setString(4, novaAplicacao.getAvaliacao().toString());
	}

	@Override
	public boolean excluir(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean excluiu = false;
		String query = "DELETE FROM aplicacao WHERE id = ?";
		try {
			if (stmt.executeUpdate(query) == 1) {
				excluiu = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao excluir aplicação.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return excluiu;
	}

	@Override
	public boolean alterar(Aplicacao aplicacaoEditada) {
		boolean alterou = false;
		String query = "UPDATE aplicacao SET id_pessoa=?, id_vacina=?, data=?, avaliacao=? WHERE id=?";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, aplicacaoEditada.getIdPessoa());
			pstmt.setInt(2, aplicacaoEditada.getVacina().getId());
			pstmt.setDate(3, Date.valueOf(aplicacaoEditada.getData()));
			pstmt.setString(4, aplicacaoEditada.getAvaliacao().toString());
			pstmt.setInt(5, aplicacaoEditada.getId());

			alterou = pstmt.executeUpdate() > 0;
		} catch (SQLException erro) {
			System.out.println("Erro ao atualizar aplicação.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return alterou;
	}

	@Override
	public Aplicacao consultarPorId(int id) {
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = null;
		ResultSet resultado = null;
		Aplicacao aplicacao = null;

		String query = "SELECT * FROM aplicacao WHERE id = ?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			resultado = pstmt.executeQuery();

			if (resultado.next()) {
				aplicacao = new Aplicacao();
				aplicacao.setId(resultado.getInt("id"));
				aplicacao.setIdPessoa(resultado.getInt("id_pessoa"));

				// Criar uma instância de VacinaRepository já que o método não é static
				VacinaRepository vacinaRepository = new VacinaRepository();

				// Consultar a vacina pelo ID
				int idVacina = resultado.getInt("id_vacina");
				Vacina vacina = vacinaRepository.consultarPorId(idVacina);
				aplicacao.setVacina(vacina);
				aplicacao.setData(resultado.getDate("data").toLocalDate());
				aplicacao.setAvaliacao(Avaliacao.valueOf(resultado.getString("avaliacao")));
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao consultar aplicação por id (" + id + ").");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return aplicacao;
	}

	@Override
	public ArrayList<Aplicacao> consultarTodos() {
		ArrayList<Aplicacao> aplicacoes = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;

		String query = "SELECT * FROM aplicacao";

		try {
			resultado = stmt.executeQuery(query);

			while (resultado.next()) {
				Aplicacao aplicacao = new Aplicacao();
				aplicacao.setId(resultado.getInt("id"));
				aplicacao.setIdPessoa(resultado.getInt("id_pessoa"));

				VacinaRepository vacinaRepository = new VacinaRepository();

				int idVacina = resultado.getInt("id_vacina");
				Vacina vacina = vacinaRepository.consultarPorId(idVacina);
				aplicacao.setVacina(vacina);

				aplicacao.setData(resultado.getDate("data").toLocalDate());
				aplicacao.setAvaliacao(Avaliacao.valueOf(resultado.getString("avaliacao")));

				aplicacoes.add(aplicacao);
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao consultar todas as aplicações.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return aplicacoes;
	}
	
	public boolean aplicarVacina(int idPessoa, int idVacina, LocalDate data, Avaliacao avaliacao) {
	    Connection conn = Banco.getConnection();
	    PreparedStatement pstmt = null;

	    String query = "INSERT INTO aplicacao (id_pessoa, id_vacina, data, avaliacao) VALUES (?, ?, ?, ?)";

	    try {
	        pstmt = conn.prepareStatement(query);
	        pstmt.setInt(1, idPessoa);
	        pstmt.setInt(2, idVacina);
	        pstmt.setDate(3, Date.valueOf(data));
	        pstmt.setString(4, avaliacao.toString());

	        int rowsAffected = pstmt.executeUpdate();

	        if (rowsAffected > 0) {
	            return true;
	        } else {
	            System.out.println("Nenhuma linha afetada ao aplicar vacina.");
	            return false;
	        }
	    } catch (SQLException erro) {
	        System.out.println("Erro ao aplicar vacina.");
	        System.out.println("Erro: " + erro.getMessage());
	        return false;
	    } finally {
	        Banco.closePreparedStatement(pstmt);
	        Banco.closeConnection(conn);
	    }
	}

}
