package implementazionePostgresDAO;

import dao.CorsoDAO;
import database.ConnessioneDatabase;
import model.Corso;
import model.Istruttore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class CorsoImplementazionePostgresDAO implements CorsoDAO {

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
        String query = "INSERT INTO Corso (Id_Corso, NomeCorso, Capienza, Id_Istruttore) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, corso.getId_Corso());
            stmt.setString(2, corso.getNomeCorso());
            stmt.setInt(3, corso.getCapienza());

            if (corso.getIstruttoreGestore() != null) {
                stmt.setString(4, corso.getIstruttoreGestore().getId_utente());
            } else {
                stmt.setNull(4, Types.VARCHAR);
            }

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Corso cercaPerNomeCorso(String nomeCorso) {
        // Query corretta: NomeCorso senza underscore
        String query = "SELECT * FROM Corso WHERE NomeCorso = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nomeCorso);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Estrazione utilizzando i nomi esatti delle colonne SQL
                    String idCorsoEstratto = rs.getString("Id_Corso");
                    String nomeCorsoEstratto = rs.getString("NomeCorso");
                    int capienzaEstratta = rs.getInt("Capienza");
                    String idIstruttoreEstratto = rs.getString("Id_Istruttore");

                    Istruttore istruttore = null;
                    if (idIstruttoreEstratto != null) {
                        istruttore = new Istruttore(idIstruttoreEstratto, null, null, null, null, new ArrayList<>(), new ArrayList<>());
                        istruttore.setId_utente(idIstruttoreEstratto);
                    }

                    return new Corso(idCorsoEstratto, nomeCorsoEstratto, capienzaEstratta, istruttore, new ArrayList<>());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Corso cercaPerId_Corso(String id_Corso) {
        // Query corretta: Id_Corso senza underscore superfluo sulla "c"
        String query = "SELECT * FROM Corso WHERE Id_Corso = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, id_Corso);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String idCorsoEstratto = rs.getString("Id_Corso");
                    String nomeCorsoEstratto = rs.getString("NomeCorso");
                    int capienzaEstratta = rs.getInt("Capienza");
                    String idIstruttoreEstratto = rs.getString("Id_Istruttore");

                    Istruttore istruttore = null;
                    if (idIstruttoreEstratto != null) {
                        istruttore = new Istruttore(idIstruttoreEstratto, null, null, null, null, new ArrayList<>(), new ArrayList<>());
                        istruttore.setId_utente(idIstruttoreEstratto);
                    }

                    return new Corso(idCorsoEstratto, nomeCorsoEstratto, capienzaEstratta, istruttore, new ArrayList<>());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Corso> trovaTutti() {
        List<Corso> corsi = new ArrayList<>();
        String query = "SELECT * FROM Corso";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String idCorsoEstratto = rs.getString("Id_Corso");
                String nomeCorsoEstratto = rs.getString("NomeCorso");
                int capienzaEstratta = rs.getInt("Capienza");
                String idIstruttoreEstratto = rs.getString("Id_Istruttore");

                Istruttore istruttore = null;
                if (idIstruttoreEstratto != null) {
                    istruttore = new Istruttore(idIstruttoreEstratto, null, null, null, null, new ArrayList<>(), new ArrayList<>());
                    istruttore.setId_utente(idIstruttoreEstratto);
                }

                Corso corsoTrovato = new Corso(idCorsoEstratto, nomeCorsoEstratto, capienzaEstratta, istruttore, new ArrayList<>());
                corsi.add(corsoTrovato);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return corsi;
    }

    @Override
    public void aggiornaCorso(Corso corso) {
        // Query corretta: allineata ai nomi delle colonne del database
        String query = "UPDATE Corso SET NomeCorso = ?, Capienza = ?, Id_Istruttore = ? WHERE Id_Corso = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, corso.getNomeCorso());
            stmt.setInt(2, corso.getCapienza());

            if (corso.getIstruttoreGestore() != null) {
                stmt.setString(3, corso.getIstruttoreGestore().getId_utente());
            } else {
                stmt.setNull(3, Types.VARCHAR);
            }

            stmt.setString(4, corso.getId_Corso());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int contaIscrittiAlCorso(String id_Corso) {
        String query = "SELECT COUNT(*) AS numero_iscritti FROM Partecipa WHERE Id_Corso = ?";
        int conteggio = 0;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, id_Corso);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    conteggio = rs.getInt("numero_iscritti");
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore nel conteggio degli iscritti: " + e.getMessage());
        }
        return conteggio;
    }
}