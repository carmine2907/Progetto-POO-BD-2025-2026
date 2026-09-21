package implementazionePostgresDAO;

import dao.IstruttoreDAO;
import database.ConnessioneDatabase;
import model.Corso;
import model.Istruttore;
import model.SchedaAllenamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class IstruttoreImplementazionePostgresDAO implements IstruttoreDAO {

    private Connection connection;

    public IstruttoreImplementazionePostgresDAO() {
        try {
            connection = ConnessioneDatabase.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void salva(Istruttore istruttore) {
        String insertUtente = "INSERT INTO Utente (Id_Utente, Nome, Cognome, Username, Password) VALUES (?, ?, ?, ?, ?)";
        String insertIstruttore = "INSERT INTO Istruttore (Id_Istruttore) VALUES (?)";

        try {
            // Disabilitiamo l'autocommit per la transazione
            connection.setAutoCommit(false);

            // 1. Salviamo i dati anagrafici nella superclasse Utente
            try (PreparedStatement pstmtUtente = connection.prepareStatement(insertUtente)) {
                pstmtUtente.setString(1, istruttore.getId_utente());
                pstmtUtente.setString(2, istruttore.getNome());
                pstmtUtente.setString(3, istruttore.getCognome());
                pstmtUtente.setString(4, istruttore.getUsername());
                pstmtUtente.setString(5, istruttore.getPassword());
                pstmtUtente.executeUpdate();
            }

            try (PreparedStatement pstmtIstruttore = connection.prepareStatement(insertIstruttore)) {
                pstmtIstruttore.setString(1, istruttore.getId_utente());
                pstmtIstruttore.executeUpdate();
            }

            connection.commit();

        } catch (SQLException e) {
            System.err.println("Errore durante il salvataggio dell'istruttore: " + e.getMessage());
            try {
                // In caso di errore, si fa il rollback per evitare dati parziali
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
    public Istruttore cercaPerUsername(String username) {
        String query = "SELECT u.Id_Utente, u.Nome, u.Cognome, u.Username, u.Password " +
                "FROM Istruttore i JOIN Utente u ON i.Id_Istruttore = u.Id_Utente " +
                "WHERE u.Username = ?";
        Istruttore istruttore = null;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    istruttore = new Istruttore(
                            rs.getString("Id_Utente"),
                            rs.getString("Nome"),
                            rs.getString("Cognome"),
                            rs.getString("Username"),
                            rs.getString("Password"),
                            new ArrayList<Corso>(),            // Lista corsi vuota
                            new ArrayList<SchedaAllenamento>() // Lista schede vuota
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la ricerca per Username: " + e.getMessage());
        }

        return istruttore;
    }

    @Override
    public List<Istruttore> trovaTutti() {
        String query = "SELECT u.Id_Utente, u.Nome, u.Cognome, u.Username, u.Password " +
                "FROM Istruttore i JOIN Utente u ON i.Id_Istruttore = u.Id_Utente";
        List<Istruttore> listaIstruttori = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Istruttore istruttore = new Istruttore(
                        rs.getString("Id_Utente"),
                        rs.getString("Nome"),
                        rs.getString("Cognome"),
                        rs.getString("Username"),
                        rs.getString("Password"),
                        new ArrayList<Corso>(),
                        new ArrayList<SchedaAllenamento>()
                );
                listaIstruttori.add(istruttore);
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero di tutti gli istruttori: " + e.getMessage());
        }

        return listaIstruttori;
    }

    @Override
    public void aggiornaIstruttore(Istruttore istruttore) {
        String query = "UPDATE Utente SET Nome = ?, Cognome = ?, Username = ?, Password = ? WHERE Id_Utente = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, istruttore.getId_utente());
            pstmt.setString(2, istruttore.getNome());
            pstmt.setString(3, istruttore.getCognome());
            pstmt.setString(4, istruttore.getUsername());
            pstmt.setString(5, istruttore.getPassword());

            int righeModificate = pstmt.executeUpdate();
            if(righeModificate == 0) {
                System.out.println("Nessun istruttore aggiornato. L'ID potrebbe non esistere.");
            }
        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiornamento dell'istruttore: " + e.getMessage());
        }
    }
}