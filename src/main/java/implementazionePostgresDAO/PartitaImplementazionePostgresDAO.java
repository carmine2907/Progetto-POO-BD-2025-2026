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
        String sql = "INSERT INTO partita (id_partita, data_part, ora_part, id_campo) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, partita.getIdPartita());

            // CONVERSIONE ESPLICITA: Trasformiamo i tipi Java nei tipi esatti che vuole SQL
            java.sql.Date sqlDate = java.sql.Date.valueOf(partita.getDataPart());
            java.sql.Time sqlTime = java.sql.Time.valueOf(partita.getOraPart());

            // Usiamo i metodi specifici (setDate e setTime) invece del generico setObject
            ps.setDate(2, sqlDate); // Va nella colonna data_part
            ps.setTime(3, sqlTime); // Va nella colonna ora_part

            // Il campo ha l'ID che ci serve
            ps.setInt(4, partita.getCampo().getIdCampo());

            ps.executeUpdate();
            System.out.println("Partita inserita a sistema correttamente.");

        } catch (SQLException e) {
            System.err.println("ERRORE SALVATAGGIO PARTITA: " + e.getMessage());
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