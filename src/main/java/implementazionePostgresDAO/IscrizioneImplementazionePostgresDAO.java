package implementazionePostgresDAO;

import model.Iscrizione;
import database.ConnessioneDatabase;
import dao.IscrizioneDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

/**
 * The type Iscrizione implementazione postgres dao.
 */
public class IscrizioneImplementazionePostgresDAO implements IscrizioneDAO {

    private Connection connection;

    /**
     * Instantiates a new Iscrizione implementazione postgres dao.
     */
    public IscrizioneImplementazionePostgresDAO() {
        try {
            connection = ConnessioneDatabase.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void salva(Iscrizione iscrizione) {
        String sql = "INSERT INTO iscrizione (id_iscrizione, data_iscrizione, stagione, valida) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, iscrizione.getIdIscrizione());

            java.sql.Date sqlDate = new java.sql.Date(iscrizione.getDataIscrizione().getTime());
            ps.setDate(2, sqlDate);

            ps.setString(3, iscrizione.getStagione());
            ps.setBoolean(4, iscrizione.isValida());

            ps.executeUpdate();
            System.out.println("Iscrizione salvata correttamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Iscrizione cercaPerId(int idIscrizione) {
        String sql = "SELECT data_iscrizione, stagione, valida FROM iscrizione WHERE id_iscrizione = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idIscrizione);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    java.util.Date dataIscrizione = rs.getDate("data_iscrizione");
                    String stagione = rs.getString("stagione");
                    boolean valida = rs.getBoolean("valida");

                    return new Iscrizione(idIscrizione, dataIscrizione, stagione, valida);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void aggiornaValidita(int idIscrizione, boolean valida) {
        String sql = "UPDATE iscrizione SET valida = ? WHERE id_iscrizione = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBoolean(1, valida);
            ps.setInt(2, idIscrizione);

            int righeAggiornate = ps.executeUpdate();

            if (righeAggiornate > 0) {
                System.out.println("Validità dell'iscrizione aggiornata con successo.");
            } else {
                System.out.println("Iscrizione non trovata. Nessun aggiornamento effettuato.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}