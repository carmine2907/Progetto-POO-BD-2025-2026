import gui.Home;
import implementazionePostgresDAO.UtenteImplementazionePostgresDao;
import model.Atleta;
import model.Squadra;
import implementazionePostgresDAO.AtletaImplementazionePostgresDAO;
import gui.Home;
import gui.LoginGUI;
import database.ConnessioneDatabase;
import controller.SistemaController;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    private static SistemaController sistemaController;

    public static void main(String[] args) {

        // 1. CHIAMATA AL DATABASE
        try {
            System.out.println("SISTEMA: Connessione al database in corso...");
            Connection conn = ConnessioneDatabase.getInstance().getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("SISTEMA: Connessione a PostgreSQL stabilita!");
            }
        } catch (SQLException e) {
            System.err.println("SISTEMA - ERRORE CRITICO: Database offline!");
            e.printStackTrace();
            return; // Inutile proseguire se il database è offline
        }


        sistemaController = new SistemaController();

        // 3. AVVIO DELLA COMPONENTE GRAFICA
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Creiamo la Home passandogli il controller (ma la terremo nascosta all'inizio)
                Home h = new Home(sistemaController);

                // Creiamo il Login passandogli la Home associata
                LoginGUI login = new LoginGUI(sistemaController, h);

                // Mostriamo solo la finestra di Login all'avvio
                login.mostra();
            }
        });
    }

    public static SistemaController getSistemaController() {
        return sistemaController;
    }
}