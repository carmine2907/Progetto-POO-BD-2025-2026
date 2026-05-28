package implementazionePostgresDAO;



import model.Squadra;
import model.Atleta;
import database.ConnessioneDatabase;
import dao.SquadraDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SquadraImplementazionePostgresDAO implements SquadraDAO {

    private Connection connection;

    public SquadraImplementazionePostgresDAO() {
        try {
            connection = ConnessioneDatabase.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void assegnaAtleta(Squadra squadra, Atleta atleta) {
        // Aggiorniamo la tabella atleta inserendo l'id della squadra
        String sql = "UPDATE atleta SET id_squadra = ? WHERE id_atleta = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, squadra.getIdSquadra());

            // Dato che idUtente è salvato come String nel tuo model, lo convertiamo in int
            ps.setInt(2, Integer.parseInt(atleta.getIdUtente()));

            int righeAggiornate = ps.executeUpdate();

            if (righeAggiornate > 0) {
                System.out.println("Atleta assegnato alla squadra con successo.");
            } else {
                System.out.println("Nessun atleta aggiornato. Verificare l'ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}