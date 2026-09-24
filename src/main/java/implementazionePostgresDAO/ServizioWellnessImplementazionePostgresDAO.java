package implementazionePostgresDAO;


// Importiamo la classe del modello ServizioWellness[cite: 5]
import dao.ServizioWellnessDAO;
import database.ConnessioneDatabase;
import model.ServizioWellness;
// Importiamo la classe MembroVip per gestire la chiave esterna del membro prenotante
import model.MembroVip;

// Importiamo le librerie per l'interazione con il database SQL
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

// Importiamo le librerie per le liste
import java.util.ArrayList;
import java.util.List;

// La classe implementa l'interfaccia ServizioWellnessDAO[cite: 6]
public class ServizioWellnessImplementazionePostgresDAO implements ServizioWellnessDAO {

    // Variabile per mantenere la connessione al database
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
        // Prepariamo la query SQL di inserimento per il servizio wellness
        String query = "INSERT INTO servizio_wellness (id_serviziowellness, nome_servizio, disponibile, id_membro_prenotante) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            // Impostiamo i parametri presi dall'oggetto servizioWellness[cite: 5]
            stmt.setString(1, servizioWellness.getId_ServizioWellness());
            stmt.setString(2, servizioWellness.getNomeServizio());

            // Gestiamo il parametro booleano per la disponibilità[cite: 5]
            if (servizioWellness.getDisponibile() != null) {
                stmt.setBoolean(3, servizioWellness.getDisponibile());
            } else {
                stmt.setNull(3, Types.BOOLEAN);
            }

            // Gestiamo la chiave esterna: verifichiamo se c'è un membro che ha prenotato il servizio[cite: 5]
            if (servizioWellness.getMembroPrenotante() != null) {
                // Estraiamo l'ID dell'utente dal membro prenotante
                stmt.setString(4, servizioWellness.getMembroPrenotante().getId_utente());
            } else {
                // Se il servizio non è prenotato da nessuno, inseriamo NULL nel database
                stmt.setNull(4, Types.VARCHAR);
            }

            // Eseguiamo la query di inserimento
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ServizioWellness cercaPerId(String id_ServizioWellness) {
        // Query per trovare un servizio wellness tramite il suo ID[cite: 6]
        String query = "SELECT * FROM servizio_wellness WHERE id_serviziowellness = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Sostituiamo il punto interrogativo con l'ID passato al metodo
            stmt.setString(1, id_ServizioWellness);

            // Eseguiamo la query e leggiamo i risultati
            try (ResultSet rs = stmt.executeQuery()) {

                // Se il database trova una riga corrispondente
                if (rs.next()) {

                    // 1. Estrazione esplicita di ogni colonna
                    String idEstratto = rs.getString("id_serviziowellness");
                    String nomeEstratto = rs.getString("nome_servizio");
                    // Estraiamo il valore booleano
                    Boolean disponibileEstratto = rs.getBoolean("disponibile");
                    // Se la colonna era NULL nel DB, correggiamo il booleano a null
                    if (rs.wasNull()) {
                        disponibileEstratto = null;
                    }

                    // Estraiamo la chiave esterna del membro
                    String idMembroEstratto = rs.getString("id_membro_prenotante");

                    // 2. Creazione dell'oggetto dipendente "MembroVip" (Proxy)
                    MembroVip membro = null;
                    if (idMembroEstratto != null) {
                        // Creiamo un oggetto MembroVip popolando solo l'ID e lasciando il resto a null/vuoto
                        // (Utilizziamo il costruttore completo della classe MembroVip vista in precedenza)
                        membro = new MembroVip(
                                idMembroEstratto,
                                null, null, null, null,
                                new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>()
                        );
                    }

                    // 3. Creazione e restituzione dell'oggetto ServizioWellness finale[cite: 5]
                    return new ServizioWellness(idEstratto, nomeEstratto, disponibileEstratto, membro);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Restituiamo null se non viene trovato nessun servizio
    }

    @Override
    public List<ServizioWellness> trovaTutti() {
        // Creiamo la lista vuota che conterrà tutti i servizi trovati[cite: 6]
        List<ServizioWellness> servizi = new ArrayList<>();
        String query = "SELECT * FROM servizio_wellness";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            // Ciclo while per scorrere tutte le righe della tabella
            while (rs.next()) {

                // RIPETIAMO LA LOGICA DI ESTRAZIONE PER OGNI RIGA
                String idEstratto = rs.getString("id_serviziowellness");
                String nomeEstratto = rs.getString("nome_servizio");

                Boolean disponibileEstratto = rs.getBoolean("disponibile");
                if (rs.wasNull()) {
                    disponibileEstratto = null;
                }

                String idMembroEstratto = rs.getString("id_membro_prenotante");

                MembroVip membro = null;
                if (idMembroEstratto != null) {
                    membro = new MembroVip(
                            idMembroEstratto,
                            null, null, null, null,
                            new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>()
                    );
                }

                // Creiamo l'oggetto ServizioWellness corrente[cite: 5]
                ServizioWellness servizioCorrente = new ServizioWellness(idEstratto, nomeEstratto, disponibileEstratto, membro);

                // Lo aggiungiamo alla lista
                servizi.add(servizioCorrente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Restituiamo la lista completa
        return servizi;
    }

    @Override
    public void aggiornaServizioWellness(ServizioWellness servizioWellness) {
        // Query per aggiornare i campi di un servizio esistente[cite: 6]
        String query = "UPDATE servizio_wellness SET nome_servizio = ?, disponibile = ?, id_membro_prenotante = ? WHERE id_serviziowellness = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            // Impostiamo il nome del servizio
            stmt.setString(1, servizioWellness.getNomeServizio());

            // Impostiamo la disponibilità
            if (servizioWellness.getDisponibile() != null) {
                stmt.setBoolean(2, servizioWellness.getDisponibile());
            } else {
                stmt.setNull(2, Types.BOOLEAN);
            }

            // Aggiorniamo la chiave esterna del membro (potrebbe essere stata assegnata o rimossa)[cite: 5]
            if (servizioWellness.getMembroPrenotante() != null) {
                stmt.setString(3, servizioWellness.getMembroPrenotante().getId_utente());
            } else {
                stmt.setNull(3, Types.VARCHAR); // Se il membro prenotante è null, rimuoviamo l'associazione nel DB
            }

            // Specifichiamo l'ID del servizio da modificare (la clausola WHERE)[cite: 5]
            stmt.setString(4, servizioWellness.getId_ServizioWellness());

            // Eseguiamo l'aggiornamento
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}