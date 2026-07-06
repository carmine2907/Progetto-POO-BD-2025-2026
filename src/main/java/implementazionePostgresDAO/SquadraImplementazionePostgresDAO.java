package implementazionePostgresDAO;



import model.Squadra;
import model.Atleta;
import database.ConnessioneDatabase;
import dao.SquadraDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Squadra implementazione postgres dao.
 */
public class SquadraImplementazionePostgresDAO implements SquadraDAO {

    private Connection connection;

    /**
     * Instantiates a new Squadra implementazione postgres dao.
     */
    public SquadraImplementazionePostgresDAO() {
        try {
            connection = ConnessioneDatabase.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void assegnaAtleta(Squadra squadra, Atleta atleta) {
        // La query corretta inserisce un record nella tabella 'iscrizione'
        String sql = "INSERT INTO iscrizione (data_iscrizione, stagione, valida, id_atleta, id_squadra) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            // 1. Data di iscrizione: prendiamo la data di oggi in automatico
            ps.setDate(1, java.sql.Date.valueOf(LocalDate.now()));

            // 2. Stagione: puoi impostare quella corrente
            ps.setString(2, "2026/2027");

            // 3. Valida: impostiamo a true (oppure false se deve approvarla il dirigente)
            ps.setBoolean(3, true);

            // 4. ID dell'Atleta (che corrisponde all'ID dell'Utente)
            ps.setInt(4, Integer.parseInt(atleta.getIdUtente()));

            // 5. ID della Squadra
            ps.setInt(5, squadra.getIdSquadra());

            // Eseguiamo l'inserimento
            ps.executeUpdate();
            System.out.println("Atleta iscritto alla squadra con successo!");

        } catch (SQLException e) {
            System.err.println("ERRORE ASSEGNAZIONE ATLETA: " + e.getMessage());
            // Lanciamo l'errore per farlo catturare alla GUI (utile per l'eccezione Unique Constraint)
            throw new RuntimeException("Errore Database: " + e.getMessage());
        }
    }
    @Override
    public List<Squadra> trovaTutti() {
        List<Squadra> listaSquadre = new ArrayList<>();
        String sql = "SELECT id_squadra, nome, categoria, max_giocatori, id_allenatore FROM squadra";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int idSquadra = rs.getInt("id_squadra");
                String nome = rs.getString("nome");
                String categoria = rs.getString("categoria");
                int maxGiocatori = rs.getInt("max_giocatori");
                // Gestione dell'allenatore (se presente)
                int idAllenatore = rs.getInt("id_allenatore");

                // Creazione dell'oggetto Squadra (assicurati che il costruttore corrisponda a quello del tuo model)
                Squadra squadra = new Squadra(idSquadra, nome, categoria, maxGiocatori);

                listaSquadre.add(squadra);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaSquadre;
    }
}