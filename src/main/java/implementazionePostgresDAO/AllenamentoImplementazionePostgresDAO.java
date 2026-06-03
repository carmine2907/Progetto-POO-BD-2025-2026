package implementazionePostgresDAO;

import model.Allenamento;
import model.Campo;
import model.Atleta;
import database.ConnessioneDatabase;
import dao.AllenamentoDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AllenamentoImplementazionePostgresDAO implements AllenamentoDAO {

    private Connection connection;

    public AllenamentoImplementazionePostgresDAO() {
        try {
            connection = ConnessioneDatabase.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void salva(Allenamento allenamento) {
        String sql = "INSERT INTO allenamento (id_allenamento, data_allenamento, orario, id_campo) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, allenamento.getIdAllenamento());
            ps.setObject(2, allenamento.getDataAllenamento());
            ps.setObject(3, allenamento.getOrario());

            ps.setInt(4, allenamento.getCampo().getIdCampo());

            ps.executeUpdate();
            System.out.println("Allenamento salvato con successo.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Allenamento cercaPerId(int idAllenamento) {
        String sql = """
                SELECT a.data_allenamento, a.orario, c.id_campo, c.nome, c.tipo, c.disponibile 
                FROM allenamento a 
                JOIN campo c ON a.id_campo = c.id_campo 
                WHERE a.id_allenamento = ?
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idAllenamento);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    int idCampo = rs.getInt("id_campo");
                    String nomeCampo = rs.getString("nome");
                    String tipoCampo = rs.getString("tipo");
                    boolean campoDisponibile = rs.getBoolean("disponibile");
                    Campo campo = new Campo(idCampo, nomeCampo, tipoCampo, campoDisponibile);
                    LocalDate dataAllenamento = rs.getObject("data_allenamento", LocalDate.class);
                    LocalTime orario = rs.getObject("orario", LocalTime.class);

                    return new Allenamento(idAllenamento, dataAllenamento, orario, campo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Allenamento> trovaTutti() {
        List<Allenamento> lista = new ArrayList<>();
        String sql = """
                SELECT a.id_allenamento, a.data_allenamento, a.orario, 
                       c.id_campo, c.nome, c.tipo, c.disponibile 
                FROM allenamento a 
                JOIN campo c ON a.id_campo = c.id_campo
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int idAllenamento = rs.getInt("id_allenamento");

                Campo campo = new Campo(
                        rs.getInt("id_campo"),
                        rs.getString("nome"),
                        rs.getString("tipo"),
                        rs.getBoolean("disponibile")
                );

                LocalDate dataAllenamento = rs.getObject("data_allenamento", LocalDate.class);
                LocalTime orario = rs.getObject("orario", LocalTime.class);

                Allenamento allenamento = new Allenamento(idAllenamento, dataAllenamento, orario, campo);
                lista.add(allenamento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void elimina(int idAllenamento) {
        String sql = "DELETE FROM allenamento WHERE id_allenamento = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idAllenamento);
            int righeEliminate = ps.executeUpdate();

            if (righeEliminate > 0) {
                System.out.println("Allenamento eliminato con successo.");
            } else {
                System.out.println("Nessun allenamento trovato con questo ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void salvaPresenza(Allenamento allenamento, Atleta atleta) {
        String sql = "INSERT INTO presenze_allenamento (id_allenamento, id_atleta) VALUES (?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, allenamento.getIdAllenamento());
            ps.setInt(2, Integer.parseInt(atleta.getIdUtente())); // Converte da String a int

            ps.executeUpdate();
            System.out.println("Presenza dell'atleta registrata correttamente per l'allenamento.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}