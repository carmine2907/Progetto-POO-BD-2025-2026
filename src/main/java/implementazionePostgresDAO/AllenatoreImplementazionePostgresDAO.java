package implementazionePostgresDAO;

import model.Allenatore;
import database.ConnessioneDatabase;
import dao.AllenatoreDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Allenatore implementazione postgres dao.
 */
public class AllenatoreImplementazionePostgresDAO implements AllenatoreDAO {

    private Connection connection;

    /**
     * Instantiates a new Allenatore implementazione postgres dao.
     */
    public AllenatoreImplementazionePostgresDAO() {
        try {
            connection = ConnessioneDatabase.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void salva(Allenatore allenatore) {
        // Inseriamo i dati solo nella tabella figlia 'allenatore'
        String sql = "INSERT INTO allenatore (id_allenatore, qualifica) VALUES (?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            // 1. L'ID è la chiave esterna che punta alla tabella utente (recuperato dal controller)
            ps.setInt(1, Integer.parseInt(allenatore.getIdUtente()));

            // 2. La qualifica specifica dell'allenatore (es. UEFA B)
            ps.setString(2, allenatore.getQualifica());

            ps.executeUpdate();
            System.out.println("Dati specifici Allenatore salvati correttamente nel database.");

        } catch (SQLException e) {
            System.err.println("ERRORE SALVATAGGIO ALLENATORE: " + e.getMessage());
            e.printStackTrace();
            // Rilanciamo l'errore per farlo gestire alla GUI
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Allenatore cercaPerId(int id) {
        String sql = """
                SELECT u.id_utente, u.login, u.password, u.nome, u.cognome, a.qualifica
                FROM utente u
                JOIN allenatore a ON u.id_utente = a.id_allenatore
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
                    String qualifica = rs.getString("qualifica");

                    Allenatore allenatore = new Allenatore(login, password, nome, cognome, qualifica);
                    allenatore.setIdUtente(String.valueOf(rs.getInt("id_utente")));

                    return allenatore;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Allenatore> trovaTutti() {
        List<Allenatore> lista = new ArrayList<>();
        String sql = """
                SELECT u.id_utente, u.login, u.password, u.nome, u.cognome, a.qualifica
                FROM utente u
                JOIN allenatore a ON u.id_utente = a.id_allenatore
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String login = rs.getString("login");
                String password = rs.getString("password");
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                String qualifica = rs.getString("qualifica");

                Allenatore allenatore = new Allenatore(login, password, nome, cognome, qualifica);
                allenatore.setIdUtente(String.valueOf(rs.getInt("id_utente")));

                lista.add(allenatore);
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
            int righeEliminate = ps.executeUpdate();
            if (righeEliminate > 0) {
                System.out.println("Allenatore eliminato con successo.");
            } else {
                System.out.println("Nessun allenatore trovato con l'ID specificato.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
