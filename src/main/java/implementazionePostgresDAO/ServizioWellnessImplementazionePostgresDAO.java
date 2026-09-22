package implementazionePostgresDAO;

import dao.ServizioWellnessDAO;
import database.ConnessioneDatabase;
import model.ServizioWellness;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ServizioWellnessImplementazionePostgresDAO implements ServizioWellnessDAO {

    private Connection connection;

    public ServizioWellnessImplementazionePostgresDAO() {
        try {
            connection = ConnessioneDatabase.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void salva(ServizioWellness servizioWellness) {
        String query = "INSERT INTO ServizioWellness (Id_ServizioWellness, NomeServizio, Disponibile) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, servizioWellness.getId_ServizioWellness());
            pstmt.setString(2, servizioWellness.getNomeServizio());
            pstmt.setBoolean(3, servizioWellness.getDisponibile());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore durante il salvataggio del servizio wellness: " + e.getMessage());
        }
    }

    @Override
    public ServizioWellness cercaPerId(String id_ServizioWellness) {
        String query = "SELECT * FROM ServizioWellness WHERE Id_ServizioWellness = ?";
        ServizioWellness servizio = null;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, id_ServizioWellness);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    servizio = mappaServizioWellness(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la ricerca del servizio wellness per ID: " + e.getMessage());
        }

        return servizio;
    }

    @Override
    public List<ServizioWellness> trovaTutti() {
        String query = "SELECT * FROM ServizioWellness";
        List<ServizioWellness> listaServizi = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                listaServizi.add(mappaServizioWellness(rs));
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero di tutti i servizi wellness: " + e.getMessage());
        }

        return listaServizi;
    }

    @Override
    public void aggiornaServizioWellness(ServizioWellness servizioWellness) {
        String query = "UPDATE ServizioWellness SET NomeServizio = ?, Disponibile = ? WHERE Id_ServizioWellness = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, servizioWellness.getNomeServizio());
            pstmt.setBoolean(2, servizioWellness.getDisponibile());
            pstmt.setString(3, servizioWellness.getId_ServizioWellness());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiornamento del servizio wellness: " + e.getMessage());
        }
    }

    private ServizioWellness mappaServizioWellness(ResultSet rs) throws SQLException {
        String idServizio = rs.getString("Id_ServizioWellness");
        String nomeServizio = rs.getString("NomeServizio");
        boolean disponibile = rs.getBoolean("Disponibile");

        return new ServizioWellness(idServizio, nomeServizio, disponibile);
    }
}