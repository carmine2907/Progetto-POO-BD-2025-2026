package implementazionePostgresDAO;

import dao.CorsoDAO;
import database.ConnessioneDatabase;
import model.Corso;
import model.Istruttore;
import model.Partecipa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    // merda
    @Override
    public void salva(Corso corso) {
        String query = "INSERT INTO Corso (Id_Corso, NomeCorso, Capienza, Id_Istruttore) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, corso.getId_Corso());
            pstmt.setString(2, corso.getNomeCorso());
            pstmt.setInt(3, corso.getCapienza());

            if (corso.getIstruttoreGestore() != null) {
                pstmt.setString(4, corso.getIstruttoreGestore().getId_utente());
            } else {
                pstmt.setNull(4, java.sql.Types.VARCHAR);
            }

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore durante il salvataggio del corso: " + e.getMessage());
        }
    }

    @Override
    public Corso cercaPerNomeCorso(String nomeCorso) {
        String query = "SELECT * FROM Corso WHERE NomeCorso = ?";
        Corso corso = null;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, nomeCorso);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    corso = mappaCorso(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la ricerca del corso per nome: " + e.getMessage());
        }

        return corso;
    }

    @Override
    public Corso cercaPerId_Corso(String id_Corso) {
        String query = "SELECT * FROM Corso WHERE Id_Corso = ?";
        Corso corso = null;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, id_Corso);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    corso = mappaCorso(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la ricerca del corso per ID: " + e.getMessage());
        }

        return corso;
    }

    @Override
    public List<Corso> trovaTutti() {
        String query = "SELECT * FROM Corso";
        List<Corso> listaCorsi = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                listaCorsi.add(mappaCorso(rs));
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero di tutti i corsi: " + e.getMessage());
        }

        return listaCorsi;
    }

    @Override
    public void aggiornaCorso(Corso corso) {
        String query = "UPDATE Corso SET NomeCorso = ?, Capienza = ?, Id_Istruttore = ? WHERE Id_Corso = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, corso.getNomeCorso());
            pstmt.setInt(2, corso.getCapienza());

            if (corso.getIstruttoreGestore() != null) {
                pstmt.setString(3, corso.getIstruttoreGestore().getId_utente());
            } else {
                pstmt.setNull(3, java.sql.Types.VARCHAR);
            }

            pstmt.setString(4, corso.getId_Corso());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiornamento del corso: " + e.getMessage());
        }
    }h
    private Corso mappaCorso(ResultSet rs) throws SQLException {
        String idCorso = rs.getString("Id_Corso");
        String nomeCorso = rs.getString("NomeCorso");
        int capienza = rs.getInt("Capienza");
        String idIstruttore = rs.getString("Id_Istruttore");

        Istruttore istruttore = null;
        if (idIstruttore != null) {
            istruttore = new Istruttore();
            istruttore.setId_utente(idIstruttore);
        }

        return new Corso(idCorso, nomeCorso, capienza, istruttore, new ArrayList<Partecipa>());
    }
}
