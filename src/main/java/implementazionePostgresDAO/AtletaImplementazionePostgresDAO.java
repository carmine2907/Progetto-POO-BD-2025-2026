package implementazionePostgresDAO;

import model.Atleta;
import database.ConnessioneDatabase;
import dao.AtletaDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AtletaImplementazionePostgresDAO implements AtletaDAO {

	private Connection connection;

	public AtletaImplementazionePostgresDAO() {
		try {
			connection = ConnessioneDatabase.getInstance().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void salva(Atleta atleta) {
		String sqlUtente = "INSERT INTO utente (login, password, nome, cognome) VALUES (?, ?, ?, ?)";
		String sqlAtleta = "INSERT INTO atleta (id_atleta, data_nascita, ruolo, pagamento_in_reg) VALUES (?, ?, ?, ?)";

		PreparedStatement psUtente = null;
		PreparedStatement psAtleta = null;

		try {
			connection.setAutoCommit(false);

			// 1. Inserimento in utente
			psUtente = connection.prepareStatement(sqlUtente, Statement.RETURN_GENERATED_KEYS);
			psUtente.setString(1, atleta.getLogin());
			psUtente.setString(2, atleta.getPassword());
			psUtente.setString(3, atleta.getNome());
			psUtente.setString(4, atleta.getCognome());
			psUtente.executeUpdate();

			int idGenerato = -1;
			try (ResultSet rsKeys = psUtente.getGeneratedKeys()) {
				if (rsKeys.next()) {
					idGenerato = rsKeys.getInt(1);
					atleta.setIdUtente(String.valueOf(idGenerato));
				}
			}

			// 2. Inserimento in atleta
			psAtleta = connection.prepareStatement(sqlAtleta);
			psAtleta.setInt(1, idGenerato);

			// Trattato come STRINGA (VARCHAR)
			psAtleta.setString(2, atleta.getDataNascita());

			psAtleta.setString(3, atleta.getRuolo());
			psAtleta.setBoolean(4, atleta.isPagamentoInRegola());
			psAtleta.executeUpdate();

			connection.commit();
			System.out.println("Atleta salvato con successo. ID: " + idGenerato);

		} catch (SQLException e) {
			try {
				if (connection != null) connection.rollback();
			} catch (SQLException rollbackEx) {
				rollbackEx.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				if (psUtente != null) psUtente.close();
				if (psAtleta != null) psAtleta.close();
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Atleta cercaPerId(int id) {
		String sql = """
                SELECT u.id_utente, u.login, u.password, u.nome, u.cognome, a.data_nascita, a.ruolo, a.pagamento_in_reg
                FROM utente u
                JOIN atleta a ON u.id_utente = a.id_atleta
                WHERE u.id_utente = ?
                """;

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					String login = rs.getString("login");
					String password = rs.getString("password");
					String nome = rs.getString("nome");
					String cognome = rs.getString("cognome");

					// Estratto come STRINGA dal database
					String dataNascita = rs.getString("data_nascita");

					String ruolo = rs.getString("ruolo");
					boolean pagRegola = rs.getBoolean("pagamento_in_reg");

					Atleta atleta = new Atleta(login, password, nome, cognome, dataNascita, ruolo);
					atleta.setIdUtente(String.valueOf(rs.getInt("id_utente")));
					atleta.setPagamentoInRegola(pagRegola);

					return atleta;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Atleta> trovaTutti() {
		List<Atleta> lista = new ArrayList<>();
		String sql = """
                SELECT u.id_utente, u.login, u.password, u.nome, u.cognome, a.data_nascita, a.ruolo, a.pagamento_in_reg
                FROM utente u
                JOIN atleta a ON u.id_utente = a.id_atleta
                """;

		try (PreparedStatement ps = connection.prepareStatement(sql);
		     ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				String login = rs.getString("login");
				String password = rs.getString("password");
				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");

				// Estratto come STRINGA dal database
				String dataNascita = rs.getString("data_nascita");

				String ruolo = rs.getString("ruolo");
				boolean pagRegola = rs.getBoolean("pagamento_in_reg");

				Atleta atleta = new Atleta(login, password, nome, cognome, dataNascita, ruolo);
				atleta.setIdUtente(String.valueOf(rs.getInt("id_utente")));
				atleta.setPagamentoInRegola(pagRegola);

				lista.add(atleta);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public void elimina(int id) {
		String sql = "DELETE FROM utente WHERE id_utente = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, id);
			ps.executeUpdate();
			System.out.println("Atleta eliminato.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	@Override
	public List<Atleta> getAtletiPerSquadra(int idSquadra) {
		List<Atleta> lista = new ArrayList<>();

		// Uniamo utente, atleta e la tabella ponte iscrizione!
		String sql = """
                SELECT u.id_utente, u.login, u.password, u.nome, u.cognome, 
                       a.data_nascita, a.ruolo, a.pagamento_in_reg
                FROM utente u
                JOIN atleta a ON u.id_utente = a.id_atleta
                JOIN iscrizione i ON a.id_atleta = i.id_atleta
                WHERE i.id_squadra = ?
                """;

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, idSquadra);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Atleta atleta = new Atleta(
							rs.getString("login"),
							rs.getString("password"),
							rs.getString("nome"),
							rs.getString("cognome"),
							rs.getString("data_nascita"),
							rs.getString("ruolo")
					);
					atleta.setIdUtente(String.valueOf(rs.getInt("id_utente")));
					atleta.setPagamentoInRegola(rs.getBoolean("pagamento_in_reg"));

					lista.add(atleta);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}
}