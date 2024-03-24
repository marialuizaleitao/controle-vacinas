package model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.entity.Pais;

public class PaisRepository implements BaseRepository<Pais> {

	@Override
	public Pais salvar(Pais novoPais) {
		String query = "INSERT INTO pais (nome, sigla) VALUES (?, ?)";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		try {
			preencherParametrosParaInsertOuUpdate(pstmt, novoPais);
			pstmt.execute();
			ResultSet resultado = pstmt.getGeneratedKeys();
			if (resultado.next()) {
				novoPais.setIdPais(resultado.getInt(1));
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao salvar novo pais.");
			System.out.println("Erro: " + erro.getMessage());
			return null;
		} finally {
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return novoPais;
	}

	private void preencherParametrosParaInsertOuUpdate(PreparedStatement pstmt, Pais novoPais) throws SQLException {
		pstmt.setString(1, novoPais.getNome());
		pstmt.setString(2, novoPais.getSigla());
	}

	@Override
	public boolean excluir(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean excluiu = false;
		String query = "DELETE FROM pais WHERE id = " + id;
		try {
			if (stmt.executeUpdate(query) == 1) {
				excluiu = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao excluir pais.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return excluiu;
	}

	@Override
	public boolean alterar(Pais pais) {
		boolean alterou = false;
		String query = " UPDATE pais " + " SET nome=?, sigla=?, " + " WHERE id=?";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);
		try {
			preencherParametrosParaInsertOuUpdate(pstmt, pais);
			alterou = pstmt.executeUpdate() > 0;
		} catch (SQLException erro) {
			System.out.println("Erro ao atualizar pais.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return alterou;
	}

	@Override
	public Pais consultarPorId(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);

		ResultSet resultado = null;
		Pais pais = new Pais();
		String query = " SELECT * FROM pais WHERE id = " + id;

		try {
			resultado = stmt.executeQuery(query);
			if (resultado.next()) {
				pais.setIdPais(Integer.parseInt(resultado.getString("id")));
				pais.setNome(resultado.getString("nome"));
				pais.setSigla(resultado.getString("sigla"));
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar consultar pais com id (" + id + ").");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return pais;
	}

	@Override
	public ArrayList<Pais> consultarTodos() {
		ArrayList<Pais> paises = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);

		ResultSet resultado = null;
		String query = " SELECT * FROM pais";

		try {
			resultado = stmt.executeQuery(query);
			while (resultado.next()) {
				Pais pais = new Pais();

				pais.setIdPais(Integer.parseInt(resultado.getString("id")));
				pais.setNome(resultado.getString("nome"));
				pais.setSigla(resultado.getString("sigla"));
				paises.add(pais);
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar consultar todos os paises.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return paises;
	}

}
