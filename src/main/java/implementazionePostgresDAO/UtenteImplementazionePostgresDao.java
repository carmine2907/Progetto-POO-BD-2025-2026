package implementazionePostgresDAO;

import model.Utente;
import database.ConnessioneDatabase;
import dao.UtenteDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtenteImplementazionePostgresDao implements UtenteDAO {

    private Connection connection;

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
        }
    }

    @Override
    public Utente cercaPerUsername(String username) {
        String sql = "SELECT login, password, nome, cognome FROM utente WHERE login = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Utente(
                            rs.getString("nome"),
                            rs.getString("cognome"),
                            rs.getString("login"),
                            rs.getString("password")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}