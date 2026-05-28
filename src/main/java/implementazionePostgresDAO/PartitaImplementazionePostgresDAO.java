package implementazionePostgresDAO;

import model.Partita;
import database.ConnessioneDatabase;
import dao.PartitaDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PartitaImplementazionePostgresDAO implements PartitaDAO {

    private Connection connection;

    public PartitaImplementazionePostgresDAO() {
        try {
            connection = ConnessioneDatabase.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void salva(Partita partita) {
        String sql = "INSERT INTO partita (id_partita, data_partita, ora_partita, id_campo) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, partita.getIdPartita());

            // PostgreSQL e JDBC gestiscono in modo nativo java.time usando setObject()
            ps.setObject(2, partita.getOraPart()); // LocalDate
            ps.setObject(3, partita.getDataPart());  // LocalTime

            ps.setInt(4, partita.getCampo().getIdCampo());

            ps.executeUpdate();
            System.out.println("Partita inserita a sistema correttamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}