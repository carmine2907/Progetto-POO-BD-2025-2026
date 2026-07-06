package implementazionePostgresDAO;

import model.Utente;
import model.Atleta;
import model.Dirigente;
import model.Allenatore;
import database.ConnessioneDatabase;
import dao.UtenteDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * The type Utente implementazione postgres dao.
 */
public class UtenteImplementazionePostgresDao implements UtenteDAO {

    private Connection connection;

    /**
     * Instantiates a new Utente implementazione postgres dao.
     */
    public UtenteImplementazionePostgresDao() {
        try {
            connection = ConnessioneDatabase.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void salva(Utente utente) {
        String sql = "INSERT INTO utente (login, password, nome, cognome) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, utente.getLogin());
            ps.setString(2, utente.getPassword());
            ps.setString(3, utente.getNome());
            ps.setString(4, utente.getCognome());
            ps.executeUpdate();
            System.out.println("Utente registrato correttamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            // AGGIUNGI QUESTA RIGA per impedire gli "errori silenziosi"
            throw new RuntimeException("Errore nel Database: " + e.getMessage());
        }
    }

    @Override
    public Utente cercaPerUsername(String loginUsername) {
        String sql = "SELECT id_utente, login, password, nome, cognome FROM utente WHERE login = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, loginUsername);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int idUtente = rs.getInt("id_utente");
                    String login = rs.getString("login");
                    String password = rs.getString("password");
                    String nome = rs.getString("nome");
                    String cognome = rs.getString("cognome");

                    // 1. Controllo se l'ID corrisponde a un Dirigente
                    Dirigente dirigente = cercaDirigente(idUtente, login, password, nome, cognome);
                    if (dirigente != null) return dirigente;

                    // 2. Controllo se l'ID corrisponde a un Atleta
                    Atleta atleta = cercaAtleta(idUtente, login, password, nome, cognome);
                    if (atleta != null) return atleta;

                    // 3. Controllo se l'ID corrisponde a un Allenatore
                    Allenatore allenatore = cercaAllenatore(idUtente, login, password, nome, cognome);
                    if (allenatore != null) return allenatore;

                    // 4. Se non si trova in nessuna tabella figlia, restituisce un Utente base
                    Utente u = new Utente(login, password, nome, cognome);
                    u.setIdUtente(String.valueOf(idUtente));
                    return u;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // --- METODI PRIVATI DI APPOGGIO PER IL POLIMORFISMO ---

    private Dirigente cercaDirigente(int idUtente, String login, String password, String nome, String cognome) {
        String sql = "SELECT ruolo_organizzativo FROM dirigente WHERE id_dirigente = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idUtente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String ruolo = rs.getString("ruolo_organizzativo");
                    Dirigente d = new Dirigente(login, password, nome, cognome, ruolo);
                    d.setIdUtente(String.valueOf(idUtente));
                    return d;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Atleta cercaAtleta(int idUtente, String login, String password, String nome, String cognome) {
        String sql = "SELECT data_nascita, ruolo FROM atleta WHERE id_atleta = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idUtente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // MODIFICA QUESTA RIGA COSÌ:
                    String dataNascita = rs.getString("data_nascita");

                    String ruolo = rs.getString("ruolo");
                    Atleta a = new Atleta(login, password, nome, cognome, dataNascita, ruolo);
                    a.setIdUtente(String.valueOf(idUtente));
                    return a;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Allenatore cercaAllenatore(int idUtente, String login, String password, String nome, String cognome) {
        String sql = "SELECT qualifica FROM allenatore WHERE id_allenatore = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idUtente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String qualifica = rs.getString("qualifica");
                    Allenatore a = new Allenatore(login, password, nome, cognome, qualifica);
                    a.setIdUtente(String.valueOf(idUtente));
                    return a;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}