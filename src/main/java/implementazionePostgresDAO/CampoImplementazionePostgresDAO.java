package implementazionePostgresDAO;

import model.Campo;
import database.ConnessioneDatabase;
import dao.CampoDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Campo implementazione postgres dao.
 */
public class CampoImplementazionePostgresDAO implements CampoDAO {

    private Connection connection;

    /**
     * Instantiates a new Campo implementazione postgres dao.
     */
    public CampoImplementazionePostgresDAO() {
        try {
            connection = ConnessioneDatabase.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void salva(Campo campo) {
        String sql = "INSERT INTO campo (id_campo, nome, tipo, disponibile) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, campo.getIdCampo());
            ps.setString(2, campo.getNome());
            ps.setString(3, campo.getTipo());
            ps.setBoolean(4, campo.isDisponibile());

            ps.executeUpdate();
            System.out.println("Campo salvato correttamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Campo cercaPerId(int idCampo) {
        String sql = "SELECT nome, tipo, disponibile FROM campo WHERE id_campo = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idCampo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String nome = rs.getString("nome");
                    String tipo = rs.getString("tipo");
                    boolean disponibile = rs.getBoolean("disponibile");

                    return new Campo(idCampo, nome, tipo, disponibile);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Campo> trovaTutti() {
        List<Campo> listaCampi = new ArrayList<>();
        String sql = "SELECT id_campo, nome, tipo, disponibile FROM campo";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int idCampo = rs.getInt("id_campo");
                String nome = rs.getString("nome");
                String tipo = rs.getString("tipo");
                boolean disponibile = rs.getBoolean("disponibile");

                Campo campo = new Campo(idCampo, nome, tipo, disponibile);
                listaCampi.add(campo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaCampi;
    }

    @Override
    public void aggiornaDisponibilita(int idCampo, boolean disponibile) {
        String sql = "UPDATE campo SET disponibile = ? WHERE id_campo = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBoolean(1, disponibile);
            ps.setInt(2, idCampo);

            int righeAggiornate = ps.executeUpdate();

            if (righeAggiornate > 0) {
                System.out.println("Disponibilità del campo aggiornata con successo.");
            } else {
                System.out.println("Campo non trovato. Nessun aggiornamento effettuato.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}