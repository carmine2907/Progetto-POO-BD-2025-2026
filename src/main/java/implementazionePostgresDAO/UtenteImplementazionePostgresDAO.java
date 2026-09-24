package implementazionePostgresDAO;

import dao.UtenteDAO;
import database.ConnessioneDatabase;
import model.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UtenteImplementazionePostgresDAO implements UtenteDAO {

    private Connection connection;

    public UtenteImplementazionePostgresDAO() {
        try {
            // Ottiene la connessione tramite la tua classe Singleton
            connection = ConnessioneDatabase.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void salva(Utente utente) {
        String query = "INSERT INTO Utente ( username,  password,  id_utente,  nome, cognome) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, utente.getUsername());
            pstmt.setString(2, utente.getPassword());
            pstmt.setString(3, utente.getId_utente());
            pstmt.setString(4, utente.getNome());
            pstmt.setString(5, utente.getCognome());


            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore durante il salvataggio dell'utente: " + e.getMessage());
        }
    }

    @Override
    public Utente cercaPerId(String id_utente) {
        String query = "SELECT * FROM Utente WHERE Id_Utente = ?";
        Utente utente = null;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, id_utente);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    utente = new Utente(
                            rs.getString("Id_Utente"),
                            rs.getString("Nome"),
                            rs.getString("Cognome"),
                            rs.getString("Username"),
                            rs.getString("Password")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la ricerca per ID: " + e.getMessage());
        }

        return utente;
    }

    @Override
    public Utente cercaPerUsername(String username) {
        String query = "SELECT * FROM Utente WHERE Username = ?";
        Utente utente = null;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    utente = new Utente(
                            rs.getString("Id_Utente"),
                            rs.getString("Nome"),
                            rs.getString("Cognome"),
                            rs.getString("Username"),
                            rs.getString("Password")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la ricerca per Username: " + e.getMessage());
        }

        return utente;
    }

    @Override
    public List<Utente> trovaTutti() {
        String query = "SELECT * FROM Utente";
        List<Utente> listaUtenti = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Utente utente = new Utente(
                        rs.getString("Id_Utente"),
                        rs.getString("Nome"),
                        rs.getString("Cognome"),
                        rs.getString("Username"),
                        rs.getString("Password")
                );
                listaUtenti.add(utente);
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero di tutti gli utenti: " + e.getMessage());
        }

        return listaUtenti;
    }

    @Override
    public void aggiornaUtente(Utente utente) {
        String query = "UPDATE Utente SET Nome = ?, Cognome = ?, Username = ?, Password = ? WHERE Id_Utente = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, utente.getNome());
            pstmt.setString(2, utente.getCognome());
            pstmt.setString(3, utente.getUsername());
            pstmt.setString(4, utente.getPassword());
            pstmt.setString(5, utente.getId_utente());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiornamento dell'utente: " + e.getMessage());
        }
    }


}