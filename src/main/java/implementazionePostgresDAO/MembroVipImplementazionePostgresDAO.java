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
        String query = "INSERT INTO MembroVip (id_utente, nome, cognome, username, password, id_scheda_allenamento) VALUES (?, ?, ?, ?, ?, ?)";

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

    public MembroVip cercaPerUsername(String username) {
        // Query corretta: Unisce MembroVip a Utente (per l'username/dati base)
        // e a SchedaAllenamento (tramite l'ID) tramite LEFT JOIN nel caso non abbia una scheda.
        String query = "SELECT " + "u.Id_Utente AS id_utente, " + "u.Nome AS nome, " + "u.Cognome AS cognome, " + "u.Username AS username, " + "u.Password AS password, " + "sa.Id_Scheda AS id_scheda_allenamento " + "FROM MembroVip mv " + "JOIN Utente u ON mv.Id_MembroVip = u.Id_Utente " + "LEFT JOIN SchedaAllenamento sa ON mv.Id_MembroVip = sa.Id_Iscritto " + "WHERE u.Username = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String idUtenteEstratto = rs.getString("id_utente");
                    String nomeEstratto = rs.getString("nome");
                    String cognomeEstratto = rs.getString("cognome");
                    String usernameEstratto = rs.getString("username");
                    String passwordEstratta = rs.getString("password");
                    String idSchedaEstratta = rs.getString("id_scheda_allenamento");

                    SchedaAllenamento scheda = null;
                    if (idSchedaEstratta != null) {
                        scheda = new SchedaAllenamento(idSchedaEstratta, null, null, null);
                        scheda.setId_Scheda(idSchedaEstratta);
                    }

                    MembroVip membroTrovato = new MembroVip(
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

                    return membroTrovato;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<MembroVip> trovaTutti() {
        // Inizializziamo la lista che conterrà tutti i
        List<MembroVip> membri = new ArrayList<>();
        String query = "SELECT * FROM MembroVip";

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
        String query = "UPDATE MembroVip SET nome = ?, cognome = ?, username = ?, password = ?, id_scheda_allenamento = ? WHERE id_utente = ?";

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
