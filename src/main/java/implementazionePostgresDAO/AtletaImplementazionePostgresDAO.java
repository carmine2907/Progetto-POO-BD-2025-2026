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
		// CORREZIONE: Inseriamo SOLO nella tabella 'atleta', perché nella tabella 'utente' c'è già!
		// (Assicurati che i nomi delle colonne corrispondano al tuo script SQL)
		String sql = "INSERT INTO atleta (id_atleta, data_nascita, ruolo, pagamento_in_reg) VALUES (?, ?, ?, ?)";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			// 1. L'ID è quello che il Controller ha appena recuperato dalla tabella utente
			ps.setInt(1, Integer.parseInt(atleta.getIdUtente()));

			// 2. Data di nascita (conversione della stringa YYYY-MM-DD in data SQL)
			ps.setDate(2, java.sql.Date.valueOf(atleta.getDataNascita()));

			// 3. Ruolo
			ps.setString(3, atleta.getRuolo());

			// 4. Stato del pagamento
			ps.setBoolean(4, atleta.isPagamentoInRegola());

			ps.executeUpdate();
			System.out.println("Dati specifici Atleta salvati correttamente nel database.");

		} catch (SQLException e) {
			System.err.println("ERRORE SALVATAGGIO ATLETA: " + e.getMessage());
			e.printStackTrace();
			// Rilanciamo l'errore per farlo arrivare alla GUI in caso di problemi
			throw new RuntimeException(e.getMessage());
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