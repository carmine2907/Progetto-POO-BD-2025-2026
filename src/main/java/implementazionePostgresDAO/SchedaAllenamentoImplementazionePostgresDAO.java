package implementazionePostgresDAO;

import dao.SchedaAllenamentoDAO;
import database.ConnessioneDatabase;
import model.SchedaAllenamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
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
    public void salva(SchedaAllenamento scheda) {
        String query = "INSERT INTO SchedaAllenamento (Id_Scheda, Descrizione, Id_Istruttore, Id_Iscritto) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, scheda.getId_Scheda());
            pstmt.setString(2, scheda.getDescrizione());

            if (scheda.getCreatore() != null) {
                pstmt.setString(3, scheda.getCreatore().getId_utente());
            } else {
                pstmt.setNull(3, Types.VARCHAR);
            }

            if (scheda.getProprietario() != null) {
                pstmt.setString(4, scheda.getProprietario().getId_utente());
            } else {
                pstmt.setNull(4, Types.VARCHAR);
            }

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Errore durante il salvataggio: " + e.getMessage());
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
                    scheda = new SchedaAllenamento(
                            rs.getString("id_scheda"),
                            rs.getString("descrizione"),
                            null,
                            null
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore ricerca ID Scheda: " + e.getMessage());
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
            System.err.println("Errore ricerca ID Iscritto: " + e.getMessage());
        }

        return scheda;
    }

    @Override
    public List<SchedaAllenamento> trovaTutti() {
        String query = "SELECT * FROM SchedaAllenamento";
        List<SchedaAllenamento> lista = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                SchedaAllenamento scheda = new SchedaAllenamento(
                        rs.getString("Id_Scheda"),
                        rs.getString("Descrizione"),
                        null,
                        null
                );
                lista.add(scheda);
            }
        } catch (SQLException e) {
            System.err.println("Errore estrazione schede: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public void aggiornaScheda(SchedaAllenamento scheda) {
        String query = "UPDATE SchedaAllenamento SET Descrizione = ?, Id_Istruttore = ?, Id_Iscritto = ? WHERE Id_Scheda = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, scheda.getDescrizione());

            if (scheda.getCreatore() != null) {
                pstmt.setString(2, scheda.getCreatore().getId_utente());
            } else {
                pstmt.setNull(2, Types.VARCHAR);
            }

            if (scheda.getProprietario() != null) {
                pstmt.setString(3, scheda.getProprietario().getId_utente());
            } else {
                pstmt.setNull(3, Types.VARCHAR);
            }

            pstmt.setString(4, scheda.getId_Scheda());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Errore aggiornamento scheda: " + e.getMessage());
        }
    }
}