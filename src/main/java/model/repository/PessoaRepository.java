package model.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.entity.Pais;
import model.entity.Pessoa;
import model.entity.enums.TipoPessoa;

public class PessoaRepository implements BaseRepository<Pessoa> {

	@Override
	public Pessoa salvar(Pessoa novaPessoa) {
		String query = "INSERT INTO pessoa (nome, data_nascimento, sexo, cpf, id_pais, tipo_pessoa) VALUES (?, ?, ?, ?, ?)";

		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		try {
			preencherParametrosParaInsertOuUpdate(pstmt, novaPessoa);
			pstmt.execute();
			ResultSet resultado = pstmt.getGeneratedKeys();
			if (resultado.next()) {
				novaPessoa.setId(resultado.getInt(1));
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao salvar nova pessoa.");
			System.out.println("Erro: " + erro.getMessage());
			return null;
		} finally {
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return novaPessoa;
	}

	public boolean cpfExiste(String cpf) {
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = null;
		ResultSet resultado = null;

		try {
			String query = "SELECT COUNT(*) AS total FROM pessoa WHERE cpf = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, cpf);
			resultado = pstmt.executeQuery();
			if (resultado.next()) {
				int total = resultado.getInt("total");
				return total > 0;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao verificar a duplicidade do CPF: " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return false;
	}

	private void preencherParametrosParaInsertOuUpdate(PreparedStatement pstmt, Pessoa novaPessoa) throws SQLException {
		pstmt.setString(1, novaPessoa.getNome());
		pstmt.setDate(2, Date.valueOf(novaPessoa.getDataNascimento()));
		pstmt.setString(3, novaPessoa.getSexo());
		pstmt.setString(4, novaPessoa.getCpf());
		pstmt.setString(5, novaPessoa.getNacionalidade().toString());
		pstmt.setString(6, novaPessoa.getTipoPessoa().toString());
	}

	@Override
	public boolean excluir(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean excluiu = false;
		String query = "DELETE FROM pessoa WHERE id = " + id;
		try {
			if (stmt.executeUpdate(query) == 1) {
				excluiu = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao excluir pessoa.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return excluiu;
	}

	@Override
	public boolean alterar(Pessoa pessoa) {
		if (pessoa.getCpf() == null) {
			System.out.println("Erro: CPF não pode ser nulo.");
			return false;
		} else if (cpfExisteParaOutraPessoa(pessoa.getId(), pessoa.getCpf())) {
			System.out.println("Erro: CPF duplicado.");
			return false;
		}

		boolean alterou = false;
		String query = " UPDATE pessoa " + " SET nome=?, data_nascimento=?, " + " sexo=?, cpf=? " + " WHERE id=?";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);
		try {
			pstmt.setString(1, pessoa.getNome());
			pstmt.setDate(2, Date.valueOf(pessoa.getDataNascimento()));
			pstmt.setString(3, pessoa.getSexo());
			pstmt.setString(4, pessoa.getCpf());
			pstmt.setInt(5, pessoa.getId());

			alterou = pstmt.executeUpdate() > 0;
		} catch (SQLException erro) {
			System.out.println("Erro ao atualizar pessoa.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return alterou;
	}

	private boolean cpfExisteParaOutraPessoa(int pessoaId, String cpf) {
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = null;
		ResultSet resultado = null;

		try {
			String query = "SELECT COUNT(*) AS total FROM pessoa WHERE cpf = ? AND id != ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, cpf);
			pstmt.setInt(2, pessoaId);
			resultado = pstmt.executeQuery();
			if (resultado.next()) {
				int total = resultado.getInt("total");
				return total > 0;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao verificar duplicidade do CPF: " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return false;
	}

	@Override
	public Pessoa consultarPorId(int id) {
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = null;
		ResultSet resultado = null;
		Pessoa pessoa = new Pessoa();

		String query = "SELECT * FROM pessoa WHERE id = ?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			resultado = pstmt.executeQuery();

			if (resultado.next()) {
				pessoa = new Pessoa();
				pessoa.setId(resultado.getInt("id"));
				pessoa.setNome(resultado.getString("nome"));
				pessoa.setDataNascimento(resultado.getDate("data_nascimento").toLocalDate());
				pessoa.setSexo(resultado.getString("sexo"));
				pessoa.setCpf(resultado.getString("cpf"));

				// Criar uma instância de PaisRepository já que o método não é static
				PaisRepository paisRepository = new PaisRepository();

				// Consultar o pais pelo ID
				int idPais = resultado.getInt("id_pais");
				Pais pais = paisRepository.consultarPorId(idPais);
				pessoa.setNacionalidade(pais);

				pessoa.setTipoPessoa(TipoPessoa.valueOf(resultado.getString("tipo_pessoa")));
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao consultar pessoa por id (" + id + ").");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return pessoa;
	}

	@Override
	public ArrayList<Pessoa> consultarTodos() {
		ArrayList<Pessoa> pessoas = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;

		String query = "SELECT * FROM pessoa";

		try {
			resultado = stmt.executeQuery(query);

			while (resultado.next()) {
				Pessoa pessoa = new Pessoa();
				pessoa.setId(resultado.getInt("id"));
				pessoa.setNome(resultado.getString("nome"));
				pessoa.setDataNascimento(resultado.getDate("data_nascimento").toLocalDate());
				pessoa.setSexo(resultado.getString("sexo"));
				pessoa.setCpf(resultado.getString("cpf"));

				// Criar uma instância de PaisRepository já que o método não é static
				PaisRepository paisRepository = new PaisRepository();

				// Consultar o pais pelo ID
				int idPais = resultado.getInt("id_pais");
				Pais pais = paisRepository.consultarPorId(idPais);
				pessoa.setNacionalidade(pais);

				pessoa.setTipoPessoa(TipoPessoa.valueOf(resultado.getString("tipo_pessoa")));

				pessoas.add(pessoa);
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao consultar todas as aplicações.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return pessoas;
	}
}