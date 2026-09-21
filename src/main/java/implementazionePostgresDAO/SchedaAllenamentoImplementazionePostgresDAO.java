package implementazionePostgresDAO;

import dao.SchedaAllenamentoDAO;
import database.ConnessioneDatabase;
import model.SchedaAllenamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SchedaAllenamentoImplementazionePostgresDAO implements SchedaAllenamentoDAO {

    private Connection connection;

    public SchedaAllenamentoImplementazionePostgresDAO() {
        try {
            connection = ConnessioneDatabase.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void salva(SchedaAllenamento schedaAllenamento) {
        String query = "INSERT INTO SchedaAllenamento (Id_Scheda, Descrizione, Id_Istruttore, Id_Iscritto) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, schedaAllenamento.getId_Scheda());
            pstmt.setString(2, schedaAllenamento.getDescrizione());

            // Estraiamo gli ID dagli oggetti annidati.
            // N.B. Assicurati che i metodi getter di Istruttore e Iscritto si chiamino effettivamente così
            pstmt.setString(3, schedaAllenamento.getCreatore().getId_utente());
            pstmt.setString(4, schedaAllenamento.getProprietario().getId_utente());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Errore durante il salvataggio della scheda di allenamento: " + e.getMessage());
        }
    }

    @Override
    public SchedaAllenamento cercaPerId_Scheda(String id_Scheda) {
        String query = "SELECT * FROM SchedaAllenamento WHERE Id_Scheda = ?";
        SchedaAllenamento scheda = null;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, id_Scheda);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Restituiamo la scheda con null per gli oggetti complessi (o potresti caricarli qui con altri DAO)
                    scheda = new SchedaAllenamento(
                            rs.getString("Id_Scheda"),
                            rs.getString("Descrizione"),
                            null,
                            null
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la ricerca della scheda per ID: " + e.getMessage());
        }

        return scheda;
    }

    @Override
    public SchedaAllenamento cercaPerid_Iscritto(String id_iscritto) {
        String query = "SELECT * FROM SchedaAllenamento WHERE Id_Iscritto = ?";
        SchedaAllenamento scheda = null;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, id_iscritto);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    scheda = new SchedaAllenamento(
                            rs.getString("Id_Scheda"),
                            rs.getString("Descrizione"),
                            null,
                            null
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la ricerca della scheda per iscritto: " + e.getMessage());
        }

        return scheda;
    }

    @Override
    public List<SchedaAllenamento> trovaTutti() {
        String query = "SELECT * FROM SchedaAllenamento";
        List<SchedaAllenamento> listaSchede = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                SchedaAllenamento scheda = new SchedaAllenamento(
                        rs.getString("Id_Scheda"),
                        rs.getString("Descrizione"),
                        null,
                        null
                );
                listaSchede.add(scheda);
            }
        } catch (SQLException e) {
            System.err.println("Errore durante l'estrazione di tutte le schede: " + e.getMessage());
        }

        return listaSchede;
    }

    @Override
    public void aggiornaScheda(SchedaAllenamento schedaAllenamento) {
        String query = "UPDATE SchedaAllenamento SET Descrizione = ?, Id_Istruttore = ?, Id_Iscritto = ? WHERE Id_Scheda = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, schedaAllenamento.getDescrizione());

            // Anche qui estraiamo le chiavi esterne dagli oggetti del model
            pstmt.setString(2, schedaAllenamento.getCreatore().getId_utente());
            pstmt.setString(3, schedaAllenamento.getProprietario().getId_utente());
            pstmt.setString(4, schedaAllenamento.getId_Scheda());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiornamento della scheda: " + e.getMessage());
        }
    }
}