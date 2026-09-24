package implementazionePostgresDAO;
import dao.MembroVipDAO;
import database.ConnessioneDatabase;
import model.MembroVip;
import model.SchedaAllenamento;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

public class MembroVipImplementazionePostgresDAO implements MembroVipDAO {
    // Variabile per mantenere la connessione al database
    private Connection connection;


    public MembroVipImplementazionePostgresDAO() {
        try {
            connection = ConnessioneDatabase.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void salva(MembroVip membroVip) {
        // Query di inserimento. Assumiamo che ci sia una tabella 'membro_vip' (o 'utente') con queste colonne
        String query = "INSERT INTO membro_vip (id_utente, nome, cognome, username, password, id_scheda_allenamento) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            // Impostiamo i parametri base ereditati dalla classe Iscritto
            stmt.setString(1, membroVip.getId_utente());
            stmt.setString(2, membroVip.getNome());
            stmt.setString(3, membroVip.getCognome());
            stmt.setString(4, membroVip.getUsername());
            stmt.setString(5, membroVip.getPassword());

            // Gestione sicura della chiave esterna per SchedaAllenamento
            // (Verifichiamo che non sia null per evitare NullPointerException)
            // L'oggetto SchedaAllenamento è una proprietà della superclasse ereditata da MembroVip[cite: 4]
            if (membroVip.getSchedaAllenamento() != null) {
                // Assumiamo che SchedaAllenamento abbia un metodo getId_Scheda()
                stmt.setString(6, membroVip.getSchedaAllenamento().getId_Scheda());
            } else {
                stmt.setNull(6, Types.VARCHAR);
            }

            // Eseguiamo l'inserimento
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public MembroVip cercaPerUsername(String username) {
        // Query per trovare un membro in base all'username
        String query = "SELECT * FROM membro_vip WHERE username = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Sostituiamo il punto interrogativo con l'username passato al metodo
            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                // Se troviamo una corrispondenza nel database
                if (rs.next()) {

                    // 1. Estrazione esplicita di tutti i campi base
                    String idUtenteEstratto = rs.getString("id_utente");
                    String nomeEstratto = rs.getString("nome");
                    String cognomeEstratto = rs.getString("cognome");
                    String usernameEstratto = rs.getString("username");
                    String passwordEstratta = rs.getString("password");
                    String idSchedaEstratta = rs.getString("id_scheda_allenamento");

                    // 2. Creazione dell'oggetto dipendente "SchedaAllenamento" (Proxy)
                    SchedaAllenamento scheda = null;
                    if (idSchedaEstratta != null) {
                        scheda = new SchedaAllenamento(idSchedaEstratta,null,null,null);
                        scheda.setId_Scheda(idSchedaEstratta);
                    }

                    // 3. Istanziazione dell'oggetto MembroVip[cite: 4]
                    // Passiamo le stringhe estratte e creiamo nuove ArrayList vuote per tutte le liste dipendenti
                    MembroVip membroTrovato = new MembroVip(
                            idUtenteEstratto,
                            nomeEstratto,
                            cognomeEstratto,
                            usernameEstratto,
                            passwordEstratta,
                            new ArrayList<>(), // pagamenti vuoti
                            scheda,            // scheda allenamento instanziata sopra
                            new ArrayList<>(), // partecipazioni vuote
                            new ArrayList<>()  // serviziPrenotati vuoti[cite: 4]
                    );

                    return membroTrovato;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Se non troviamo nessuno, restituiamo null
        return null;
    }

    @Override
    public List<MembroVip> trovaTutti() {
        // Inizializziamo la lista che conterrà tutti i risultati[cite: 3]
        List<MembroVip> membri = new ArrayList<>();
        String query = "SELECT * FROM membro_vip";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            // Scorriamo tutte le righe della tabella
            while (rs.next()) {

                // RIPETIZIONE DELLA LOGICA DI ESTRAZIONE PER OGNI RIGA
                String idUtenteEstratto = rs.getString("id_utente");
                String nomeEstratto = rs.getString("nome");
                String cognomeEstratto = rs.getString("cognome");
                String usernameEstratto = rs.getString("username");
                String passwordEstratta = rs.getString("password");
                String idSchedaEstratta = rs.getString("id_scheda_allenamento");

                SchedaAllenamento scheda = null;
                if (idSchedaEstratta != null) {
                    scheda = new SchedaAllenamento(idSchedaEstratta,null,null,null);
                    scheda.setId_Scheda(idSchedaEstratta);
                }

                // Creazione dell'oggetto per la riga corrente
                MembroVip membroCorrente = new MembroVip(
                        idUtenteEstratto,
                        nomeEstratto,
                        cognomeEstratto,
                        usernameEstratto,
                        passwordEstratta,
                        new ArrayList<>(),
                        scheda,
                        new ArrayList<>(),
                        new ArrayList<>()
                );

                // Aggiungiamo l'oggetto appena creato alla lista
                membri.add(membroCorrente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return membri;
    }

    @Override
    public void aggiornaMembro(MembroVip membroVip) {
        // Query di aggiornamento usando id_utente come chiave di ricerca (WHERE)[cite: 3]
        String query = "UPDATE membro_vip SET nome = ?, cognome = ?, username = ?, password = ?, id_scheda_allenamento = ? WHERE id_utente = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            // Impostiamo i nuovi valori presi dall'oggetto
            stmt.setString(1, membroVip.getNome());
            stmt.setString(2, membroVip.getCognome());
            stmt.setString(3, membroVip.getUsername());
            stmt.setString(4, membroVip.getPassword());

            // Aggiorniamo la chiave esterna della scheda allenamento
            if (membroVip.getSchedaAllenamento() != null) {
                stmt.setString(5, membroVip.getSchedaAllenamento().getId_Scheda());
            } else {
                stmt.setNull(5, Types.VARCHAR);
            }

            // Usiamo l'ID dell'utente per specificare quale riga modificare
            stmt.setString(6, membroVip.getId_utente());

            // Eseguiamo l'aggiornamento
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
