package implementazionePostgresDAO;

// Importiamo le classi del nostro modello per poterle utilizzare
import dao.CorsoDAO;
import database.ConnessioneDatabase;
import model.Corso;
import model.Istruttore;

// Importiamo le classi della libreria standard di Java per lavorare con i database SQL
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

// Importiamo le classi per gestire le liste
import java.util.ArrayList;
import java.util.List;

// La classe implementa l'interfaccia CorsoDAO, promettendo di definire tutti i suoi metodi
public class CorsoImplementazionePostgresDAO implements CorsoDAO {

    // Variabile per memorizzare la connessione al database
    private Connection connection;
    public CorsoImplementazionePostgresDAO() {
        try {
            connection = ConnessioneDatabase.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void salva(Corso corso) {
        // Scriviamo la query SQL per inserire un nuovo corso. I punti interrogativi (?) sono parametri dinamici.
        String query = "INSERT INTO corso (id_corso, nome_corso, capienza, id_istruttore) VALUES (?, ?, ?, ?)";

        // Apriamo il PreparedStatement per eseguire la query in modo sicuro
        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            // Sostituiamo il primo '?' con l'ID del corso preso dall'oggetto Java
            stmt.setString(1, corso.getId_Corso());
            // Sostituiamo il secondo '?' con il nome del corso
            stmt.setString(2, corso.getNomeCorso());
            // Sostituiamo il terzo '?' con la capienza (che è un numero intero)
            stmt.setInt(3, corso.getCapienza());

            // Per il quarto '?', verifichiamo se il corso ha un istruttore assegnato
            if (corso.getIstruttoreGestore() != null) {
                // Se esiste, estraiamo l'ID dell'istruttore e lo inseriamo nella query
                stmt.setString(4, corso.getIstruttoreGestore().getId_utente());
            } else {
                // Se non c'è nessun istruttore (è null), inseriamo un valore NULL nel database SQL
                stmt.setNull(4, Types.VARCHAR);
            }

            // Eseguiamo la query di modifica (INSERT) sul database
            stmt.executeUpdate();

        } catch (SQLException e) {
            // Se c'è un errore di connessione o sintassi SQL, stampiamo l'errore nella console
            e.printStackTrace();
        }
    }

    @Override
    public Corso cercaPerNomeCorso(String nomeCorso) {
        // Prepariamo la query per cercare un corso specifico in base al nome
        String query = "SELECT * FROM corso WHERE nome_corso = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Inseriamo il nome cercato al posto del '?'
            stmt.setString(1, nomeCorso);

            // Eseguiamo la query di lettura (SELECT) e salviamo i risultati nel ResultSet
            try (ResultSet rs = stmt.executeQuery()) {

                // Se il ResultSet contiene almeno una riga (ovvero se il corso è stato trovato)
                if (rs.next()) {
                    // Estraiamo il valore della colonna 'id_corso' dal database
                    String idCorsoEstratto = rs.getString("id_corso");
                    // Estraiamo il valore della colonna 'nome_corso' dal database
                    String nomeCorsoEstratto = rs.getString("nome_corso");
                    // Estraiamo il valore della colonna 'capienza' dal database
                    int capienzaEstratta = rs.getInt("capienza");
                    // Estraiamo l'ID dell'istruttore dalla chiave esterna (se presente)
                    String idIstruttoreEstratto = rs.getString("id_istruttore");

                    // Creiamo una variabile vuota per l'istruttore
                    Istruttore istruttore = null;
                    // Se l'ID dell'istruttore nel database non era vuoto
                    if (idIstruttoreEstratto != null) {
                        // Creiamo un nuovo oggetto Istruttore
                        istruttore = new Istruttore(idIstruttoreEstratto,null,null,null,null, new ArrayList<>(),new ArrayList<>());
                        // Assegniamo all'istruttore l'ID appena trovato
                        istruttore.setId_utente(idIstruttoreEstratto);
                    }

                    // Creiamo il nuovo oggetto Corso con tutti i dati estratti (e una lista vuota per le partecipazioni)[cite: 1]
                    Corso corsoTrovato = new Corso(idCorsoEstratto, nomeCorsoEstratto, capienzaEstratta, istruttore, new ArrayList<>());

                    // Restituiamo il corso trovato e mappato
                    return corsoTrovato;
                }
            }
        } catch (SQLException e) {
            // Gestione dell'errore SQL
            e.printStackTrace();
        }
        // Se non è stato trovato nulla nell'if(rs.next()), restituiamo null
        return null;
    }

    @Override
    public Corso cercaPerId_Corso(String id_Corso) {
        // Prepariamo la query per cercare un corso tramite la sua chiave primaria (ID)
        String query = "SELECT * FROM corso WHERE id_corso = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Inseriamo l'ID cercato al posto del '?'
            stmt.setString(1, id_Corso);

            // Eseguiamo la query
            try (ResultSet rs = stmt.executeQuery()) {

                // Anche qui, controlliamo se c'è un risultato
                if (rs.next()) {
                    // RIPETIAMO LA LOGICA DI ESTRAZIONE ESPLICITA:

                    // Estraiamo singolarmente ogni colonna della riga corrente
                    String idCorsoEstratto = rs.getString("id_corso");
                    String nomeCorsoEstratto = rs.getString("nome_corso");
                    int capienzaEstratta = rs.getInt("capienza");
                    String idIstruttoreEstratto = rs.getString("id_istruttore");

                    // Gestiamo la creazione dell'oggetto Istruttore associato
                    Istruttore istruttore = null;
                    if (idIstruttoreEstratto != null) {
                        istruttore = new Istruttore(idIstruttoreEstratto,null,null,null,null, new ArrayList<>(),new ArrayList<>());
                        istruttore.setId_utente(idIstruttoreEstratto);
                    }

                    // Assembliamo l'oggetto finale Corso e lo restituiamo[cite: 1]
                    return new Corso(idCorsoEstratto, nomeCorsoEstratto, capienzaEstratta, istruttore, new ArrayList<>());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Restituiamo null se l'ID non esiste
    }

    @Override
    public List<Corso> trovaTutti() {
        // Creiamo una lista vuota in cui andremo a inserire tutti i corsi trovati
        List<Corso> corsi = new ArrayList<>();
        // Prepariamo la query per prendere tutte le righe della tabella corso
        String query = "SELECT * FROM corso";

        // Prepariamo ed eseguiamo la query. Qui lo facciamo tutto nella stessa riga del try perché non ci sono '?' da sostituire
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            // Usiamo 'while' invece di 'if' perché vogliamo scorrere TUTTE le righe restituite, non solo una
            while (rs.next()) {

                // RIPETIAMO LA LOGICA DI ESTRAZIONE PER OGNI SINGOLA RIGA TROVATA:

                String idCorsoEstratto = rs.getString("id_corso");
                String nomeCorsoEstratto = rs.getString("nome_corso");
                int capienzaEstratta = rs.getInt("capienza");
                String idIstruttoreEstratto = rs.getString("id_istruttore");

                Istruttore istruttore = null;
                if (idIstruttoreEstratto != null) {
                    istruttore = new Istruttore(idIstruttoreEstratto,null,null,null,null, new ArrayList<>(),new ArrayList<>());
                    istruttore.setId_utente(idIstruttoreEstratto);
                }

                // Creiamo l'oggetto Corso per la riga corrente del database[cite: 1]
                Corso corsoTrovato = new Corso(idCorsoEstratto, nomeCorsoEstratto, capienzaEstratta, istruttore, new ArrayList<>());

                // Invece di restituirlo subito, lo aggiungiamo alla lista creata all'inizio del metodo
                corsi.add(corsoTrovato);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Restituiamo l'intera lista riempita con tutti i corsi
        return corsi;
    }

    @Override
    public void aggiornaCorso(Corso corso) {
        // Query per aggiornare i campi di un corso già esistente usando il suo ID
        String query = "UPDATE corso SET nome_corso = ?, capienza = ?, id_istruttore = ? WHERE id_corso = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            // Impostiamo il primo parametro (nome del corso)
            stmt.setString(1, corso.getNomeCorso());
            // Impostiamo il secondo parametro (capienza)
            stmt.setInt(2, corso.getCapienza());

            // Gestiamo l'aggiornamento dell'istruttore (può essere stato rimosso o aggiunto)
            if (corso.getIstruttoreGestore() != null) {
                // Inseriamo il nuovo ID istruttore
                stmt.setString(3, corso.getIstruttoreGestore().getId_utente());
            } else {
                // Cancelliamo l'ID istruttore precedente impostando il valore a NULL
                stmt.setNull(3, Types.VARCHAR);
            }

            // Impostiamo il quarto parametro: l'ID del corso (per dire al database QUALE riga modificare)
            stmt.setString(4, corso.getId_Corso());

            // Eseguiamo la query di modifica (UPDATE)
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}