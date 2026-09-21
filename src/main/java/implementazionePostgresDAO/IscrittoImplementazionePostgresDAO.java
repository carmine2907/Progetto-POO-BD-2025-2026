package implementazionePostgresDAO;

import dao.IscrittoDAO;
import database.ConnessioneDatabase;
import model.Iscritto;
import model.Utente;

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
            // Ottiene la connessione tramite la tua classe Singleton
            connection = ConnessioneDatabase.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void salva(Iscritto iscritto) {
        String query = "INSERT INTO Iscritto (Id_Utente, Nome, Cognome, Username, Password) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, iscritto.getId_utente());
            pstmt.setString(2, iscritto.getNome());
            pstmt.setString(3, iscritto.getCognome());
            pstmt.setString(4, iscritto.getUsername());
            pstmt.setString(5, iscritto.getPassword());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore durante il salvataggio dell'utente: " + e.getMessage());
        }
    }

    @Override
    public Iscritto cercaPerUsername(String username){
        String query = "SELECT u.Id_Utente, u.Nome, u.Cognome, u.Username, u.Password " + "FROM Iscritto i JOIN Utente u ON i.Id_Iscritto = u.Id_Utente " + "WHERE u.Username = ?";
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
                            rs.getString("Password")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la ricerca dell'iscritto per Username: " + e.getMessage());
        }

        return iscritto;
    }

    @Override
    public List<Iscritto> trovaTutti() {
        String query = "SELECT u.Id_Utente, u.Nome, u.Cognome, u.Username, u.Password " + "FROM Iscritto i JOIN Utente u ON i.Id_Iscritto = u.Id_Utente " + "WHERE u.Username = ?";
        List<Iscritto> listaIscritti = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Iscritto iscritto= new Iscritto(
                        rs.getString("Id_Utente"),
                        rs.getString("Nome"),
                        rs.getString("Cognome"),
                        rs.getString("Username"),
                        rs.getString("Password")
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
        // Poiché Iscritto non ha attributi propri nel diagramma UML, l'aggiornamento
        // riguarda solo i dati anagrafici ereditati dalla tabella Utente.
        String query = "UPDATE Utente SET Nome = ?, Cognome = ?, Username = ?, Password = ? WHERE Id_Utente = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, iscritto.getId_utente());
            pstmt.setString(2, iscritto.getNome());
            pstmt.setString(3, iscritto.getCognome());
            pstmt.setString(4, iscritto.getUsername());
            pstmt.setString(5, iscritto.getPassword());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiornamento dell'iscritto: " + e.getMessage());
        }
    }

}
