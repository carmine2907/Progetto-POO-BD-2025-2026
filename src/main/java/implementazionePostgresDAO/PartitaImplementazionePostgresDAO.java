package implementazionePostgresDAO;

import model.Campo;
import model.Partita;
import database.ConnessioneDatabase;
import dao.PartitaDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
    @Override
    public List<Partita> trovaTutti() {
        List<Partita> listaPartite = new ArrayList<>();
        // Query con JOIN per recuperare anche i dati del Campo associato
        String sql = """
                SELECT p.id_partita, p.data_part, p.ora_part, 
                       c.id_campo, c.nome, c.tipo, c.disponibile 
                FROM partita p 
                JOIN campo c ON p.id_campo = c.id_campo
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // 1. Ricostruiamo l'oggetto Campo
                Campo campo = new Campo(
                        rs.getInt("id_campo"),
                        rs.getString("nome"),
                        rs.getString("tipo"),
                        rs.getBoolean("disponibile")
                );

                // 2. Ricostruiamo l'oggetto Partita
                // Usiamo getObject per mappare LocalDate e LocalTime dal database
                Partita partita = new Partita(
                        rs.getInt("id_partita"),
                        rs.getObject("data_part", LocalDate.class),
                        rs.getObject("ora_part", LocalTime.class),
                        campo
                );

                listaPartite.add(partita);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaPartite;
    }
}