package implementazionePostgresDAO;

import model.Dirigente;
import database.ConnessioneDatabase;
import dao.DirigenteDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Dirigente implementazione postgres dao.
 */
public class DirigenteImplementazionePostgresDAO implements DirigenteDAO {

    private Connection connection;

    /**
     * Instantiates a new Dirigente implementazione postgres dao.
     */
    public DirigenteImplementazionePostgresDAO() {
        try {
            connection = ConnessioneDatabase.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void salva(Dirigente dirigente) {
        // Inseriamo i dati solo nella tabella figlia 'dirigente'
        String sql = "INSERT INTO dirigente (id_dirigente, ruolo_organizzativo) VALUES (?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            // 1. L'ID è la chiave esterna che punta alla tabella utente
            ps.setInt(1, Integer.parseInt(dirigente.getIdUtente()));

            // 2. Il ruolo dirigenziale (es. Presidente)
            ps.setString(2, dirigente.getRuoloOrganizzativo());

            ps.executeUpdate();
            System.out.println("Dati specifici Dirigente salvati correttamente nel database.");

        } catch (SQLException e) {
            System.err.println("ERRORE SALVATAGGIO DIRIGENTE: " + e.getMessage());
            e.printStackTrace();
            // Rilanciamo l'errore per farlo gestire alla GUI
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Dirigente cercaPerId(int id) {
        String sql = """
                SELECT u.id_utente, u.login, u.password, u.nome, u.cognome, d.ruolo_organizzativo
                FROM utente u
                JOIN dirigente d ON u.id_utente = d.id_dirigente
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
                    String ruoloOrganizzativo = rs.getString("ruolo_organizzativo");

                    Dirigente dirigente = new Dirigente(login, password, nome, cognome, ruoloOrganizzativo);
                    dirigente.setIdUtente(String.valueOf(rs.getInt("id_utente")));

                    return dirigente;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Dirigente> trovaTutti() {
        List<Dirigente> lista = new ArrayList<>();
        String sql = """
                SELECT u.id_utente, u.login, u.password, u.nome, u.cognome, d.ruolo_organizzativo
                FROM utente u
                JOIN dirigente d ON u.id_utente = d.id_dirigente
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String login = rs.getString("login");
                String password = rs.getString("password");
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                String ruoloOrganizzativo = rs.getString("ruolo_organizzativo");

                Dirigente dirigente = new Dirigente(login, password, nome, cognome, ruoloOrganizzativo);
                dirigente.setIdUtente(String.valueOf(rs.getInt("id_utente")));

                lista.add(dirigente);
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
                System.out.println("Dirigente eliminato.");
            } else {
                System.out.println("Nessun dirigente trovato con questo ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}