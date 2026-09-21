package implementazionePostgresDAO;

import dao.PartecipaDAO;
import database.ConnessioneDatabase;
import model.Partecipa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PartecipaImplementazionePostgresDAO implements PartecipaDAO {

    private Connection connection;

    public PartecipaImplementazionePostgresDAO() {
        try {
            connection = ConnessioneDatabase.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void salva(Partecipa partecipa) {
        String query = "INSERT INTO Partecipa (Id_Corso, Id_Iscritto) VALUES (?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, partecipa.getId_Corso());
            pstmt.setString(2, partecipa.getId_utente());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore durante il salvataggio della partecipazione: " + e.getMessage());
        }
    }

    @Override
    public Partecipa cercaPerId(String id_corso, String id_iscritto) {
        String query = "SELECT * FROM Partecipa WHERE Id_Corso = ? AND Id_Iscritto = ?";
        Partecipa partecipa = null;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, id_corso);
            pstmt.setString(2, id_iscritto);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Il costruttore richiede (String, String, Corso, Iscritto)
                    partecipa = new Partecipa(
                            rs.getString("Id_Corso"),
                            rs.getString("Id_utente"),
                            null,
                            null
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la ricerca della partecipazione: " + e.getMessage());
        }

        return partecipa;
    }

    @Override
    public void aggiornaPartecipazione(Partecipa partecipa) {
        // Essendo una tabella associativa che contiene solo le due chiavi primarie,
        // non ha senso fare un UPDATE. Per cambiare un'iscrizione si esegue una DELETE e poi una INSERT.
        System.out.println("Nessun attributo aggiornabile nella tabella associativa Partecipa.");
    }

    @Override
    public List<Partecipa> trovaPartecipazioniPerCorso(String idCorso) {
        String query = "SELECT * FROM Partecipa WHERE Id_Corso = ?";
        List<Partecipa> listaPartecipazioni = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, idCorso);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Partecipa p = new Partecipa(
                            rs.getString("Id_Corso"),
                            rs.getString("Id_Iscritto"),
                            null,
                            null
                    );
                    listaPartecipazioni.add(p);
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la ricerca per corso: " + e.getMessage());
        }

        return listaPartecipazioni;
    }

    @Override
    public List<Partecipa> trovaPartecipazioniPerIscritto(String idIscritto) {
        String query = "SELECT * FROM Partecipa WHERE Id_Iscritto = ?";
        List<Partecipa> listaPartecipazioni = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, idIscritto);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Partecipa p = new Partecipa(
                            rs.getString("Id_Corso"),
                            rs.getString("Id_Iscritto"),
                            null,
                            null
                    );
                    listaPartecipazioni.add(p);
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la ricerca per iscritto: " + e.getMessage());
        }

        return listaPartecipazioni;
    }
}