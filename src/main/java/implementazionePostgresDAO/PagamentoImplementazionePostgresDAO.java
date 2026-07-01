package implementazionePostgresDAO;

import model.Pagamento;
import model.Pagamento.StatoPag;
import database.ConnessioneDatabase;
import dao.PagamentoDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

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
        String sql = "INSERT INTO pagamento (id_pagamento, importo, data_pagamento, stato) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, pagamento.getIdPagamento());
            ps.setDouble(2, pagamento.getImporto());

            java.sql.Date sqlDate = new java.sql.Date(pagamento.getDataPagamento().getTime());
            ps.setDate(3, sqlDate);

            ps.setString(4, pagamento.getStato().name());

            ps.executeUpdate();
            System.out.println("Pagamento salvato correttamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Pagamento cercaPerId(int idPagamento) {
        String sql = "SELECT importo, data_pagamento, stato FROM pagamento WHERE id_pagamento = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idPagamento);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    double importo = rs.getDouble("importo");

                    java.util.Date dataPagamento = rs.getDate("data_pagamento");

                    String statoStringa = rs.getString("stato");
                    StatoPag statoEnum = StatoPag.valueOf(statoStringa);

                    return new Pagamento(idPagamento, importo, dataPagamento, statoEnum);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.out.println("Errore: lo stato salvato nel DB non corrisponde all'enum StatoPag.");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void aggiornaStato(int idPagamento, StatoPag nuovoStato) {
        String sql = "UPDATE pagamento SET stato = ? WHERE id_pagamento = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, nuovoStato.name());
            ps.setInt(2, idPagamento);

            int righeAggiornate = ps.executeUpdate();

            if (righeAggiornate > 0) {
                System.out.println("Stato del pagamento aggiornato a: " + nuovoStato.name());
            } else {
                System.out.println("Pagamento non trovato. Nessun aggiornamento effettuato.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String getUltimoStatoPagamento(int idAtleta) {
        // Ordiniamo per data decrescente e prendiamo il primo (il più recente)
        String sql = "SELECT stato FROM pagamento WHERE id_atleta = ? ORDER BY data_pagamento DESC LIMIT 1";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idAtleta);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("stato"); // Ritorna APPROVATO, RIFIUTATO o IN_ATTESA
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Se l'atleta non ha mai fatto pagamenti
        return "NESSUN_PAGAMENTO";
    }
}