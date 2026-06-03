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

public class DirigenteImplementazionePostgresDAO implements DirigenteDAO {

    private Connection connection;

    public DirigenteImplementazionePostgresDAO() {
        try {
            connection = ConnessioneDatabase.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void salva(Dirigente dirigente) {
        String sqlUtente = "INSERT INTO utente (login, password, nome, cognome) VALUES (?, ?, ?, ?)";
        String sqlDirigente = "INSERT INTO dirigente (id_dirigente, ruolo_organizzativo) VALUES (?, ?)";

        PreparedStatement psUtente = null;
        PreparedStatement psDirigente = null;

        try {
            connection.setAutoCommit(false);

            psUtente = connection.prepareStatement(sqlUtente, Statement.RETURN_GENERATED_KEYS);
            psUtente.setString(1, dirigente.getLogin());     // Oppure getUsername() se hai cambiato nome
            psUtente.setString(2, dirigente.getPassword());
            psUtente.setString(3, dirigente.getNome());
            psUtente.setString(4, dirigente.getCognome());
            psUtente.executeUpdate();

            int idGenerato = -1;
            try (ResultSet rsKeys = psUtente.getGeneratedKeys()) {
                if (rsKeys.next()) {
                    idGenerato = rsKeys.getInt(1);
                    dirigente.setIdUtente(String.valueOf(idGenerato));
                }
            }

            psDirigente = connection.prepareStatement(sqlDirigente);
            psDirigente.setInt(1, idGenerato);
            psDirigente.setString(2, dirigente.getRuoloOrganizzativo());
            psDirigente.executeUpdate();
            connection.commit();
            System.out.println("Dirigente salvato con successo. ID: " + idGenerato);

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
                if (psDirigente != null) psDirigente.close();

                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
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