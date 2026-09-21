package implementazionePostgresDAO;

import dao.PagamentoDAO;
import database.ConnessioneDatabase;
import model.Iscritto;
import model.Pagamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PagamentoImplementazionePostgresDAO implements PagamentoDAO {

    private Connection connection;

    public PagamentoImplementazionePostgresDAO() {
        try {
            connection = ConnessioneDatabase.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void salva(Pagamento pagamento) {
        String query = "INSERT INTO Pagamento (Id_Pagamento, Importo, Id_Iscritto) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, pagamento.getId_Pagamento());
            pstmt.setDouble(2, pagamento.getImporto());

            // Estraiamo l'ID dall'oggetto Iscritto associato al pagamento
            if (pagamento.getIscritto() != null) {
                pstmt.setString(3, pagamento.getIscritto().getId_utente());
            } else {
                pstmt.setNull(3, java.sql.Types.VARCHAR);
            }

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore durante il salvataggio del pagamento: " + e.getMessage());
        }
    }

    @Override
    public Pagamento cercaPerId_Pagamento(String id_pagamento) {
        // Facciamo una JOIN con Utente per ottenere i dati dell'iscritto che ha pagato
        String query = "SELECT p.Id_Pagamento, p.Importo, p.Id_Iscritto, " +
                "u.Nome, u.Cognome, u.Username, u.Password " +
                "FROM Pagamento p " +
                "JOIN Utente u ON p.Id_Iscritto = u.Id_Utente " +
                "WHERE p.Id_Pagamento = ?";

        Pagamento pagamento = null;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, id_pagamento);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // 1. Ricostruiamo l'oggetto Iscritto (con liste vuote)
                    Iscritto iscritto = new Iscritto(
                            rs.getString("Id_Iscritto"), // corrisponde a Id_Utente
                            rs.getString("Nome"),
                            rs.getString("Cognome"),
                            rs.getString("Username"),
                            rs.getString("Password"),
                            new ArrayList<>(), // pagamenti (vuoto per evitare cicli infiniti)
                            null,              // schedaAllenamento
                            new ArrayList<>()  // partecipazioni
                    );

                    // 2. Creiamo l'oggetto Pagamento inserendoci l'Iscritto appena creato
                    pagamento = new Pagamento(
                            rs.getString("Id_Pagamento"),
                            rs.getDouble("Importo"),
                            iscritto
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la ricerca del pagamento per ID: " + e.getMessage());
        }

        return pagamento;
    }

    @Override
    public List<Pagamento> trovaTutti() {
        String query = "SELECT p.Id_Pagamento, p.Importo, p.Id_Iscritto, " +
                "u.Nome, u.Cognome, u.Username, u.Password " +
                "FROM Pagamento p " +
                "JOIN Utente u ON p.Id_Iscritto = u.Id_Utente";

        List<Pagamento> listaPagamenti = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Iscritto iscritto = new Iscritto(
                        rs.getString("Id_Iscritto"),
                        rs.getString("Nome"),
                        rs.getString("Cognome"),
                        rs.getString("Username"),
                        rs.getString("Password"),
                        new ArrayList<>(),
                        null,
                        new ArrayList<>()
                );

                Pagamento pagamento = new Pagamento(
                        rs.getString("Id_Pagamento"),
                        rs.getDouble("Importo"),
                        iscritto
                );

                listaPagamenti.add(pagamento);
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero di tutti i pagamenti: " + e.getMessage());
        }

        return listaPagamenti;
    }

    @Override
    public void aggiornaPagamento(Pagamento pagamento) {
        String query = "UPDATE Pagamento SET Importo = ?, Id_Iscritto = ? WHERE Id_Pagamento = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setDouble(1, pagamento.getImporto());

            if (pagamento.getIscritto() != null) {
                pstmt.setString(2, pagamento.getIscritto().getId_utente());
            } else {
                pstmt.setNull(2, java.sql.Types.VARCHAR);
            }

            pstmt.setString(3, pagamento.getId_Pagamento());

            int righeModificate = pstmt.executeUpdate();
            if(righeModificate == 0) {
                System.out.println("Nessun pagamento aggiornato. L'ID potrebbe non esistere.");
            }
        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiornamento del pagamento: " + e.getMessage());
        }
    }
}
