package implementazionePostgresDAO;

import dao.IscrittoDAO;
import database.ConnessioneDatabase;
import model.Iscritto;
import model.Pagamento;
import model.Partecipa;
import model.SchedaAllenamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class IscrittoImplementazionePostgresDAO implements IscrittoDAO {

    private Connection connection;

    public IscrittoImplementazionePostgresDAO() {
        try {
            connection = ConnessioneDatabase.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
// porca troia
    @Override
    public void salva(Iscritto iscritto) {
        String insertUtente = "INSERT INTO Utente (Id_Utente, Nome, Cognome, Username, Password) VALUES (?, ?, ?, ?, ?)";
        String insertIscritto = "INSERT INTO Iscritto (Id_Iscritto) VALUES (?)";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement pstmtUtente = connection.prepareStatement(insertUtente)) {
                pstmtUtente.setString(1, iscritto.getId_utente());
                pstmtUtente.setString(2, iscritto.getNome());
                pstmtUtente.setString(3, iscritto.getCognome());
                pstmtUtente.setString(4, iscritto.getUsername());
                pstmtUtente.setString(5, iscritto.getPassword());
                pstmtUtente.executeUpdate();
            }

            try (PreparedStatement pstmtIscritto = connection.prepareStatement(insertIscritto)) {
                pstmtIscritto.setString(1, iscritto.getId_utente());
                pstmtIscritto.executeUpdate();
            }

            connection.commit();

            // Nota: Il salvataggio delle liste (Pagamenti, Partecipazioni, Scheda)
            // NON avviene qui. Se l'oggetto iscritto ha dei pagamenti nuovi,
            // dovrai chiamare PagamentoDAO.salva(pagamento) iterando la lista nel Controller.

        } catch (SQLException e) {
            System.err.println("Errore durante il salvataggio dell'iscritto: " + e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                System.err.println("Errore durante il rollback: " + rollbackEx.getMessage());
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Iscritto cercaPerUsername(String username) {
        String query = "SELECT u.Id_Utente, u.Nome, u.Cognome, u.Username, u.Password " +
                "FROM Iscritto i JOIN Utente u ON i.Id_Iscritto = u.Id_Utente " +
                "WHERE u.Username = ?";
        Iscritto iscritto = null;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    iscritto = new Iscritto(
                            rs.getString("Id_Utente"),
                            rs.getString("Nome"),
                            rs.getString("Cognome"),
                            rs.getString("Username"),
                            rs.getString("Password"),
                            new ArrayList<Pagamento>(),
                            null,
                            new ArrayList<Partecipa>()
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la ricerca per Username: " + e.getMessage());
        }

        return iscritto;
    }

    @Override
    public List<Iscritto> trovaTutti() {
        String query = "SELECT u.Id_Utente, u.Nome, u.Cognome, u.Username, u.Password " +
                "FROM Iscritto i JOIN Utente u ON i.Id_Iscritto = u.Id_Utente";
        List<Iscritto> listaIscritti = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Iscritto iscritto = new Iscritto(
                        rs.getString("Id_Utente"),
                        rs.getString("Nome"),
                        rs.getString("Cognome"),
                        rs.getString("Username"),
                        rs.getString("Password"),
                        new ArrayList<Pagamento>(),
                        null,
                        new ArrayList<Partecipa>()
                );
                listaIscritti.add(iscritto);
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero di tutti gli iscritti: " + e.getMessage());
        }

        return listaIscritti;
    }

    @Override
    public void aggiornaIscritto(Iscritto iscritto) {
        String query = "UPDATE Utente SET Nome = ?, Cognome = ?, Username = ?, Password = ? WHERE Id_Utente = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, iscritto.getId_utente());
            pstmt.setString(2, iscritto.getNome());
            pstmt.setString(3, iscritto.getCognome());
            pstmt.setString(4, iscritto.getUsername());
            pstmt.setString(5, iscritto.getPassword());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiornamento: " + e.getMessage());
        }
    }

}