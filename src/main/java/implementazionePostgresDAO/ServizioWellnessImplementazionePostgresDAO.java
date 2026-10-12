package implementazionePostgresDAO;



import dao.ServizioWellnessDAO;
import database.ConnessioneDatabase;
import model.ServizioWellness;

import model.MembroVip;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;


import java.util.ArrayList;
import java.util.List;


/**
 * The type Servizio wellness implementazione postgres dao.
 */
public class ServizioWellnessImplementazionePostgresDAO implements ServizioWellnessDAO {


    private Connection connection;


    /**
     * Instantiates a new Servizio wellness implementazione postgres dao.
     */
    public ServizioWellnessImplementazionePostgresDAO() {
        try {
            connection = ConnessioneDatabase.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void salva(ServizioWellness servizioWellness) {

        String query = "INSERT INTO servizio_wellness (Id_ServizioWellness, nome_servizio, disponibile, id_membro_prenotante) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {


            stmt.setString(1, servizioWellness.getId_ServizioWellness());
            stmt.setString(2, servizioWellness.getNomeServizio());


            if (servizioWellness.getDisponibile() != null) {
                stmt.setBoolean(3, servizioWellness.getDisponibile());
            } else {
                stmt.setNull(3, Types.BOOLEAN);
            }

            if (servizioWellness.getMembroPrenotante() != null) {

                stmt.setString(4, servizioWellness.getMembroPrenotante().getId_utente());
            } else {

                stmt.setNull(4, Types.VARCHAR);
            }


            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ServizioWellness cercaPerId(String id_ServizioWellness) {

        String query = "SELECT * FROM servizio_wellness WHERE id_serviziowellness = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, id_ServizioWellness);


            try (ResultSet rs = stmt.executeQuery()) {


                if (rs.next()) {


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


                    return new ServizioWellness(idEstratto, nomeEstratto, disponibileEstratto, membro);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ServizioWellness> trovaTutti() {

        List<ServizioWellness> servizi = new ArrayList<>();
        String query = "SELECT * FROM servizio_wellness";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {


            while (rs.next()) {


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


                ServizioWellness servizioCorrente = new ServizioWellness(idEstratto, nomeEstratto, disponibileEstratto, membro);


                servizi.add(servizioCorrente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return servizi;
    }

    @Override
    public void aggiornaServizioWellness(ServizioWellness servizioWellness) {

        String query = "UPDATE ServizioWellness SET NomeServizio = ?, Disponibile = ? WHERE Id_ServizioWellness = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {


            stmt.setString(1, servizioWellness.getNomeServizio());


            if (servizioWellness.getDisponibile() != null) {
                stmt.setBoolean(2, servizioWellness.getDisponibile());
            } else {
                stmt.setNull(2, Types.BOOLEAN);
            }


            if (servizioWellness.getMembroPrenotante() != null) {
                stmt.setString(3, servizioWellness.getMembroPrenotante().getId_utente());
            } else {
                stmt.setNull(3, Types.VARCHAR); // Se il membro prenotante è null, rimuoviamo l'associazione nel DB
            }

            stmt.setString(3, servizioWellness.getId_ServizioWellness());


            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}