package implementazionePostgresDAO;

import dao.MembroVipDAO;
import database.ConnessioneDatabase;
import model.MembroVip;
import model.Pagamento;
import model.Partecipa;
import model.SchedaAllenamento;
import model.ServizioWellness;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MembroVipImplementazionePostgresDAO implements MembroVipDAO {

    private Connection connection;

    public MembroVipImplementazionePostgresDAO() {
        try {
            connection = ConnessioneDatabase.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
// merda
    @Override
    public void salva(MembroVip membroVip) {
        // Pattern Table-per-Subclass: Inserimento in Utente -> Iscritto -> MembroVip
        String queryUtente = "INSERT INTO Utente (Id_Utente, Nome, Cognome, Username, Password) VALUES (?, ?, ?, ?, ?)";
        String queryIscritto = "INSERT INTO Iscritto (Id_Utente) VALUES (?)";
        String queryMembroVip = "INSERT INTO MembroVip (Id_Utente) VALUES (?)";

        try {
            // Inserimento nella tabella base (Utente)
            try (PreparedStatement pstmtUtente = connection.prepareStatement(queryUtente)) {
                pstmtUtente.setString(1, membroVip.getId_utente());
                pstmtUtente.setString(2, membroVip.getNome());
                pstmtUtente.setString(3, membroVip.getCognome());
                pstmtUtente.setString(4, membroVip.getUsername());
                pstmtUtente.setString(5, membroVip.getPassword());
                pstmtUtente.executeUpdate();
            }

            // Inserimento nella tabella figlia (Iscritto)
            try (PreparedStatement pstmtIscritto = connection.prepareStatement(queryIscritto)) {
                pstmtIscritto.setString(1, membroVip.getId_utente());
                pstmtIscritto.executeUpdate();
            }

            // Inserimento nella tabella nipote (MembroVip)
            try (PreparedStatement pstmtVip = connection.prepareStatement(queryMembroVip)) {
                pstmtVip.setString(1, membroVip.getId_utente());
                pstmtVip.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println("Errore durante il salvataggio del Membro Vip: " + e.getMessage());
        }
    }

    @Override
    public MembroVip cercaPerUsername(String username) {
        // Recuperiamo i dati dalla tabella Utente, sapendo che l'utente è un MembroVip
        String query = "SELECT u.Id_Utente, u.Nome, u.Cognome, u.Username, u.Password " +
                "FROM MembroVip mv " +
                "JOIN Utente u ON mv.Id_Utente = u.Id_Utente " +
                "WHERE u.Username = ?";
        MembroVip membroVip = null;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    membroVip = mappaMembroVip(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la ricerca del Membro Vip per Username: " + e.getMessage());
        }

        return membroVip;
    }

    @Override
    public List<MembroVip> trovaTutti() {
        String query = "SELECT u.Id_Utente, u.Nome, u.Cognome, u.Username, u.Password " +
                "FROM MembroVip mv " +
                "JOIN Utente u ON mv.Id_Utente = u.Id_Utente";
        List<MembroVip> listaMembriVip = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                listaMembriVip.add(mappaMembroVip(rs));
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero di tutti i Membri Vip: " + e.getMessage());
        }

        return listaMembriVip;
    }

    @Override
    public void aggiornaMembro(MembroVip membroVip) {
        // L'aggiornamento dei dati anagrafici va fatto sulla tabella base Utente
        String query = "UPDATE Utente SET Nome = ?, Cognome = ?, Username = ?, Password = ? WHERE Id_Utente = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, membroVip.getNome());
            pstmt.setString(2, membroVip.getCognome());
            pstmt.setString(3, membroVip.getUsername());
            pstmt.setString(4, membroVip.getPassword());
            pstmt.setString(5, membroVip.getId_utente());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiornamento del Membro Vip: " + e.getMessage());
        }
    }

    /**
     * Metodo di supporto per convertire il ResultSet nell'oggetto MembroVip
     * rispettando tutti i parametri richiesti dal costruttore della classe.
     */
    private MembroVip mappaMembroVip(ResultSet rs) throws SQLException {
        String idUtente = rs.getString("Id_Utente");
        String nome = rs.getString("Nome");
        String cognome = rs.getString("Cognome");
        String username = rs.getString("Username");
        String password = rs.getString("Password");

        // Richiamiamo il costruttore di MembroVip passando le liste vuote e la scheda nulla.
        // Parametri: id_Utente, nome, cognome, username, password, pagamenti, schedaAllenamento, partecipazioni, serviziPrenotati
        return new MembroVip(
                idUtente,
                nome,
                cognome,
                username,
                password,
                new ArrayList<Pagamento>(),
                null,
                new ArrayList<Partecipa>(),
                new ArrayList<ServizioWellness>()
        );
    }
}
